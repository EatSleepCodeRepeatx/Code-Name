package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

import Control.*;
import Model.Driver;

public class GUI implements Observer {
	
	private JFrame _frame;
	private JMenuBar _menuBar;
	private JMenu _fileMenu;
	private JMenuItem _new2TeamGame;
	private JMenuItem _new3TeamGame;
	private JMenuItem _quit;
	private JPanel _locationsPanel;
	private JPanel _spymasterInfoPanel;
	private JButton[][] _locations;
	private Driver _driver;
	private Game _game;
	private String _turnMessage;
	private IMGFinalStage _img;
	private Egg _egg;

	/*
	 * Constructor that initializes the GUI elements and populates
	 * them with data from the Game objecSet up the frame and panel for Spymaster input
		_spymasterPanelt.
	 */
	public GUI() {};
	public GUI (Driver d) {
		// egg image
		_egg = new Egg();
		// image
		_img  = new IMGFinalStage();
		
		_driver = d;
		_game = _driver.getGame();
		
		//This is the primary frame everything goes into.
		_frame = new JFrame("Codewords");
		
		//This is the menu bar
		_menuBar = new JMenuBar();
		
		//This is the panel where the locations go... the BoxLayout is set 
		//up so the correct number of rows display (see loop below).
		_locationsPanel = new JPanel();
		_locationsPanel.setLayout(new BoxLayout(_locationsPanel,BoxLayout.Y_AXIS));
		
		//...and this is the Array where the individual location panels are.
		_locations = new JButton[_game.getBoard().getRows()][_game.getBoard().getCols()];
		
		//This is the panel at the bottom where the spymaster information is
		_spymasterInfoPanel = new JPanel();
		
		//Show the initial dialog message for the first turn
		_turnMessage = _game.getTurnMessage();
		JOptionPane.showMessageDialog(_frame, _turnMessage);
		
		//This adds the location panels to the main panel row by row
		updateLocationsPanel();
		
		//This initializes all the menu bar stuff and packs it into the menu.
		updateMenuBar();
		
		//This updates the Spymaster Panel the first time
		updateSpymasterPanel();
		
		//Finally, we add all our stuff to the frame
		_frame.add(_menuBar, BorderLayout.NORTH);
		_frame.add(_locationsPanel, BorderLayout.CENTER);
		_frame.add(_spymasterInfoPanel, BorderLayout.SOUTH);
	}
	
	/*  
	 * This method populates the buttons on the GUI with updated location information, including whether the location is revealed.
	 * Called whenever a new game is set up, and when a button is clicked to reveal the location.
	 */
	public void updateLocationsPanel() {
		//Empty out all the buttons to replace them with new information
		_locationsPanel.removeAll();

		//Cycle through the rows in the grid, make panels for each one.
		for (int i=0; i<_locations.length; i++) {
			JPanel row = new JPanel();
			
			//Cycle through all the columns in each row to make the buttons
			for (int j=0; j<_locations[i].length; j++) {
				
				//Make a button for this position and set the size and layout.
				_locations[i][j] = new JButton();
				_locations[i][j].setPreferredSize(new Dimension(130,65));
				_locations[i][j].setLayout(new BoxLayout(_locations[i][j],BoxLayout.Y_AXIS));
				
				//Get the location associated with this spot on the grid.
				Location loc = _game.getBoard().getPosition(i,j);
				
				//Associate the location with this grid point
				loc.setRowsAndCols(i, j);
				
				//Set up all the info we need from the location to put in the button
				JLabel codename = new JLabel(loc.getCodename());
				JLabel team = new JLabel(loc.getTypeOfPerson());
				
				//Show the appropriate information for the spymaster
				if (_game.isSpymasterTurn()) {
					//If the location hasn't been revealed yet, show the codename
					if (!loc.getIsRevealed()) {
						_locations[i][j].add(codename);					
					}
					_locations[i][j].add(team);//Then show the team, regardless	
				}
				
				//Show the appropriate information for the player's turn (after the spymaster goes)
				else {
					//Add the location information to the button, starting with the codename...
					_locations[i][j].add(codename);
				
					//...and if the location is revealed, add the team name
					if (loc.getIsRevealed()) {
						_locations[i][j].add(team);
					}
				}

				//Copy the row and col number so we can use it in the action listener to reveal the right location
				int rowCopy = i;
				int colCopy = j;

				//Set up the actionListener (if the location is revealed, and it isn't the spymaster's turn
				if(!loc.getIsRevealed() && !_game.isSpymasterTurn()) { 
					_locations[i][j].addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							//Copy the row and column location
							int row = rowCopy;
							int col = colCopy;
						
							//Cycle through every location in the location list to find the one associated with this spot on the grid
							for (int i=0;i<_game.getBoard().getRows();i++) {
								for (int j=0;j<_game.getBoard().getCols();j++) {
									if(row == i && col == j) {
										//...when the right location is found, mark it revealed and update the GUI
										_game.getBoard().getPosition(i,j).locationIsRevealed();
										
										//ends the turn if the selected location isn't an agent from the currentTeam. If it's an assassin, the currentTeam is removed from the game
										if(!_game.getBoard().getPosition(i, j).getTypeOfPerson().equals(_game.getCurrentTeam())) {
											if(_game.getBoard().getPosition(i,j).getTypeOfPerson().equals("Assassin")) {
												JOptionPane.showMessageDialog(_frame, _game.getCurrentTeam() + " Team has revealed the assassin!");
												_game.getCurrentTeamEntry().setRevealedAssassin();
												_game.removeTeam();
												//nextTurn();
											} else {											
												JOptionPane.showMessageDialog(_frame, "This is not your agent!");
												//nextTurn();
											}
										}/*else if(_game.getBoard().isTeamRevealed(_game.getCurrentTeamEntry())) */
										nextTurn();
									}
								}
							}
							
							//After the player makes a selection, decrease the count by 1
							_game.decreaseCount();
							update();
						}			
					});
				}
				
				//Add this button to the row
				row.add(_locations[i][j]);
			}
			
			//Add this row of buttons to the panel
			_locationsPanel.add(row);
		}
	}
	
	//This populates menu bar
	public void updateMenuBar() {
		_fileMenu = new JMenu("File");
		_new2TeamGame = new JMenuItem("New 2-Team Game");
		_new3TeamGame = new JMenuItem("New 3-Team Game");
		
		//Add an ActionListener to start a new 2 team game
		_new2TeamGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_game = new Game(_driver, 2);//by default a new game is 2 team
				
				update();
			}
			
		});
		_fileMenu.add(_new2TeamGame);
		
		//Add an ActionListener to start a new 3 team game
		_new3TeamGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_game = new Game(_driver,3);//by default a new game is 2 teams
				
				update();
			}
			
		});
		_fileMenu.add(_new3TeamGame);
		
		
		_quit = new JMenuItem("Quit");
		_quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		
		_fileMenu.add(_quit);
		_menuBar.add(_fileMenu);
		

	}
	
	//This is the method to update what's in the Spymaster panel, based on whose turn it is and whether it's the spymaster's part of the turn
	public void updateSpymasterPanel() {
		_spymasterInfoPanel.removeAll();
		
		//The text fields where the Spymaster will enter the clues and counts
		JTextField clue = new JTextField(10);
		JTextField count = new JTextField(10);
		
		//This is what goes into the spymaster's panel if it's the spymaster's turn
		if(_game.isSpymasterTurn()) {
			
			//Make a button to end the spymaster's portion of the turn
			JButton endTurn = new JButton("END");
			_spymasterInfoPanel.add(new JLabel("ENTER SPYMASTER CLUE: "));
			_spymasterInfoPanel.add(clue);
			_spymasterInfoPanel.add(new JLabel("ENTER COUNT: "));
			_spymasterInfoPanel.add(count);		
			JButton bePositive = new JButton("Teehee");
			bePositive.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					_egg.displayImage();
					// update();
				}
			});

			//Specialized actionlistener for the Spymaster
			endTurn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String clueMessage = !_game.isClueLegal(clue.getText())? "Clue": "";
					String countMessage = !_game.isCountLegal(count.getText())? "Count": "";
					String both = !clueMessage.equals("") && !countMessage.equals("")? " and ": " ";
					
					//checks if the clue and count is legal; count has to be between 1 and 25 inclusive
					if(_game.isClueLegal(clue.getText()) && _game.isCountLegal(count.getText())) {
						
						//...accept the spymaster's clue and count
						_game.setSpymasterClueAndCount(clue.getText(), count.getText());
						
						//Spymaster's turn is over
						_game.spymasterDone();
						
						//Update the GUI
						update();
						
					} else {
						JOptionPane.showMessageDialog(_frame, clueMessage + both + countMessage + " is invalid please change");
					}
				}		
			});
			
			//Add the button to the panel
			_spymasterInfoPanel.add(endTurn);
			_spymasterInfoPanel.add(bePositive);
		} 
		
		//This is what goes in the Spymaster panel if the spymaster's portion of the turn is over
		else {
			
			//The button for the player to end their turn
			JButton endTurn = new JButton("END TURN");
			
			//Display the spymaster's clue and count
			_spymasterInfoPanel.add(new JLabel("SPYMASTER CLUE: " + _game.getSpymastersClue() + " COUNT: " + _game.getSpymastersCount()));
			
			//Custom actionlistener for the player's portion of the turn
			endTurn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					nextTurn();
				}	
			});
			_spymasterInfoPanel.add(endTurn);
		}
	}
	
	//This method is called every time a turn is over, it updates the necessary GUI and game elements (written to avoid duplicate code).
	public void nextTurn() {
		//Go to the next turn and display the appropriate message.
		_game.turn();
		JOptionPane.showMessageDialog(_frame, _game.getTurnMessage());	
		//displays easter egg if game won; otherwise, shows next turn dialog.
		if(_game.gameWon()) {
			//JOptionPane.showMessageDialog(_frame, _game.getWinningTeam()  + " Team has won!");		 
			_img.displayImage();
		}
		
		
		//Update the GUI, when they should have been
		update();
	}
	
	//This method has all the GUI updates needed
	public void update() {
		updateSpymasterPanel();
		updateLocationsPanel();
		_driver.updateJFrame();			
	}
	//getters
	public JFrame getFrame() 			{ return _frame;			}
	public JButton[][] getLocations() 	{ return _locations;		}
	public JMenuBar getMenuBar() 		{ return _menuBar;			}
	public JPanel getLocationsPanel() 	{ return _locationsPanel;	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
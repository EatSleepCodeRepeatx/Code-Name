package Control;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import Control.*;
import Model.Driver;


public class Game {
	private BufferedImage _img;
	private Entry<String> _currentTeam;
	private Entry<String> _winningTeam;
	private Board _board;
	private int _count;
	private boolean _spymaster;
	private String _spymastersClue;
	private int _spymastersCount;
	private Driver _driver;
	private String _turnMessage;
	
	//teams are linked lists
	private Entry<String> red;
	private Entry<String> blue;
	private Entry<String> green;
	
	/*****Getters*****/
	public Board getBoard() 	     { return _board;	       			 }//Getter method for the game's board locations and state
	public int getCount() 		     { return _count;          			 }//Getter for the count
	public String getWinningTeam()   { return _winningTeam.getElement(); }//Getter for the winning team
	public String getTurnMessage()   { return _turnMessage;    			 }
	public boolean isSpymasterTurn() { return _spymaster;  	   			 }//Getter for whether it's the spymaster's portion of the turn
	public String getSpymastersClue(){ return _spymastersClue; 			 }//Getter for spymaster information
	public int getSpymastersCount()  { return _spymastersCount;		     }//Getter for spymaster count
	public String getCurrentTeam()   { return _currentTeam.getElement(); }//return "redTeam" or "blueTeam"
	public Entry<String> getCurrentTeamEntry() { return _currentTeam; 	 }//returns the Entry for the current team (for removing)
	public Entry<String> getRed() 	 { return red;						 }
	public Entry<String> getBlue() 	 { return blue;      				 }
	
	
	
	/******Setters*****/
	public void setCount(int num) 	{ _count = num;       }//set the Count
	//Setter for information the Spymaster inputs 
	public void setSpymasterClueAndCount(String clue, String count) {
		_spymastersClue = clue;
		_spymastersCount = Integer.parseInt(count);
	}
	public void setRed(Entry<String> red)   { this.red = red;	}
	public void setBlue(Entry<String> blue) { this.blue = blue; }
	
	
	/*Constructor*/
	public Game(Driver d, int numTeams) {
		_board = new Board(numTeams);
		_driver = d;
		startGame();
	}
	
	/*Sets the conditions for the beginning of the game*/
	public void startGame() {
		_spymaster = true;
		_spymastersClue = "";
		_spymastersCount = 0;
		//_board = new Board();
		
		//teams are now a linked list and the tail is linked to the head so we can call getNext() indefinitley to get the next team
		setRed(new Entry<String>());
		setBlue(new Entry<String>());
		green = new Entry<String>();
		red.setElement("Red");
		blue.setElement("Blue");
		green.setElement("Green");
		
		if(_board.getNumOfTeams() == 2) {
			red.setNext(blue);
			blue.setNext(red);
			blue.setPrev(red);
			red.setPrev(blue);
		}
		
		if(_board.getNumOfTeams() == 3) {
			red.setNext(blue);
			blue.setNext(green);
			green.setNext(red);
			
			red.setPrev(green);
			blue.setPrev(red);
			green.setPrev(blue);
		
			
		}		
		
		_currentTeam = red;
		_winningTeam = null;
		
		_turnMessage = "It is the " + getCurrentTeam() + " team's turn.";
	}
	
	/*receives the choice with an int representing a location that has been revealed, 
	 * then decreases the count and sets the location to "revealed"
	 */
	public void locationSelected(int row, int col) {
		decrementCount();
		_board.getPosition(row,col).locationIsRevealed();
	}
	
	/*
	 * Boolean that determines whether the revealed location is a person for the current team
	 */
	@SuppressWarnings("unlikely-arg-type")
	public boolean isPersonRevealedInCurrentTeam(int row,int col) {
		if ((_currentTeam.equals(getRed()) && _board.getPosition(row,col).getTypeOfPerson().equals(getRed())) || 
				(_currentTeam.equals(getBlue()) && _board.getPosition(row,col).getTypeOfPerson().equals(getBlue())) ||
				(_currentTeam.equals(green) && _board.getPosition(row,col).getTypeOfPerson().equals(green))) 
			return true;
		return false;
	}
	
	/*
	 * This is where new turn stages are indicated
	 */
	public void turn(){
		//changes to the next team if the game hasnt been won yet
		if(gameWon()) 
			_turnMessage = getWinningTeam()  + " Team has won!";
		else {
			_currentTeam = _currentTeam.getNext();
			_spymaster = true;
			_turnMessage = "It is the " + getCurrentTeam()  + " team's turn.";
		}
	}
	
	
	public void spymasterDone() {_spymaster = false;	}
	
	//Decrease spymaster's count
	public void decreaseCount() {
		//As long as there's still a positive count, take one away
		if (_spymastersCount>0) {
			_spymastersCount = _spymastersCount-1;		
		}
		
		//Check again; if it's zero or lower, it's the next team's turn.
		if (_spymastersCount<1) {
			_driver.getGUI().nextTurn();
		}
	}
	
	public void incrementCount() {_count++;}//increment the count
	public void decrementCount() {_count--;}//decrement the count
	
	/*Cycles through to check for all the winning conditions*/
	public boolean gameWon() {
		
		//Two player game: if 1 team reveals the assassin, the other is declared the winner.
		if (_board.getNumOfTeams()==2 && getRed().hasRevealedAssassin()) {
			_winningTeam = getBlue();
			return true;
		} else if (_board.getNumOfTeams()==2 && getBlue().hasRevealedAssassin()) {
			_winningTeam = getRed();
			return true;
		}
		
		//Three player game: If only one team remains, they are declared the winner.
		if (_currentTeam.getNext().equals(_currentTeam)) {
			_winningTeam = _currentTeam;
			return true;
		}
		
		//If all 6 Red Agents are Revealed and the Red team has not Revealed the Assassin, then the Red team wins. This is true no matter who Revealed the final Red Agent.
		if (_board.isTeamRevealed(getRed()) && !getRed().hasRevealedAssassin()) {
			_winningTeam = getRed();
			return true;
		}

		//If all 5 Blue Agents are Revealed and the Blue team has not Revealed the Assassin, then the Blue team wins. This is true no matter who Revealed the final Blue Agent.
		if (_board.isTeamRevealed(getBlue()) && !getBlue().hasRevealedAssassin()) {
			_winningTeam = getBlue();
			return true;
		}
		
		//If all 5 Green Agents are Revealed and the Green team has not Revealed the Assassin, then the Green team wins. This is true no matter who Revealed the final Green Agent.
		if ( _board.isTeamRevealed(green) && !green.hasRevealedAssassin() && _board.getNumOfTeams()==3) {
			_winningTeam = green;
			return true;
		}	
		return false;
	}
	
	
	/*Checks for a legal clue i.e. it returns false if the clue is one of the code names*/
	public boolean isClueLegal(String clue) {
		for(int i=0;i<_board.getRows();i++) {
			for(int j=0;j<_board.getCols();j++) {
				if(clue.equalsIgnoreCase(_board.getPosition(i, j).getCodename()) && !_board.getPosition(i, j).isRevealed) {
					return false;				
				}
			}
		}
		return true;
	}
	//checks if count is between 1 and 25 (inclusive) and if count is an integer or not
	public boolean isCountLegal(String count) {
		int c;
		try{
            c = Integer.parseInt(count);
        } catch (NumberFormatException e) {
            return false;
        }return (c >= 1 && c <= 25)? true: false;
	}
	
	
	public void removeTeam() {
		Entry<String> tmp = _currentTeam.getPrev();
		Entry<String> next = getCurrentTeamEntry().getNext();
		Entry<String> prev = getCurrentTeamEntry().getPrev();
		
		next.setPrev(getCurrentTeamEntry().getPrev());
		prev.setNext(getCurrentTeamEntry().getNext());
		_currentTeam.getPrev();
	}
	
}
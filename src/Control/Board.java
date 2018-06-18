
package Control;

import java.util.ArrayList;
import java.util.Collections;

import Control.CodewordsList;
import Control.Location;


public class Board {
	
	/* default value for the number of locations (row and columns) we should have */
	private final int _rows = 5;
	private final int _cols = 5;
	
	/* ArrayList of Location objects containing 25 space for 25 "Location" instance that defined below */
//	private ArrayList<Location> _locations;
	private Location[][] _locations;
	
	/* ArrayList of the integer-coded persons to be shuffled and assigned to locations*/
	private ArrayList<Integer> _personAssignment;
	
	/*Larger Codewords list, from which the smaller list is generated*/
	private CodewordsList cWL;
	
	/*used for generating random 25 codenames*/
	private ArrayList<String> _wordList;
	
	private int _numOfTeams;// 2 and 3 only
	
	/* Constructor */
	public Board(int numTeams) {
		_numOfTeams = numTeams;//default number of teams
		//initialize the list of _locations and the _personAssignment
		_locations = new Location[_rows][_cols];
		_personAssignment = new ArrayList<Integer>();
		
		//creates a list of 25 random codenames
		cWL = new CodewordsList("src/data/GameWords.txt");
		_wordList = cWL.getList25();
		
		//creates a list of random person assignments
		generatePersonAssignment();
		
		//assigns codewords, persons and revealed status to each location
		assignLoc();
	}
	
	//getters
	public Location getPosition(int row, int col) 		{ return _locations[row][col]; 	}
	public Location[][] getLocationList() 				{ return _locations;			}
	public ArrayList<Integer> getPersonAssignments() 	{ return _personAssignment;		}
	public CodewordsList getLargeCodewordsList() 		{ return cWL; 					}
	public ArrayList<String> getCodewordsList25() 		{ return _wordList;				}
	public int getRows() 								{ return _rows;					}
	public int getCols() 								{ return _cols;					}
	public int getNumOfTeams()    						{ return _numOfTeams; 			}
	
	public void randomizeCodewords() {
		cWL.generateList();
		_wordList = cWL.getList25();
	}

	/*Assigns actual data (codewords and person assignments) to the locations*/
	public void assignLoc() throws UnsupportedOperationException{
		int numOfLoc = _rows * _cols -1;
		try {
			if (_wordList != null) {
				if (_wordList.size() >= numOfLoc) {
					for (int i=0;i<_rows; i++) {
						for (int j=0;j<_cols; j++) {
							_locations[i][j] = new Location(_wordList.get(numOfLoc), _personAssignment.get(numOfLoc));
							numOfLoc--;
						}
					}
				}else {
					throw new UnsupportedOperationException();
				}
			}
		} catch (NullPointerException _ex) {
			_ex.printStackTrace();
		}
}
	
	//creates a list with following person designation and shuffles it
	// 0-red	1-blue	2-innocent	3-assassin 4-green
	public void generatePersonAssignment(){
		if(_numOfTeams == 3) {
			for (int i = 0; i < 6; ++i)//6 reds
				_personAssignment.add(0);
			for (int i = 0; i < 5; ++i) //5 blues
				_personAssignment.add(1);
			for (int i = 0; i < 7; ++i)//7 innocents 
				_personAssignment.add(2);	
			for (int i = 0; i < 5; ++i)//5 greens
				_personAssignment.add(4);
			_personAssignment.add(3);//2 assassins
			_personAssignment.add(3);
		}
	  if(_numOfTeams == 2){
		for (int i = 0; i < 9; ++i)// 9 reds
			_personAssignment.add(0);
		for (int i = 0; i < 8; ++i) // 8 blues 
			_personAssignment.add(1);
		for (int i = 0; i < 7; ++i) // 7 innocents
			_personAssignment.add(2);	
	     	_personAssignment.add(3);//one assassin
	  }
		Collections.shuffle(_personAssignment);
	}
	
	
	
	//checks if teams are revealed
	public boolean isTeamRevealed(Entry team) {
		int count = 0;
		for (int i=0; i<_rows; i++) {
			for (int j=0; j<_cols; j++) {
				if(_locations[i][j].getTypeOfPerson().equals(team.getElement()) && !_locations[i][j].getIsRevealed()) {
					count++;
				}
			}
		}
		return count < 1;
	}
	
	//checks if the assassin is revealed
	public boolean isAssassinRevealed() {
		int count =0;
		for (int i=0;i<_rows;i++) {
			for (int j=0;j<_cols;j++) {
				if(_locations[i][j].getTypeOfPerson().equals("Assassin") && _locations[i][j].getIsRevealed())
					count++;				
			}
		}
		return _numOfTeams == 2? count==1: count == 2;		
	}
	 
}

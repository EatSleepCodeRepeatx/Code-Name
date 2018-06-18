package Control;

import java.util.ArrayList;

import java.util.Collections;

public class Location {

	// 0-red	1-blue	2-innocent	3-assassin 4-green
	private int typeOfPerson;
	
	//is this location revealed?
	boolean isRevealed;
	
	//the location's codename
	private String codeName;
	
	//stores the coordinates of the location on the grid, if chosen;
	private int row;
	private int col;
	
	//default constructor
	public Location() {
		isRevealed = false;
		typeOfPerson = -1;//default value for int is 0 so if this wasnt here there could be issues
		
	}
	//constructor takes in a codename
	public Location(String cName) {
		codeName = cName;
		typeOfPerson = -1;
		isRevealed = false;
		
	}
	//constructor takes in a codename and person type
	public Location(String cName, int type) {
		codeName = cName;
		setTypeOfPerson(type);//call method instead since there could be an exception
		isRevealed = false;
	}
	
	/*Sets the isRevealed boolean to true when revealed*/
	public void locationIsRevealed() {
		isRevealed = true;
	}
	
	//getters
	public String getCodename()   	{ return codeName;	}//returns codename
	public boolean getIsRevealed()	{ return isRevealed;}//returns isRevealed

	//returns the type of person 
	public String getTypeOfPerson(){
		if(typeOfPerson == 0)
			return "Red";
		if(typeOfPerson == 1)
			return "Blue";
		if(typeOfPerson == 2)
			return "Innocent";
		if(typeOfPerson == 3)
			return "Assassin";
		if(typeOfPerson == 4)
			return "Green";
		
		return "No_Person_Assigned";
	}
	
	
	
	//setters
	/*sets the type of person 
	 * poops out IndexOutOfBoundsException if input not 0 to 3
	 * idk if im using the right exception
	*/
	public void setTypeOfPerson(int type) throws IndexOutOfBoundsException{
		if(type > -1 && type < 5)
			typeOfPerson = type;
		else
			throw new IndexOutOfBoundsException("Please enter value 0 to 3: 0-red	1-blue	2-innocent	3-assassin 4-green");
	}
	//sets codename
	public void setCodeName(String s) {
		codeName =s;
	}
	
	//sets the row in the grid for this location
	public void setRowsAndCols(int r, int c) {
		row=r;
		col=c;
	}
}

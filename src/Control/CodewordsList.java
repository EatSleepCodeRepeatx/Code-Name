package Control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class CodewordsList {
	
	/**
	 *  Declare class variables...
	 *  'bank' is where all the codewords from the file go
	 *  'list25' is the randomly generated list of 25 codewords used by the game
	 *  'error' is for when the file doesn't work and the random list doesn't turn out... it'll be at the '0' index of the list, so we can check for errors
	 */
	private ArrayList<String> _bank;
	private ArrayList<String> _list25;
	private String _error;
	
	/**
	 * Empty constructor for Eric
	 */
	public CodewordsList() {
		_bank = new ArrayList<String>();
		_list25 = new ArrayList<String>();		
	}
	
	/**
	 * Constructor
	 * @param file name of file to be read
	 */
	public CodewordsList(String file) {
		_bank = new ArrayList<String>();
		_list25 = new ArrayList<String>();
		_error = "non specified error";
		loadFromFile(file);
		generateList();
	}
	
	/**
	 * Reads file and generates the bank. If the code throws an exception while reading the file, it puts the error message in the index 0 position of the bank
	 * @param file
	 */
	public void loadFromFile(String file) {
		try {
			for (String line : Files.readAllLines(Paths.get(file))) 
				if (!line.equals("")) 
					_bank.add(line);
		}catch (InvalidPathException | IOException e) {
			_error = "Codewords not loaded correctly";
			_bank.add(_error);
		}
	}
	
	/**
	 * Generates the list of 25 random codewords.
	 * First, it checks for the file read error, and passes it to the list so we can test the list before implementing it in the game.
	 * Then, it checks that bank has enough codewords for the game. If not, it passes a different error (there's probably an easier way to do this, but I don't know how!)
	 * Last, it copies the bank, shuffles the copy and assigns the first (random) 25 words to the list. 
	 */
	public void generateList() {
		if(_bank.size()==1 && _bank.get(0).equals(_error)) {
			_list25 = _bank;
		}
		else if(_bank.size()<25) {
			_error = "not enough codewords in file to generate list";
			_list25.add(_error);
		}
		else {
			ArrayList<String> shuffledBank = copyBank();
			Collections.shuffle(shuffledBank);
			for(int i=0;i<25;i++) {
				_list25.add(shuffledBank.get(i));
			}
		}
	}
	
	/**
	 * Makes a copy of the bank in order to manipulate the data while leaving the original data from the file unchanged.
	 * @return copy of the bank
	 */
	public ArrayList<String> copyBank() {
		ArrayList<String> copy = new ArrayList<String>();
		for (int i=0;i<_bank.size();i++) {
			copy.add(_bank.get(i));
		}
		return copy;
	}
	
	/**
	 * Getter method for the bank. NOTE: we shouldn't need to access the bank in the game code, this method is just for testing purposes basically.
	 * @return _bank
	 */
	public ArrayList<String> getBank() {
		return _bank;
	}
	
	/**
	 * Getter method for the _list25. Use this to access the randomly-assigned codenames in the game code.
	 * @return the list of shuffled codewords
	 */
	public ArrayList<String> getList25() {
		return _list25;
	}
	
	/**
	 * Getter method for the _error code. We can use this to test with logic (if _list.get(0).equals(codewords._error) { something's wrong! }) for errors during running
	 * @return the error message
	 */
	public String error() {
		return _error;
	}
}
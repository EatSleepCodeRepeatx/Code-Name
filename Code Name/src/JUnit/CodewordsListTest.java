package JUnit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Control.CodewordsList;

public class CodewordsListTest {

/**
 * Test that the resulting list is the right size and contains the right elements
 */
	@Test
	public void readsFileCorrectly() {
		String filename = "src/data/GameWords.txt";
		CodewordsList codewords = new CodewordsList(filename); 
		ArrayList<String> whatComesBack = codewords.getBank();
		assertEquals(400,whatComesBack.size());
		assertEquals("AFRICA",whatComesBack.get(0));
		assertEquals("YARD",whatComesBack.get(399));
	}

/**
 * Tests to see if the code properly handles empty lines
 */
	@Test
	public void readsMalformedFile() {
		String filename = "src/data/gamewords_malformed.txt";
		CodewordsList codewords = new CodewordsList(filename); 
		ArrayList<String> whatComesBack = codewords.getBank();
		assertEquals(400,whatComesBack.size());
		assertEquals("AFRICA",whatComesBack.get(0));
		assertEquals("YARD",whatComesBack.get(399));		
	}
	
/**
 * Tests to see if the cloning method words as intended
 */
	@Test
	public void makesCopy() {
		CodewordsList codewords = new CodewordsList("src/data/GameWords.txt");
		ArrayList<String> whatComesBack = codewords.copyBank();
		assertEquals(codewords.getBank().size(),whatComesBack.size());
		assertEquals(whatComesBack.get(0),codewords.getBank().get(0));
		assertEquals(whatComesBack.get(5),codewords.getBank().get(5));
		assertEquals(whatComesBack.get(10),codewords.getBank().get(10));
	}
	
/**
 * Tests to see if the code properly handles empty lines
 */
	@Test
	public void readsNoFile() {
		CodewordsList codewords = new CodewordsList(" ");
		assertEquals(1,codewords.getList25().size());
		assertEquals("Codewords not loaded correctly",codewords.getList25().get(0));
	}

/**
 * Tests to see if the code properly handles empty lines
 */
	@Test
	public void generatesListCorrectly() {
		String filename = "src/data/GameWords.txt";
		CodewordsList codewords = new CodewordsList(filename);
		ArrayList<String> whatComesBack = codewords.getList25();
		assertEquals(25,whatComesBack.size());		
	}
	
/**
 * Tests to see if the code properly handles empty lines
 */
	@Test
	public void notEnoughCodewords() {
		CodewordsList codewords = new CodewordsList("src/data/gamewords_tooshort.txt");
		assertEquals(1,codewords.getList25().size());
		assertEquals("not enough codewords in file to generate list",codewords.getList25().get(0));
	}
/**
 * Tests to see if the code properly handles empty lines
 */
	@Test
	public void listIsShuffled() {
		CodewordsList codewords = new CodewordsList("src/data/GameWords.txt");
		int numberOfMatches = 0;
		for(int i=0;i<codewords.getList25().size();i++) {
			if (codewords.getList25().get(i).equals(codewords.getBank().get(i))) {
				numberOfMatches++;
			}
		}
		assertTrue(numberOfMatches<codewords.getList25().size());
	}
}
package JUnit;

import static org.junit.Assert.*;

import org.junit.Test;

import Control.*;

public class BoardTest {

	/*Test that Board class contains 25 Location positions with Location objects in each*/
	@Test
	public void allPosAreMatched() {
		Board b = new Board(2);
		int numberOfLocations = 25;
		assertEquals(numberOfLocations,b.getRows()*b.getCols());
		assertNotNull(b.getPosition(0,0));
		assertNotNull(b.getPosition(b.getRows()-1,b.getCols()-1));
		}

	/*Test that a list is generated correctly assigning the 9 red agents, 8 blue agents, 7 bystanders and 1 assassin*/
	@Test
	public void assignsPeople() {
		Board b = new Board(2);
		int illegalAssignments = 0;
		for(int i=0;i<b.getPersonAssignments().size();i++) {
			if(b.getPersonAssignments().get(i)>3) {
				illegalAssignments++;
			}
			else if(b.getPersonAssignments().get(i)<0) {
				illegalAssignments++;
			}
		}
		
		assertEquals(0,illegalAssignments);
	}
	
	/*Tests where the list of people assignments is shuffled*/
	@Test
	public void listOfAssignmentsShuffled() {
		Board b = new Board(2);
		int numberOfMatches = 0;
		for(int i=0;i<9;i++) {
			if (b.getPersonAssignments().get(i)==0) {
				numberOfMatches++;
			}
		}
		for(int i=9;i<16;i++) {
			if (b.getPersonAssignments().get(i)==1) {
				numberOfMatches++;
			}
		}
		for(int i=16;i<23;i++) {
			if (b.getPersonAssignments().get(i)==2) {
				numberOfMatches++;
			}
		}
		if(b.getPersonAssignments().get(24)==3) {
			numberOfMatches++;
		}
		assertTrue(numberOfMatches<b.getPersonAssignments().size());
}
}

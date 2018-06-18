package JUnit;

import static org.junit.Assert.*;

import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.Test;

import Control.*;
import Model.Driver;

public class GameTest {
	
	private Driver d;
	
	
	@Before
	public void before() {
		d = new Driver();
	}
	
	/*Tests that it's Red's turn when the game starts*/
	@Test
	public void redsTurn() {
		Game test = new Game(d,2);
		String whatComesBack = test.getCurrentTeam();
		String shouldBe = "Red";
		assertEquals(shouldBe,whatComesBack);
	}
	
	/*Checks the locations for codenames*/
	@Test
	public void locationsHaveCodenames() {
		Game test = new Game(d,2);
		for(int i=0; i<test.getBoard().getRows();i++) {
			for(int j=0; j<test.getBoard().getCols();j++) {
				assertNotNull(test.getBoard().getPosition(i,j).getCodename());				
			}
		}
	}
	
	/*Test that it's correctly determined whether a clue is legal or illegal*/
	@Test
	public void clueIsIllegal() {
		Game test = new Game(d,2);
		for(int i=0; i<test.getBoard().getRows();i++) {
			for(int j=0; j<test.getBoard().getCols();j++) {
			String clue = test.getBoard().getPosition(i,j).getCodename();
			assertFalse(test.isClueLegal(clue));
			}
		}
	}
	
	/*Tests that the count is decreased when a location's codename is selected*/
	@Test
	public void countDecreased() {
		Game test = new Game(d,2);
		test.setCount(3);
		int comesBackFirst = test.getCount();
		test.locationSelected(0,0);
		int comesBackAfter = test.getCount();
		int whatComesBack = comesBackFirst - comesBackAfter;
		assertEquals(1,whatComesBack);
	}
	
	/*Tests that a Location is updated when its codeword is selected*/
	@Test
	public void locationIsUpdated() {
		Game test = new Game(d,2);
		test.setCount(3);
		assertFalse(test.getBoard().getPosition(0, 0).getIsRevealed());
		test.locationSelected(0,0);
		assertTrue(test.getBoard().getPosition(0,0).getIsRevealed());
	}
	
	/*Tests winning state: red is all revealed*/
	@Test
	public void checkRedTeamRevealed() {
		Game test = new Game(d,2);
		for(int i=0;i<test.getBoard().getRows();i++) {
			for(int j=0;j<test.getBoard().getCols();j++) {
				if (test.getBoard().getPosition(i,j).getTypeOfPerson().equals("Red")) {
					test.getBoard().getPosition(i,j).locationIsRevealed();
				}				
			}
		}
		assertTrue(test.gameWon());
		assertEquals("Red",test.getWinningTeam());
		assertEquals("Red Team has won!",test.getTurnMessage());
	}
	/*Tests winning state: blue is all revealed*/
	@Test
	public void checkBlueTeamRevealed() {
		Game test = new Game(d,2);
		for(int i=0;i<test.getBoard().getRows();i++) {
			for(int j=0;j<test.getBoard().getCols();j++) {
				if (test.getBoard().getPosition(i,j).getTypeOfPerson().equals("Blue")) {
					test.getBoard().getPosition(i,j).locationIsRevealed();
				}				
			}
		}
		assertTrue(test.gameWon());
		assertEquals("Blue",test.getWinningTeam());
		assertEquals("Blue Team has won!",test.getTurnMessage());
	}	
	
	@Test
	public void checkGreenTeamRevealed() {
		Game test = new Game(d,3);
		for(int i=0;i<test.getBoard().getRows();i++) {
			for(int j=0;j<test.getBoard().getCols();j++) {
				if (test.getBoard().getPosition(i,j).getTypeOfPerson().equals("Green")) {
					test.getBoard().getPosition(i,j).locationIsRevealed();
				}				
			}
		}
		assertTrue(test.gameWon());
		assertEquals("Green",test.getWinningTeam());
		assertEquals("Green Team has won!",test.getTurnMessage());
	}	
	
	
	//Tests that gameWon() returns the correct winningTeam() after the last assassin has been revealed
	@Test
	public void correctWinningTeamAfterLastAssassinTest() {
		
		//Tests for three players
		Game test = new Game(d,3);
		assertTrue(test.getCurrentTeamEntry().getElement().equals("Red"));
		test.removeTeam();
		test.turn();
		assertTrue(test.getCurrentTeamEntry().getElement().equals("Blue"));
		test.removeTeam();
		test.turn();
		assertTrue(test.getCurrentTeamEntry().getElement().equals("Green"));
	}
	
	//Tests that gameWon() returns the correct winningTeam() after the last assassin has been revealed
	@Test
	public void correctTeamAfterTurnEndsTest() {
		
		//Tests for three players
		Game test = new Game(d,3);
		assertTrue(test.getCurrentTeamEntry().getElement().equals("Red"));
		test.removeTeam();
		test.turn();
		assertTrue(test.getCurrentTeamEntry().getElement().equals("Blue"));
		test.removeTeam();
		test.turn();
		assertTrue(test.getCurrentTeamEntry().getElement().equals("Green"));
	}	
	
	
	/*Tests that the right team wins when assassin is revealed*/
	/*@Test
	public void whoWinsWhenAssassinRevealed() {
		Game test = new Game(d);
		test.setCurrentTeam("Red");
		for(int i=0;i<test.getBoard().getRows();i++) {
			for(int j=0;j<test.getBoard().getCols();j++) {
				if (test.getBoard().getPosition(i,j).getTypeOfPerson().equals("assassin")) {
				test.getBoard().getPosition(i,j).locationIsRevealed();
			}				
			}
		}

		assertTrue(test.gameWon());
		assertEquals("blueTeam",test.getWinningTeam());
	}*/
}
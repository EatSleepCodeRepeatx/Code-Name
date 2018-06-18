package JUnit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Control.*;


public class LocationTest {
		//test for commit
	@Test
	public void testSetTypeOfPerson() {
		Location loc = new Location();
		
		assertFalse(loc.getIsRevealed());
		assertEquals(null, loc.getCodename());
		assertEquals("No_Person_Assigned", loc.getTypeOfPerson());
	}
	//test the 3 constructors
	@Test
	public void testConstructors() {
		Location loc1 = new Location("jackie");
		assertFalse(loc1.getIsRevealed());
		assertEquals("jackie", loc1.getCodename());
		assertEquals("No_Person_Assigned", loc1.getTypeOfPerson());
		
		Location loc2 = new Location("alsojackie", 2);
		assertFalse(loc2.getIsRevealed());
		assertEquals("alsojackie", loc2.getCodename());
		assertEquals("Innocent", loc2.getTypeOfPerson());
		
		Location loc3 = new Location();
		assertFalse(loc3.getIsRevealed());
		assertEquals(null, loc3.getCodename());
		assertEquals("No_Person_Assigned", loc3.getTypeOfPerson());
	}
}



package environment;
import lifeform.MockLifeForm;
import static org.junit.Assert.*;

import org.junit.Test;

import weapon.MockWeapon;

/** 
 * The test cases for the Cell class 
 * @author Dr. Dudley Girard -- first author
 * @author Dr. Alice Armstrong -- revisions
 */ 
public class TestCell 
{ 
	
	 /************ LAB 5 (Singleton) TESTS BELOW THIS POINT ******************/
	/** 
	  * At initialization, the Cell should be empty and not contain a    
	  * LifeForm. 
	  * Refectored from Lab 2 to include 2 weapons
	  */ 
    @Test 
    public void testInitialization() 
    { 
        Cell cell = new Cell(); 
        assertNull(cell.getLifeForm()); 
        assertEquals(0, cell.getWeaponsCount()); 
    } 
    
    /**
     * tests that a cell have have up to 2 weapons and no more than that
     * tests that the two weapons must be different instances
     */
    @Test
    public void testAddWeapons()
    {
    	MockWeapon weapon1 = new MockWeapon(); 
    	MockWeapon weapon2 = new MockWeapon(); 
    	MockWeapon weapon3 = new MockWeapon(); 
    	Cell cell  = new Cell(); 
    	
    	//add one weapon
    	assertTrue(cell.addWeapon(weapon1)); 
    	assertEquals(1, cell.getWeaponsCount()); 
    	
    	//make sure you can't add the same Weapon twice
    	assertFalse(cell.addWeapon(weapon1)); 
    	assertEquals(1, cell.getWeaponsCount()); 
    	
    	//add a second weapon
    	assertTrue(cell.addWeapon(weapon2));
    	assertEquals(2, cell.getWeaponsCount()); 
    	
    	//try to add the same weapon
    	assertFalse(cell.addWeapon(weapon2));
    	assertEquals(2, cell.getWeaponsCount()); 
    	
    	//try to add a new third weapon
    	assertFalse(cell.addWeapon(weapon3));
    	assertEquals(2, cell.getWeaponsCount()); 
    	
    }
    
    @Test
    public void testRemoveWeapon()
    {
    	MockWeapon weapon1 = new MockWeapon(); 
    	MockWeapon weapon2 = new MockWeapon(); 
    	MockWeapon weapon3 = new MockWeapon(); 
    	Cell cell  = new Cell(); 
    	
    	//add two weapons
    	cell.addWeapon(weapon1); 
    	cell.addWeapon(weapon2); 
    	
    	//try to remove a weapon that does not exist
    	assertNull(cell.removeWeapon(weapon3)); 
    	
    	//remove weapon1
    	assertNotNull(cell.removeWeapon(weapon1)); 
    	assertEquals(1, cell.getWeaponsCount()); 
    	
    	//try to remove weapon1 again
    	assertNull(cell.removeWeapon(weapon1)); 
    	
    	//remove weapon2
    	assertNotNull(cell.removeWeapon(weapon2)); 
    	assertEquals(0, cell.getWeaponsCount()); 
    	
    	//try to remove weapon2 again
    	assertNull(cell.removeWeapon(weapon2)); 
    }

 /************ LAB 2 (Strategy) TESTS BELOW THIS POINT ******************/
    
/** 
 * Checks to see if we change the LifeForm held by the Cell that
 * getLifeForm properly responds to this change. 
 */ 
   @Test 
   public void testSetLifeForm() 
   { 
       MockLifeForm bob = new MockLifeForm("Bob", 40); 
       MockLifeForm fred = new MockLifeForm("Fred", 40);
       Cell cell = new Cell();
     // The cell is empty so this should work.
       boolean success = cell.addLifeForm(bob);
       assertTrue(success);
       assertEquals(bob,cell.getLifeForm());
     // The cell is not empty so this should fail.
       success = cell.addLifeForm(fred);
       assertFalse(success);
       assertEquals(bob,cell.getLifeForm());

   } 

   @Test
   public void testRemoveLifeForm()
   {
	   MockLifeForm bob = new MockLifeForm("Bob", 40); 
       Cell cell = new Cell();
       boolean success = cell.addLifeForm(bob);
       assertTrue(success);
       assertEquals(bob,cell.getLifeForm());
       
       //now remove the LifeForm from the cell
       assertNotNull(cell.removeLifeForm()); 
       assertNull(cell.getLifeForm()); 
       
       //now remove from an empty cell
       
       Cell cell2 = new Cell(); 
       assertNull(cell2.getLifeForm()); 
       assertNull(cell2.removeLifeForm()); 
       assertNull(cell2.getLifeForm()); 
    
   }
}


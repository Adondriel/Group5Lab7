/**
 * 
 */
package lifeform;

import static org.junit.Assert.*;

import org.junit.Test;

import environment.Range;

/**
 * Tests the Human class, which is a subclass of LifeForm
 * Humans have armor
 * @author Dr. Alice Armstrong
 *
 */
public class TestHuman 
{
	
	/************ LAB 2 (Strategy) TESTS BELOW THIS POINT ******************/
	@Test
	public void testSpeed()
	{
		Human entity = new Human("Bob", 40, 10); 
		assertEquals(3, entity.getMaxSpeed()); 
	}
	 /************ LAB 3 (Observer) TESTS START HERE (refactored in Lab 4 to handle distance ********/
	private Range r = new Range(); 
	/**
	 * refactored from Lab 2 to include default attack strength
	 */
	@Test
	public void testInitialization() {
		Human entity; 
		//create a Human named Bob with 40 HP and 10 armor points
        entity = new Human("Bob", 40, 10); 
        
        //check default armor
        assertEquals(10, entity.getArmorPoints()); 
        
        //check default attack
        assertEquals(5, entity.getAttackStrength()); 
        
        //check setter
        //we don't need to recheck the LifeForm methods, they are already tested. 
        entity.setArmorPoints(15); 
        assertEquals(15, entity.getArmorPoints()); 
        
        //test that we can't initialize or set armor points < 0
        entity = new Human ("Bob", 10, -5); 
        assertEquals(0, entity.getArmorPoints()); 
        
        entity.setArmorPoints(-2); 
        assertEquals(0, entity.getArmorPoints());
        
        
	}
	/**
	 * tests that Humans can attack with a strength of 5
	 * This test assumes an effective armor strength of 0
	 */
	@Test
	public void testAttackNoArmor()
	{
		MockLifeForm bob;
		Human sheryl; 
		bob = new MockLifeForm("Bob", 40, 5); 
		
		//Sheryl is a Human with no armor
		sheryl = new Human("Sheryl", 50, 0); 
		
		//set the distance to something in range
		r.distance = 2; 
       
       //if Bob attacks Sheryl, Sheryl should take 5 points of damage
       bob.attack(sheryl, r.distance); 
       assertEquals(45, sheryl.getCurrentLifePoints()); 
       
       //if Sheryl attacks Bob, Bob should take 5 points of damage
       sheryl.attack(bob, r.distance); 
       assertEquals(35, bob.getCurrentLifePoints()); 
	}
	
	@Test
	public void testAttackWithArmor()
	{
		MockLifeForm bob;
		Human sheryl; 
		bob = new MockLifeForm("Bob", 40, 5); 
		
		//Sheryl is a Human with an armor of 3
		sheryl = new Human("Sheryl", 50, 3); 
		
		//set the distance to something in range
		r.distance = 1; 
		
       //standard attack, armor absorbs some, but not all of the attack
       //if Bob attacks Sheryl, Sheryl should take 2 points of damage (5 - 3)
       bob.attack(sheryl, r.distance); 
       assertEquals(48, sheryl.getCurrentLifePoints()); 
       
       //armor is stronger than the attack, Sheryl takes no damage
       sheryl.setArmorPoints(10);
       bob.attack(sheryl, r.distance); 
       assertEquals(48, sheryl.getCurrentLifePoints()); 
       
       //armor is exactly the same as the attack, Sheryl takes no damage
       sheryl.setArmorPoints(5);
       bob.attack(sheryl, r.distance);
       assertEquals(48, sheryl.getCurrentLifePoints()); 
      
	}
	
	 /************ LAB 2 (Strategy) TESTS BELOW THIS POINT ******************/
	
	//testInitialization was refactored for Lab3

}

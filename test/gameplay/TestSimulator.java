package gameplay;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.RecoveryRateException;
import exceptions.WeaponException;
import lifeform.Alien;
import lifeform.Human;
import recovery.RecoveryBehavior;
import recovery.RecoveryNone;
import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.Weapon;
import recovery.RecoveryFractional;
import recovery.RecoveryLinear;

/**
 * @author Bradley Solorzano
 * Professor Armstrong - Design Patterns
 * 10/28/15
 * 
 * Test the functionality of Simulator
 * 
 */
public class TestSimulator
{
	
	//test that the correct number of aliens and humans are made.
	//test armor value and recoveryBehaviors are within the ranges allowed
	//test that the correct number of weapons are being created and that 
	//they are not all the same
	//test that simpleTimer updates are being processed.
	//EXTRA test the randomized aliens and weapons.

	/**
	 * test populates the world with LifeForms and Weapons
	 * create 2 LifeForms, placing them on random part
	 * 1 human(randomized) and 1 Alien(randomized)
	 * 2 weapons(randomized) w or w/o attachments
	 * @throws RecoveryRateException 
	 */
	@Test
	public void testSimulatorInitializer() throws RecoveryRateException
	{
		
		/*RecoveryBehavior none = new RecoveryNone();
		Human john = new Human("John", 100, 50);
		Alien zork = new Alien("Zork", 100, none, 0);
		Weapon plasma = new PlasmaCannon();
		Weapon pistol = new Pistol(); */
		
		int numberOfAliens = 1;
		int numberOfHumans = 1;
		
		Simulator matrix = new Simulator(numberOfHumans, numberOfAliens);
		assertEquals(1, matrix.getNumberOfAlien());
		assertEquals(1, matrix.getNumberOfHuman());
		
			
	}
	
	/**
	 * test updateAI updates
	 * all the AIContexts
	 */
	@Test
	public void testSimulatorUpdateAI()
	{
		fail("Not yet implemented");
	}
	
	/**
	 * test that a time update
	 * triggers updateAI
	 */
	@Test
	public void testSimulatorTimeUpdate()
	{
		fail("Not yet implemented");
	}

}

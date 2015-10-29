/**
 * 
 */
package lifeform;

import static org.junit.Assert.*;

import org.junit.Test;

import environment.Range;
import exceptions.RecoveryRateException;
import gameplay.SimpleTimer;
import recovery.RecoveryBehavior;
import recovery.RecoveryFractional;
import recovery.RecoveryLinear;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class TestAlien 
{
	private Range r = new Range(); 
	/************ LAB 6 (Decorator) TESTS BELOW THIS POINT  *********/
	@Test
	/**
	 * This tests the creation of an Alien without a recover behavior
	 * Refactored in Lab 6 to include direction and speed
	 */
	public void testInitialization() throws RecoveryRateException {
		LifeForm a = new Alien("AlienBob", 15); 
		assertEquals(15, a.getMaxLifePoints()); 
		assertEquals(10, a.getAttackStrength()); 
		assertEquals("North", a.getDirection()); 
		assertEquals(2, a.getMaxSpeed()); 
	}
	
	 /************ LAB 3 (Observer) TESTS START HERE (refactored in Lab 4 to handle distance
	 * @throws RecoveryRateException ******************/

	
	/**
	 * makes sure we can 
	 * * create a LifeForm with an attack strength
	 * * check the attack strength
	 * * attack another LifeForm
	 * * see the other LifeForm take the damage
	 * @throws RecoveryRateException 
	 */
	@Test
	public void testAttack() throws RecoveryRateException
	{
		MockLifeForm bob;
		LifeForm sheryl; 
		bob = new MockLifeForm("Bob", 40, 5); 
		sheryl = new Alien("Sheryl", 50); 
		
		//set distance to something in range
		r.distance = 3; 
		
		//check that we can get the attack strength
        assertEquals(5, bob.getAttackStrength()); 
        
        //if Bob attacks Sheryl, Sheryl should take 5 points of damage
        bob.attack(sheryl, r.distance); 
        assertEquals(45, sheryl.getCurrentLifePoints()); 
        
        //if Sheryl attacks Bob, Bob should take 10 points of damage
        sheryl.attack(bob, r.distance); 
        assertEquals(30, bob.getCurrentLifePoints()); 
	}
	
	/**
	 * tests that we can set a recovery rate for an Alien
	 * tests that the default recovery rate is zero (Alien will never recover any life points, even if it has a recovery behavior)
	 * @throws RecoveryRateException 
	 * 
	 */
	@Test
	public void testSetRecoveryRate() throws RecoveryRateException
	{
		//create an Alien that will recover every 5 rounds
		RecoveryBehavior linear = new RecoveryLinear(3); 
		Alien a = new Alien("AlienBob", 15, linear, 5);
		assertEquals(5, a.getRecoveryRate()); 
		
		//check that an Alien created without a recovery rate has the default recover rate of 0
		Alien b = new Alien("AlienBob", 15, linear);
		assertEquals(0, b.getRecoveryRate()); 
	}
	
	/**
	 * tests that negative recovery rates thrown an exception
	 * @throws RecoveryRateException 
	 */
	@Test(expected = RecoveryRateException.class)  
	public void testNegRecovery() throws RecoveryRateException
	{
		//create an Alien that will recover every -5 rounds, should throw an exception
		RecoveryBehavior linear = new RecoveryLinear(3); 
		Alien a = new Alien("AlienBob", 15, linear, -5);
		assertEquals(5, a.getRecoveryRate()); 
	}
	
	/**
	 * tests an Alien's recovery in combat
	 * SimpleTimer is not threaded for this test so we can control the round updates 
	 * @throws RecoveryRateException lazy exception handling here. no exception is expected. 
	 */
	@Test
	public void testCombatRecovery() throws RecoveryRateException
	{
		//create an Alien that will recover every 2 rounds, should not throw an exception
		RecoveryBehavior linear = new RecoveryLinear(3); 
		Alien a = new Alien("AlienBob", 15, linear, 2);
		
		//a timer for a to observer
		SimpleTimer timer = new SimpleTimer(); 
		//Alien should implement TimerObserver
		timer.addTimeObserver(a);
		
		//set to round 1
		timer.timeChanged();
		
		//AlienBob to take some damage (more than the recovery amount)
		a.takeHit(6);
		assertEquals(9, a.getCurrentLifePoints()); 
		
		//set to round 2, AlienBob should recover 3 points
		timer.timeChanged();
		assertEquals(12, a.getCurrentLifePoints()); 
		
		//set to round 3, AlienBob does not recover this round
		timer.timeChanged();
		assertEquals(12, a.getCurrentLifePoints()); 
		
		//set to round 4, AlienBob recovers to 15 points
		timer.timeChanged();
		assertEquals(15, a.getCurrentLifePoints()); 
		
		//kill AlienBob
		a.takeHit(20);
		assertEquals(0, a.getCurrentLifePoints()); 
		
		//set to round 5, AlienBob should not recover (not a recovery round)
		timer.timeChanged();
		assertEquals(0, a.getCurrentLifePoints()); 
		
		//set to round 6, AlienBob should not recover (a recovery round, but AlienBob is dead)
		timer.timeChanged();
		assertEquals(0, a.getCurrentLifePoints()); 
	}
	 /************ LAB 2 (Strategy) TESTS BELOW THIS POINT 
	 * @throws RecoveryRateException ******************/
	@Test
	public void testLinearRecovery() throws RecoveryRateException
	{
		RecoveryBehavior linear = new RecoveryLinear(3); 
		Alien a = new Alien("AlienBob", 15, linear);
		
		//damge the Alien
		a.takeHit(10);
		assertEquals(5, a.getCurrentLifePoints()); 
		
		//recover 3 points
		a.recover(); 
		assertEquals(8, a.getCurrentLifePoints()); 
		
		//recover 3 points
		a.recover(); 
		assertEquals(11, a.getCurrentLifePoints()); 
		
		//recover 3 points
		a.recover(); 
		assertEquals(14, a.getCurrentLifePoints()); 
		
		//recover 1 final point
		a.recover(); 
		assertEquals(15, a.getCurrentLifePoints()); 
		
		//recover 0 points
		a.recover(); 
		assertEquals(15, a.getCurrentLifePoints()); 
	}
	
	@Test
	public void testLinearFractional() throws RecoveryRateException
	{
		//recover 1/5 of current life points each time
		RecoveryBehavior fraction = new RecoveryFractional(.2); 
		Alien a = new Alien("AlienBob", 20, fraction);
		
		//damge the Alien
		a.takeHit(10);
		assertEquals(10, a.getCurrentLifePoints()); 
		
		//recover 2 points (20% of 10)
		a.recover(); 
		assertEquals(12, a.getCurrentLifePoints()); 
		
		//recover 3 points (20% of 12 = 2.4 round to 3)
		a.recover(); 
		assertEquals(15, a.getCurrentLifePoints()); 
		
		//recover 3 points (20% of 15)
		a.recover(); 
		assertEquals(18, a.getCurrentLifePoints()); 
		
		//recover 2 final points (20% of 18 = 3.6, round to 4, cap at 20 points)
		a.recover(); 
		assertEquals(20, a.getCurrentLifePoints()); 
		
		//recover 0 points
		a.recover(); 
		assertEquals(20, a.getCurrentLifePoints()); 
	}
}

package state;
/**
 * Tests the functionality of the HasWeaponState class.
 * Author: Jason LoBianco
 */
import static org.junit.Assert.*;

import org.junit.Test;

import environment.Environment;

public class TestHasWeaponState 
{
	private Environment e = Environment.getEnvironment(10, 10);

	/**
	 * Checks to see that an AI calls search method when there is no target.
	 */
	@Test
	public void testNoTarget() 
	{
		e.clearBoard();
	}
	
	/**
	 * Checks to see that an AI will not shot a team mate.
	 */
	@Test
	public void testSameRace() 
	{
		e.clearBoard();
	}
	
	/**
	 * Checks to see that an AI will attack a correct target.
	 */
	@Test
	public void testTargetDiffRace() 
	{
		e.clearBoard();
	}
	
	/**
	 * Checks to see that an AI will attack a correct target and move to OutOfAmmoState.
	 */
	@Test
	public void testTargetDiffRaceWithOneShot() 
	{
		e.clearBoard();
	}
	
	/**
	 * Checks to see that an AI will move to DeadState when dead.
	 */
	@Test
	public void testDead() 
	{
		e.clearBoard();
	}
	
	/**
	 * Checks to see what an AI will do if target is out of range.
	 */
	@Test
	public void testTargetOutOfRange() 
	{
		e.clearBoard();
	}
}
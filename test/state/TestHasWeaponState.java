package state;
/**
 * Tests the functionality of the HasWeaponState class.
 * Author: Jason LoBianco
 */
import static org.junit.Assert.*;
import lifeform.Alien;
import lifeform.Human;

import org.junit.Test;

import recovery.RecoveryNone;
import weapon.Pistol;
import environment.Environment;
import exceptions.RecoveryRateException;
import exceptions.WeaponException;

public class TestHasWeaponState 
{
	private Environment e = Environment.getEnvironment(20, 20);

	/**
	 * Checks to see that an AI calls search method when there is no target.
	 */
	@Test
	public void testNoTarget() 
	{
		Human h = new Human("Bob", 20, 0);
		Pistol p = new Pistol();
		AIContext ai = new AIContext(h);
		HasWeaponState hws = new HasWeaponState(ai);
		h.pickUpWeapon(p);
		ai.setState(ai.getHasWeaponState());
		e.addLifeForm(h, 5, 5);
		String oldDirection = h.getDirection();
		hws.executeAction();
		assertEquals(ai.getHasWeaponState(), ai.getCurrentState());
		assertNotEquals(oldDirection, h.getDirection());
		e.clearBoard();
	}
	
	/**
	 * Checks to see that an AI will not shot a team mate.
	 */
	@Test
	public void testSameRace() 
	{
		Human h = new Human("Bob", 20, 0);
		Human fred = new Human("Fred", 30, 0);
		Pistol p = new Pistol();
		AIContext ai = new AIContext(h);
		HasWeaponState hws = new HasWeaponState(ai);
		h.pickUpWeapon(p);
		ai.setState(ai.getHasWeaponState());
		e.addLifeForm(h, 5, 5);
		e.addLifeForm(fred, 4, 5);
		hws.executeAction();
		assertEquals(30, fred.getCurrentLifePoints());
		e.clearBoard();
	}
	
	/**
	 * Checks to see that an AI will attack a correct target.
	 * @throws RecoveryRateException 
	 */
	@Test
	public void testTargetDiffRace() throws RecoveryRateException 
	{
		Human h = new Human("Bob", 20, 0);
		RecoveryNone r1 = new RecoveryNone();
		Alien a = new Alien("Fred", 30, r1);
		Pistol p = new Pistol();
		AIContext ai = new AIContext(h);
		HasWeaponState hws = new HasWeaponState(ai);
		h.pickUpWeapon(p);
		ai.setState(ai.getHasWeaponState());
		e.addLifeForm(h, 5, 5);
		e.addLifeForm(a, 4, 5);
		hws.executeAction();
		assertEquals(19, a.getCurrentLifePoints());
		e.clearBoard();
	}
	
	/**
	 * Checks to see that an AI will attack a correct target and move to OutOfAmmoState.
	 * @throws RecoveryRateException 
	 * @throws WeaponException 
	 */
	@Test
	public void testTargetDiffRaceWithOneShot() throws RecoveryRateException, WeaponException 
	{
		Human h = new Human("Bob", 20, 0);
		RecoveryNone r1 = new RecoveryNone();
		Alien a = new Alien("Fred", 30, r1);
		Pistol p = new Pistol();
		p.fire(5);
		assertEquals(1, p.getShotsLeft());
		AIContext ai = new AIContext(h);
		HasWeaponState hws = new HasWeaponState(ai);
		h.pickUpWeapon(p);
		ai.setState(ai.getHasWeaponState());
		e.addLifeForm(h, 5, 5);
		e.addLifeForm(a, 4, 5);
		hws.executeAction();
		assertEquals(19, a.getCurrentLifePoints());
		e.clearBoard();
	}
	
	/**
	 * Checks to see that an AI will move to DeadState when dead.
	 */
	@Test
	public void testDead() 
	{
		Human h = new Human("Bob", 0, 0);
		Pistol p = new Pistol();
		AIContext ai = new AIContext(h);
		HasWeaponState hws = new HasWeaponState(ai);
		h.pickUpWeapon(p);
		ai.setState(ai.getHasWeaponState());
		e.addLifeForm(h, 5, 5);
		hws.executeAction();
		assertEquals(ai.getDeadState(), ai.getCurrentState());
		e.clearBoard();
	}
	
	/**
	 * Checks to see what an AI will do if target is out of range.
	 * @throws RecoveryRateException 
	 */
	@Test
	public void testTargetOutOfRange() throws RecoveryRateException 
	{
		Human h = new Human("Bob", 20, 0);
		RecoveryNone r1 = new RecoveryNone();
		Alien a = new Alien("Fred", 30, r1);
		Pistol p = new Pistol();
		AIContext ai = new AIContext(h);
		HasWeaponState hws = new HasWeaponState(ai);
		h.pickUpWeapon(p);
		ai.setState(ai.getHasWeaponState());
		e.addLifeForm(h, 15, 5);
		e.addLifeForm(a, 0, 5);
		hws.executeAction();
		assertEquals(30, a.getCurrentLifePoints());
		e.clearBoard();
	}
	
	/********************************************************
	 *       BONUS TEST                                     *
	 ********************************************************/
	
	/**
	 * Checks to see that an AI will move to the NoAmmoState when out of ammo.
	 * @throws RecoveryRateException 
	 */
	@Test
	public void testNoAmmoState() throws RecoveryRateException
	{
		Human h = new Human("Bob", 20, 0);
		Pistol p = new Pistol();
		RecoveryNone r1 = new RecoveryNone();
		Alien a = new Alien("Fred", 30, r1);
		AIContext ai = new AIContext(h);
		HasWeaponState hws = new HasWeaponState(ai);
		p.setCurrentAmmo(0);
		h.pickUpWeapon(p);
		ai.setState(ai.getHasWeaponState());
		e.addLifeForm(h, 5, 5);
		e.addLifeForm(a, 4, 5);
		hws.executeAction();
		assertEquals(ai.getOutOfAmmoState(), ai.getCurrentState());
		e.clearBoard();
	}
}
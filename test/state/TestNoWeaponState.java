package state;
/**
 * Tests the functionality of the NoWeaponState class.
 * Author: Jason LoBianco
 */
import static org.junit.Assert.*;

import lifeform.Human;

import org.junit.Test;

import weapon.Pistol;
import environment.Environment;

public class TestNoWeaponState 
{
	private Environment e = Environment.getEnvironment(10, 10);

	/**
	 * Checks to see that an AI LifeForm will pick up a weapon.
	 */
	@Test
	public void testWeaponInCell() 
	{
		Human h = new Human("Bob", 20, 0);
		AIContext ai = new AIContext(h);
		NoWeaponState nws = new NoWeaponState(ai);
		Pistol p = new Pistol();
		e.addLifeForm(h, 2, 2);
		e.addWeapon(p, 2, 2);
		nws.executeAction();
		assertEquals(p, ai.l.getWeapon());
		assertEquals(ai.getHasWeaponState(), ai.getCurrentState());
		e.clearBoard();
	}
	
	/**
	 * Checks to see that an AI LifeForm will call search method when no weapon in a cell.
	 */
	@Test
	public void testNoWeaponInCell()
	{
		Human h = new Human("Bob", 20, 0);
		AIContext ai = new AIContext(h);
		NoWeaponState nws = new NoWeaponState(ai);
		e.addLifeForm(h, 5, 5);
		nws.executeAction();
		assertNull(ai.l.getWeapon());
		assertEquals(ai.getNoWeaponState(), ai.getCurrentState());
		assertNull(e.getLifeForm(5, 5));
		e.clearBoard();
	}
	
	/**
	 * Checks to see that an AI LifeForm will move to the dead state when dead.
	 */
	@Test
	public void testDead()
	{
		Human h = new Human("Bob", 0, 0);
		AIContext ai = new AIContext(h);
		ai.currentState = ai.noWeapon;
		e.addLifeForm(h, 2, 2);
		ai.executeAction();
		assertEquals(ai.getDeadState(), ai.getCurrentState());
		e.clearBoard();
	}
}
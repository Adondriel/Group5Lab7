/**
 * 
 */
package command;

import static org.junit.Assert.*;
import lifeform.Alien;
import lifeform.Human;
import lifeform.LifeForm;

import org.junit.Before;
import org.junit.Test;

import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.Weapon;
import environment.Environment;
import exceptions.RecoveryRateException;
import exceptions.WeaponException;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class TestCommand {
	Environment e; 
	final int NUMROWS = 12; 
	final int NUMCOLS = 12; 
	Human h, h1, h2, h3, h4; 
	Alien a, a1, a2, a3, a4; 
	Pistol p, p1, p2, p3; 
	PlasmaCannon pC, pC1, pC2, pC3; 
	
	/**
	 * clear the board before each test to avoid Singleton collisions
	 * creates the following Cells
	 * (0, 0) Human facing south, with Pistol (loaded), no weapons in the cell
	 * 
	 * (2, 0) Alien facing North, with Plasma Cannon
	 * 
	 * (0, 2) Alien facing West, with a Pistol
	 * 
	 * (0, 3) Alien facing West, no weapon
	 * 
	 * (0, 4) Human facing East with a Pistol
	 * 
	 * (0, 6) Alien facing South, no weapon
	 */
	@Before
	public void clearBoard()
	{
		e = Environment.getEnvironment(NUMROWS, NUMCOLS); 
		e.clearBoard();
		
		//(0, 0) Human facing south, with Pistol (loaded), no weapons in the cell
		h = new Human("Bob", 100, 3); 
		p = new Pistol(); 
		h.pickUpWeapon(p); 
		h.setDirection("South"); 
		e.addLifeForm(h, 0, 0); 
		
		//(0, 2) Alien facing North, no weapon, one weapon in the cell
		try {
			a = new Alien("Alien1", 75);
			pC = new PlasmaCannon(); 
			a.pickUpWeapon(pC); 
			e.addLifeForm(a, 2, 0); 
		} catch (RecoveryRateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		//(0, 2) Alien facing West, with a Pistol
		try {
			a1 = new Alien("Alien2", 75);
			a1.setDirection("West"); 
			p1 = new Pistol();  
			a1.pickUpWeapon(p1); 
			e.addLifeForm(a1, 0, 2); 
		} catch (RecoveryRateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		//(0, 3) Alien facing West, no weapon
		try {
			a2 = new Alien("Alien3", 75);
			a2.setDirection("West"); 
			e.addLifeForm(a2, 0, 3); 
		} catch (RecoveryRateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//(0, 4) Human facing East with a Pistol
		h1 = new Human("Bob2", 60, 5); 
		h1.setDirection("East"); 
		p2 = new Pistol(); 
		h1.pickUpWeapon(p2); 
		e.addLifeForm(h1, 0, 4);
		
		//(0, 6) Alien facing South, no weapon
		try {
			a3 = new Alien("Alien4", 75);
			a3.setDirection("South"); 
			e.addLifeForm(a3, 0, 6); 
		} catch (RecoveryRateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	@Test
	public void testAttackCommand()
	{
		AttackCmd attack = new AttackCmd(); 

		//attack north with weapon a --> h
		attack.execute(2,  0);
		assertEquals(3, pC.getCurrentAmmo()); 
		assertEquals(53, h.getCurrentLifePoints());
		
		//attack south with weapon h --> a
		attack.execute(0, 0); 
		assertEquals(9, p.getCurrentAmmo()); 
		assertEquals(65, a.getCurrentLifePoints()); 
		
		//attack west no weapon a2 --> a1
		attack.execute(0, 3);
		assertEquals(65, a1.getCurrentLifePoints()); 
		
		//attack east with weapon h1 --> a3
		attack.execute(0, 4);
		assertEquals(65, a3.getCurrentLifePoints()); 
		assertEquals(9, p2.getCurrentAmmo()); 
	}
	/**
	 * if there is a lifeform holding a weapon, tests that a weapon reloads
	 * if there is no lifeform, nothing happens (even if there are weapons in the cell)
	 * if there is a lifeform without a weapon, nothing happens 
	 * @throws WeaponException 
	 */
	@Test
	public void testReload() throws WeaponException {
		//fire a weapon down, then reload is
		p = new Pistol(); 
		p.fire(10); 
		p.fire(10); 
		p.updateTime(1);
		h.dropWeapon(); 
		h.pickUpWeapon(p); 
		ReloadCmd reload = new ReloadCmd(); 
		reload.execute(0,0); 
		assertEquals(10, p.getCurrentAmmo()); 
		
		
		//try to reload in a cell with no lifeform
		reload.execute(1,1); 

		//try to reload in a cell with a lifeform without a weapon
		reload.execute(0,3); 
		
		
	}
	
	/**
	 * tests the Move command
	 * a cell with a lifeform will have that lifeform move (the movement is not fully tested here, but in TestEnvironment)
	 * a cell with no lifeform remains unchanged
	 */
	@Test
	public void testMove()
	{
		MoveCmd move = new MoveCmd(); 
		move.execute(0, 0); 
		assertNull(e.getLifeForm(0, 0)); 
		assertNotNull(e.getLifeForm(3, 0)); 
		
		assertNull(e.getLifeForm(1, 1)); 
		//nothin is here
		move.execute(1, 1);
		assertNull(e.getLifeForm(1, 1)); 
		
	}

	@Test
	public void testTurnCommand()
	{
		TurnNorthCmd turnN = new TurnNorthCmd(); 
		TurnSouthCmd turnS = new TurnSouthCmd();
		TurnEastCmd turnE = new TurnEastCmd(); 
		TurnWestCmd turnW = new TurnWestCmd(); 
		
		//test on cells with a lifeform
		turnN.execute(0, 0); 
		assertEquals(e.getLifeForm(0, 0).getDirection(), "North"); 
		
		turnS.execute(0, 0); 
		assertEquals(e.getLifeForm(0, 0).getDirection(), "South");
		
		turnE.execute(0, 0); 
		assertEquals(e.getLifeForm(0, 0).getDirection(), "East");
		
		turnW.execute(0, 0); 
		assertEquals(e.getLifeForm(0, 0).getDirection(), "West");
		
		//test on empty cells
		turnN.execute(1,  1);
		assertNull(e.getLifeForm(1, 1)); 
		turnS.execute(1,  1);
		assertNull(e.getLifeForm(1, 1)); 
		turnE.execute(1,  1);
		assertNull(e.getLifeForm(1, 1)); 
		turnW.execute(1,  1);
		assertNull(e.getLifeForm(1, 1)); 
	}
	
	/**
	 * tests the Drop Command 
	 */
	@Test
	public void testDropCommand()
	{
		DropCmd drop = new DropCmd(); 
		
		//drop in a cell with no weapons
		LifeForm life = e.getLifeForm(0, 0); 
		assertTrue(life.hasWeapon()); 
		drop.execute(0, 0); 
		assertFalse(life.hasWeapon()); 
		
		Weapon[] weapons = e.getWeapons(0, 0); 
		assertNotNull(weapons[0]); 
		assertNull(weapons[1]); 
		
		//drop in a cell with one weapon
		life.pickUpWeapon(weapons[0]); 
		e.removeWeapon(weapons[0], 0, 0);
		Pistol p3 = new Pistol(); 
		e.addWeapon(p3, 0, 0); 
		drop.execute(0, 0); 
		assertFalse(life.hasWeapon()); 
		
		weapons = e.getWeapons(0, 0); 
		assertNotNull(weapons[0]); 
		assertNotNull(weapons[1]); 
		
		//drop in a cell with two weapons (weapon not dropped)
		life.pickUpWeapon(weapons[0]); 
		e.removeWeapon(weapons[0], 0, 0);
		Pistol p4 = new Pistol(); 
		e.addWeapon(p4, 0, 0); 
		drop.execute(0, 0); 
		assertTrue(life.hasWeapon()); 
		
		weapons = e.getWeapons(0, 0); 
		assertNotNull(weapons[0]); 
		assertNotNull(weapons[1]); 
		
		//drop from a lifeform with no weapon (a2 in 0,3)
		assertFalse(a2.hasWeapon()); 
		weapons = e.getWeapons(0, 3); 
		assertNull(weapons[0]); 
		assertNull(weapons[1]); 
		drop.execute(0, 3);
		assertFalse(a2.hasWeapon()); 
		weapons = e.getWeapons(0, 3); 
		assertNull(weapons[0]); 
		assertNull(weapons[1]); 
		
		//drop from a cell with no life form
		drop.execute(2,  2);
		
	}
	
	/**
	 * test the Acquire1 command. Picks up a weapon from slot 1. 
	 */
	@Test
	public void testAcquire1()
	{
		Acquire1Cmd acquire = new Acquire1Cmd(); 
		
		//acquire in a cell with no weapons
		//weapon remains unchanged, cell weapons remain unchanged
		LifeForm life = e.getLifeForm(0, 0); 
		assertTrue(life.hasWeapon());
		Weapon[] weapons = e.getWeapons(0, 0); 
		assertNull(weapons[0]); 
		assertNull(weapons[1]); 
		assertEquals(p, life.getWeapon()); 
		acquire.execute(0, 0); 
		assertTrue(life.hasWeapon()); 
		assertEquals(p, life.getWeapon());
		weapons = e.getWeapons(0, 0); 
		assertNull(weapons[0]); 
		assertNull(weapons[1]);
		
		//acquire when the lifeform has no weapon, and no weapons in cell (0,3)
		life = e.getLifeForm(0, 3); 
		assertFalse(life.hasWeapon());
		weapons = e.getWeapons(0, 3); 
		assertNull(weapons[0]); 
		assertNull(weapons[1]); 
		acquire.execute(0, 3); 
		assertFalse(life.hasWeapon()); 
		weapons = e.getWeapons(0, 3); 
		assertNull(weapons[0]); 
		assertNull(weapons[1]);
		
		//acquire when the lifeform has a weapon and the cell has 1 weapon, in slot#1
		life = e.getLifeForm(0, 0);
		assertEquals(p, life.getWeapon());
		Pistol p3 = new Pistol(); 
		e.addWeapon(p3, 0, 0); 
		weapons = e.getWeapons(0, 0); 
		assertNotNull(weapons[0]); 
		assertNull(weapons[1]); 
		acquire.execute(0, 0); 
		assertTrue(life.hasWeapon()); 
		assertEquals(p3, life.getWeapon());
		weapons = e.getWeapons(0, 0); 
		assertNotNull(weapons[0]); 
		assertNull(weapons[1]);
	}
	
	/**
	 * test the Acquire2 command. Picks up a weapon from slot 2. 
	 */
	@Test
	public void testAcquire2()
	{
		Acquire2Cmd acquire = new Acquire2Cmd(); 
		
		//acquire in a cell with no weapons
		//weapon remains unchanged, cell weapons remain unchanged
		LifeForm life = e.getLifeForm(0, 0); 
		assertTrue(life.hasWeapon());
		Weapon[] weapons = e.getWeapons(0, 0); 
		assertNull(weapons[0]); 
		assertNull(weapons[1]); 
		assertEquals(p, life.getWeapon()); 
		acquire.execute(0, 0); 
		assertTrue(life.hasWeapon()); 
		assertEquals(p, life.getWeapon());
		weapons = e.getWeapons(0, 0); 
		assertNull(weapons[0]); 
		assertNull(weapons[1]);
		
		//acquire when the lifeform has no weapon, and no weapons in cell (0,3)
		life = e.getLifeForm(0, 3); 
		assertFalse(life.hasWeapon());
		weapons = e.getWeapons(0, 3); 
		assertNull(weapons[0]); 
		assertNull(weapons[1]); 
		acquire.execute(0, 3); 
		assertFalse(life.hasWeapon()); 
		weapons = e.getWeapons(0, 3); 
		assertNull(weapons[0]); 
		assertNull(weapons[1]);
		
		//acquire when the lifeform has a weapon and the cell has 2 weapons
		life = e.getLifeForm(0, 0);
		assertEquals(p, life.getWeapon());
		Pistol p3 = new Pistol();
		Pistol p4 = new Pistol(); 
		e.addWeapon(p3, 0, 0); 
		e.addWeapon(p4, 0, 0); 
		weapons = e.getWeapons(0, 0); 
		assertNotNull(weapons[0]); 
		assertNotNull(weapons[1]); 
		acquire.execute(0, 0); 
		assertTrue(life.hasWeapon()); 
		assertEquals(p4, life.getWeapon());
		weapons = e.getWeapons(0, 0); 
		assertNotNull(weapons[0]); 
		assertNotNull(weapons[1]);
		
		
	}
}

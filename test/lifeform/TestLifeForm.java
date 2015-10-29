package lifeform;
import static org.junit.Assert.*; 

import org.junit.Before;
import org.junit.Test; 

import environment.Environment;
import environment.Range;
import exceptions.EnvironmentException;
import exceptions.WeaponException;
import gameplay.SimpleTimer;
import weapon.MockWeapon;
import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.Weapon;
 
/** 
 * Tests the functionality provided by the Book class 
 * 
 */ 
public class TestLifeForm 
{ 
	private final int NUMROWS = 100; 
	private final int NUMCOLS = 100; 
	private Environment e; 
	
	
	/************ LAB 6 (Command) TESTS START HERE ******************/
	/**
	 * tests that the default direction and speed are correct at Initialization
	 */
	@Test
	public void testDirectionAndSpeed()
	{
		MockLifeForm bob = bob = new MockLifeForm("Bob", 40, 5); 
		assertEquals(3, bob.getMaxSpeed()); 
		assertEquals("North", bob.getDirection()); 
	}
	
	/**
	 * tests that only valid directions are accepted
	 * a valid input makes the change
	 * an invalid input returns false and the direction does not change
	 */
	@Test
	public void testChangeDirection()
	{
		MockLifeForm bob = new MockLifeForm("Bob", 40, 5); 
		assertEquals("North", bob.getDirection()); 
		
		//change to the valid directions
		assertTrue(bob.setDirection("South")); 
		assertEquals("South", bob.getDirection()); 
		assertTrue(bob.setDirection("West")); 
		assertEquals("West", bob.getDirection()); 
		assertTrue(bob.setDirection("East")); 
		assertEquals("East", bob.getDirection());
		assertTrue(bob.setDirection("North")); 
		assertEquals("North", bob.getDirection()); 
		
		//check that there is no problem changing to the existing direction
		assertTrue(bob.setDirection("North")); 
		assertEquals("North", bob.getDirection());
		
		//check for invalid input
		assertFalse(bob.setDirection("Up")); 
		assertEquals("North", bob.getDirection());
		
	}
	
	@Test
	public void testGetWeapon()
	{
		MockLifeForm bob = new MockLifeForm("Bob", 40, 5); 
		MockWeapon gun = new MockWeapon(); 
		
		bob.pickUpWeapon(gun); 
		
		//check that we get the Weapon 
		assertEquals(gun, bob.getWeapon()); 
		
		//check that it returns null if there is no weapon
		bob.dropWeapon(); 
		assertFalse(bob.hasWeapon()); 
		assertNull(bob.getWeapon()); 
	}
	
	/**
	 * refactored maxLifePoints up to the LifeForm class in Lab 6
	 */
	@Test
	public void testGetMaxLife()
	{
		MockLifeForm bob = new MockLifeForm("Bob", 40, 5); 
		
		assertEquals(40, bob.getMaxLifePoints()); 
	}
	
	@Test
	public void testAttackRange() throws WeaponException
	{
		MockLifeForm bob = new MockLifeForm("Bob", 40, 5); 
		
		//with no weapon, bob attacks in touch range
		assertEquals(5, bob.getAttackRange()); 
		
		//with a weapon, bob attacks at the weapon's range
		Pistol p = new Pistol(); 
		bob.pickUpWeapon(p); 
		assertEquals(50, bob.getAttackRange());
		
		//fire two shots, then pistol is out of shots for this round
		p.fire(10); 
		p.fire(10); 
		//bob attacks at touch range
		assertEquals(5, bob.getAttackRange()); 
		
		//reset the round
		p.updateTime(1);
		assertEquals(50, bob.getAttackRange()); 
		
		//empty the clip
		for (int i = 0; i< 48; i++)
		{
			p.fire(10); 
			p.updateTime(1);
		}
		
		//bob attacks with touch range because the weapon is out of ammo
		assertEquals(5, bob.getAttackRange()); 
	}
	
	/************ LAB 5 (Singleton) TESTS START HERE ******************/
	/**
	 * clear the board before each test to avoid Singleton collisions
	 * in the Cells of the Environment
	 */
	@Before
	public void clearBoard()
	{
		e = Environment.getEnvironment(NUMROWS, NUMCOLS); 
		e.clearBoard();
	}
	
	@Test
	public void testLocation()
	{
		MockLifeForm bob; 
		bob = new MockLifeForm("Bob", 40, 5); 
		
		//test the LifeForms start at -1, -1
		assertEquals(-1, bob.getRow()); 
		assertEquals(-1, bob.getCol()); 
		
		//can reset the location
		bob.setLocation(0, 4);
		assertEquals(0, bob.getRow());
		assertEquals(4, bob.getCol()); 
		
		//anything < -1 means the coordinates go unchanged
		bob.setLocation(-5,  -3);
		assertEquals(0, bob.getRow());
		assertEquals(4, bob.getCol()); 
	}
	
	/************ LAB 5 (Singleton) Refactoring of ******************/
	/************ LAB 4 (Decorator) TESTS START HERE ******************/
	//private Range r = new Range(); 
	
	/**
	 * make sure that a LifeForm with no weapon cannot attack
	 * an opponent that is more that 5 feet away
	 * @throws EnvironmentException 
	 */
	@Test
	public void testHandToHandOutOfRange() throws EnvironmentException
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS); 
		MockLifeForm bob;
		MockLifeForm sheryl; 
		bob = new MockLifeForm("Bob", 40, 5); 
		sheryl = new MockLifeForm("Sheryl", 50, 7); 
		
		//set the distance to an acceptable attack range
		//r.distance = 6; 
		//place Bob and Sheryl too far apart to attack (10)
		e.addLifeForm(bob, 0, 0); 
		e.addLifeForm(sheryl, 0, 2); 
		
		//if Bob attacks Sheryl, Sheryl should take 0 points of damage
		//because they are too far apart
        bob.attack(sheryl, e.getDistance(bob, sheryl)); 
        assertEquals(50, sheryl.getCurrentLifePoints()); 
	}
	
	@Test
	public void testAttackWithWeapon() throws EnvironmentException
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS);
		MockLifeForm bob;
		MockLifeForm sheryl; 
		bob = new MockLifeForm("Bob", 100, 1); 
		sheryl = new MockLifeForm("Sheryl", 50, 2); 
		
		Pistol p = new Pistol(); 
		PlasmaCannon pC = new PlasmaCannon(); 
		
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(p);
		timer.addTimeObserver(pC);
		
		//give Bob the Pistol and Sheryl the PlasmaCannon
		bob.pickUpWeapon(p); 
		sheryl.pickUpWeapon(pC); 
		
		//set distance to something in range of both weapons
		//r.distance = 35; 
		//place Bob and Sheryl too far apart to attack approx 35
		e.addLifeForm(bob, 0, 0); 
		e.addLifeForm(sheryl, 5, 6); 
		
		//bob should to 5 points of damage to Sheryl
		bob.attack(sheryl, e.getDistance(bob, sheryl));
		assertEquals(46, sheryl.getCurrentLifePoints()); 
		
		//sheryl should to 50 points of damage to Bob
		sheryl.attack(bob, e.getDistance(bob, sheryl));
		assertEquals(50, bob.getCurrentLifePoints()); 
		assertEquals(pC.getCurrentAmmo(), 3); 
		
		//set distance in range of Pistol but out of range of Plasma Cannon
		//r.distance = 45;  
		e.removeLifeForm(5, 6); 
		e.addLifeForm(sheryl, 6, 6);
		
		//bob should do 2 points of damage to Sheryl
		bob.attack(sheryl, e.getDistance(bob, sheryl));
		assertEquals(43, sheryl.getCurrentLifePoints()); 
		
		//sheryl is too far away to shoot bob, should do 0 points of damage
		//update the time, so all shots can be fired
		timer.timeChanged();
		sheryl.attack(bob, e.getDistance(bob, sheryl));
		assertEquals(50, bob.getCurrentLifePoints()); 
		assertEquals(pC.getCurrentAmmo(), 2); 
		
		//update the time, so all shots can be fired
		timer.timeChanged();
		//empty the Plasma Cannon clip
		sheryl.attack(bob, e.getDistance(bob, sheryl));
		assertEquals(pC.getCurrentAmmo(), 1); 
		//update the time, so all shots can be fired
		timer.timeChanged();
		sheryl.attack(bob, e.getDistance(bob, sheryl));
		assertEquals(pC.getCurrentAmmo(), 0); 
		
		//reset the distance to a ranged touch attack will work
		//r.distance = 3;
		e.removeLifeForm(6, 6); 
		e.addLifeForm(sheryl, 0, 1); 
		//sheryl attacks bob hand to hand
		sheryl.attack(bob, e.getDistance(bob, sheryl));
		assertEquals(48, bob.getCurrentLifePoints()); 
	}
	/************ LAB 3 (Observer) Tests were refactored in Lab4 and Lab5 to hand ranged touch attacks *****/
	
	/**
	 * makes sure we can 
	 * * create a LifeForm with an attack strength
	 * * check the attack strength
	 * * attack another LifeForm that is within hand-to-hand attack range
	 * * see the other LifeForm take the damage
	 * @throws EnvironmentException 
	 */
	@Test
	public void testAttack() throws EnvironmentException
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS);
		MockLifeForm bob;
		MockLifeForm sheryl; 
		bob = new MockLifeForm("Bob", 40, 5); 
		sheryl = new MockLifeForm("Sheryl", 50, 7); 
		
		//set the distance to an acceptable attack range
		//r.distance = 4; 
		e.addLifeForm(sheryl, 0, 0); 
		e.addLifeForm(bob, 0, 1);
		
		//check that we can get the attack strength
        assertEquals(5, bob.getAttackStrength()); 
        
        //if Bob attacks Sheryl, Sheryl should take 5 points of damage
        bob.attack(sheryl, e.getDistance(bob, sheryl)); 
        assertEquals(45, sheryl.getCurrentLifePoints()); 
        
        //if Sheryl attacks Bob, Bob should take 7 points of damage
        sheryl.attack(bob, e.getDistance(bob, sheryl)); 
        assertEquals(33, bob.getCurrentLifePoints()); 
	}

	
	/**
	 * makes sure that a LifeForm with zero life point does no damage
	 * @throws EnvironmentException 
	 */
	@Test
	public void testDeadCantAttack() throws EnvironmentException
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS);
		MockLifeForm bob;
		MockLifeForm sheryl; 
		
		//Bob is dead
		bob = new MockLifeForm("Bob", 0, 5); 
		sheryl = new MockLifeForm("Sheryl", 50, 7); 
		
		//set the distance to an acceptable attack range
		//r.distance = 4; 
		e.addLifeForm(sheryl, 0, 0); 
		e.addLifeForm(bob, 0, 1);
	
        //if Bob attacks Sheryl, Sheryl should take 0 points of damage
        bob.attack(sheryl, e.getDistance(bob, sheryl)); 
        assertEquals(50, sheryl.getCurrentLifePoints()); 
	}
	
	/************ LAB 4 (Decorator) TESTS START HERE ******************/
	/**
	 * makes sure a LifeForm can pick up a Weapon
	 * makes sure a LifeForm can only hold one Weapon at a time
	 * makes sure a Weapon can be dropped and returned to the caller
	 */
	@Test
	public void testWeaponPickUpAndDrop()
	{
		MockLifeForm bob;
		bob = new MockLifeForm("Bob", 40, 5); 
		MockWeapon gun = new MockWeapon(); 
		
		//check that bob is not holding a weapon
		assertFalse(bob.hasWeapon()); 
		
		//pick up the gun
		assertTrue(bob.pickUpWeapon(gun)); 
		
		//check that bob has a weapon
		assertTrue(bob.hasWeapon()); 
		
		//try to pick up the gun again
		assertFalse(bob.pickUpWeapon(gun)); 
		
		//drop the gun & pass it back
		MockWeapon temp = (MockWeapon)bob.dropWeapon(); 
		assertEquals(temp, gun); 
		
		//check the bob no longer has the gun
		assertFalse(bob.hasWeapon()); 
		
		//pick up the gun again
		assertTrue(bob.pickUpWeapon(gun)); 
		
		//check the bob has the gun again
		assertTrue(bob.hasWeapon()); 
		
	}

	


/************ LAB 2 (Strategy) TESTS BELOW THIS POINT ******************/
	
 /** 
  * When a LifeForm is created, it should know its name and how
  * many life points it has. 
  */ 
    @Test 
    public void testInitialization() 
    { 
        MockLifeForm entity; 
        entity = new MockLifeForm("Bob", 40); 
        assertEquals("Bob", entity.getName()); 
        assertEquals(40, entity.getCurrentLifePoints()); 
    } 
    
    @Test
    public void testTakeHit()
    {
    	 MockLifeForm entity; 
         entity = new MockLifeForm("Bob", 40); 
         
         //take a 5 point hit 
         entity.takeHit(5); 
         assertEquals(35, entity.getCurrentLifePoints()); 
         
         //take a 1 point hit
         entity.takeHit(1); 
         assertEquals(34, entity.getCurrentLifePoints());
         
         //take a 34 point hit (down to 0 HP)
         entity.takeHit(34); 
         assertEquals(0, entity.getCurrentLifePoints());
         
         //take another hit (HP can't go below 0)
         entity.takeHit(5); 
         assertEquals(0, entity.getCurrentLifePoints());
    }
} 

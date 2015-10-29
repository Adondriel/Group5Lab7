/**
 * 
 */
package weapon;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.AttachmentException;
import exceptions.WeaponException;
import gameplay.SimpleTimer;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class TestWeapons {

	/**
	 * tests for MockWeapon initialization, firing, and reloading
	 * @throws WeaponException (lazy error handling, one exception is expected)
	 */
	@Test(expected = WeaponException.class)
	public void testMockWeapon() throws WeaponException{
		MockWeapon m = new MockWeapon(); 
		
		//need to track the rounds now, we are going to update the round after every firing
		//tests for timer issues are separate
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(m);
		
		//set to round1
		timer.timeChanged();
		
		
		//check MockWeapon basic getters
		assertEquals(5, m.getBaseDamage()); 
		assertEquals(40, m.getMaxRange()); 
		assertEquals(3, m.getRateOfFire()); 
		assertEquals(20, m.getMaxAmmo()); 
		assertEquals(20, m.getCurrentAmmo()); 
		
		//check MockWeapon key methods
		assertEquals(0, m.getNumAttachments()); 
		
		//check toString
		assertEquals("MockWeapon", m.toString()); 

		//fire the MockWeapon once at something in range (30)
		//damage should be 5 every time until out of ammo
		assertEquals(5, m.fire(30));
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(19, m.getCurrentAmmo());
		
		//fire the MockWeapon at something out of range (60)
		//damage should be zero
		assertEquals(0, m.fire(60));
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(18, m.getCurrentAmmo());
		
		//fire the MockWeapon once at something just in range (40)
		//damage should be 2
		assertEquals(5, m.fire(40));
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(17, m.getCurrentAmmo());
		
		//fire the MockWeapon once at something at point blank range (0)
		//damage should be 2
		assertEquals(5, m.fire(0));
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(16, m.getCurrentAmmo());
		
		//fire until the clip is empty
		for (int i = 16; i > 0; i--)
		{
			m.fire(10); 
			timer.timeChanged();
		}
		
		//fire MockWeapon when empty
		assertEquals(0, m.fire(10));
		timer.timeChanged();
		//check that ammo is still 0
		assertEquals(0, m.getCurrentAmmo());
		//reload when empty
		m.reload();
		assertEquals(20, m.getCurrentAmmo());
		//partially empty the clip
		m.fire(10);
		timer.timeChanged();
		m.fire(10);
		timer.timeChanged();
		m.fire(10); 
		timer.timeChanged();
		//reload when only partially empty
		assertEquals(17, m.getCurrentAmmo());
		m.reload();
		assertEquals(20, m.getCurrentAmmo());
		//reload when full
		m.reload();
		assertEquals(20, m.getCurrentAmmo());
		//fire at something at a negative distance (invalid), should throw and Exception
		m.fire(-10);	
	}
	
	/**
	 * tests for Pistol toString(), firing
	 */
	@Test (expected = WeaponException.class)
	public void testPistol() throws WeaponException {
		Pistol p = new Pistol(); 
		
		//need to track the rounds now, we are going to update the round after every firing
		//tests for timer issues are separate
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(p);
		
		//set to round1
		timer.timeChanged();
		
		
		//check toString()
		assertEquals("Pistol", p.toString()); 
		
		//fire the Pistol once at something in range (30)
		//damage should be 6
		assertEquals(6, p.fire(30)); 
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(9, p.getCurrentAmmo()); 
		
		//fire the Pistol at something out of range (60)
		//damage should be zero
		assertEquals(0, p.fire(60));
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(8, p.getCurrentAmmo());
		
		//fire the Pistol once at something in range (50)
		//damage should be 2
		assertEquals(2, p.fire(50)); 
		//check the ammo is reduced by 1
		assertEquals(7, p.getCurrentAmmo());
		
		//fire the Pistol once at something in range (0)
		//damage should be 12
		assertEquals(12, p.fire(0));
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(6, p.getCurrentAmmo());
		
		//fire pistol until empty
		for(int i = 6; i > 0; i--)
		{
			p.fire(30);
			timer.timeChanged();
		}
		//Pistol should be empty
		assertEquals(0, p.getCurrentAmmo()); 
		
		//fire Pistol when empty
		assertEquals(0, p.fire(10));
		timer.timeChanged();
		//check that ammo is still 0
		assertEquals(0, p.getCurrentAmmo()); 
		
		//fire at something at a negative distance (invalid), should throw and Exception
		p.fire(-10);
	}

	/**
	 * tests for ChainGun initialization, firing, and reloading
	 */
	@Test(expected = WeaponException.class)
	public void testChainGun() throws WeaponException {
		ChainGun c = new ChainGun(); 
		
		//need to track the rounds now, we are going to update the round after every firing
		//tests for timer issues are separate
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(c);
		
		//set to round1
		timer.timeChanged();
	
		
		//check toString()
		assertEquals("ChainGun", c.toString()); 
		
		//fire the ChainGun once at something in range (30)
		//damage should be 7
		assertEquals(7, c.fire(30));
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(39, c.getCurrentAmmo()); 
		
		//fire the ChainGun at something out of range (100)
		//damage should be zero
		assertEquals(0, c.fire(100));
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(38, c.getCurrentAmmo());
		
		//fire the ChainGun once at something just in range (60)
		//damage should be 15
		assertEquals(15, c.fire(60)); 
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(37, c.getCurrentAmmo());
		
		//fire the ChainGun once at something in range (0)
		//damage should be 0 (minimum damage and point blank range)
		assertEquals(0, c.fire(0)); 
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(36, c.getCurrentAmmo());
		
		//fire ChainGun until empty
		for(int i = 36; i > 0; i--)
		{
			c.fire(30); 
			timer.timeChanged();
		}
		//ChainGun should be empty
		assertEquals(0, c.getCurrentAmmo()); 
		
		//fire ChainGun when empty
		assertEquals(0, c.fire(10)); 
		timer.timeChanged();
		//check that ammo is still 0
		assertEquals(0, c.getCurrentAmmo());
		
		//fire at something at a negative distance (invalid), should throw and Exception
		c.fire(-10);	
	}
	
	/**
	 * tests for PlasmaCannon initialization, firing, and reloading
	 */
	@Test(expected = WeaponException.class)
	public void testPlasmaCannon() throws WeaponException{
		PlasmaCannon pC = new PlasmaCannon(); 
		//need to track the rounds now, we are going to update the round after every firing
		//tests for timer issues are separate
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(pC);
		
		//set to round1
		timer.timeChanged();
	
		
		//check toString()
		assertEquals("PlasmaCannon", pC.toString()); 
		
		//fire the PlasmaCannon once at something in range (30)
		//power weakens with each shot
		//damage should be 50
		assertEquals(50, pC.fire(30)); 
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(3, pC.getCurrentAmmo()); 
		
		//fire the PlasmaCannon at something out of range (100)
		//damage should be zero
		assertEquals(0, pC.fire(100));
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(2, pC.getCurrentAmmo());
		
		//fire the PlasmaCannon once at something just in range (40)
		//damage should be 25 (2 shots fired so far)
		assertEquals(25, pC.fire(40));
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(1, pC.getCurrentAmmo());
		
		//fire the PlasmaCannon once at something in range (0)
		//damage should be 12 (1/4 of 50)
		assertEquals(12, pC.fire(0)); 
		timer.timeChanged();
		//check the ammo is reduced by 1
		assertEquals(0, pC.getCurrentAmmo());
		
	
		//fire PlasmaCannon when empty
		assertEquals(0, pC.fire(10)); 
		timer.timeChanged();
		//check that ammo is still 0
		assertEquals(0, pC.getCurrentAmmo()); 
		
		//fire at something at a negative distance (invalid), should throw and Exception
		pC.fire(-10);	

	}
	
	/**
	 * tests that non-generic weapons handle rate of fire and round updates correctly
	 * no threaded tests here. 
	 */
	@Test
	public void testRoundUpdates() throws WeaponException
	{
		Pistol p = new Pistol(); //2 shots per round
		PlasmaCannon pC = new PlasmaCannon(); //1 shot per round
		ChainGun c = new ChainGun(); //4 shots per round
		
		SimpleTimer timer = new SimpleTimer(); 
		
		//add the weapons as observers of the timer
		timer.addTimeObserver(p);
		timer.addTimeObserver(pC);
		timer.addTimeObserver(c);
		
		//update to round 1
		timer.timeChanged();
		
		//fire all weapons once (everyone should have at least one bullet)
		p.fire(10); 
		pC.fire(10); 
		c.fire(10); 
		
		//check shots left
		assertEquals(1, p.getShotsLeft()); 
		assertEquals(0, pC.getShotsLeft()); 
		assertEquals(3, c.getShotsLeft()); 
		
		//update the round, all weapons have all shots back
		timer.timeChanged();
		assertEquals(2, p.getShotsLeft()); 
		assertEquals(1, pC.getShotsLeft()); 
		assertEquals(4, c.getShotsLeft()); 
		
		//update round after no shots fired, all shots are at full
		timer.timeChanged();
		assertEquals(2, p.getShotsLeft()); 
		assertEquals(1, pC.getShotsLeft()); 
		assertEquals(4, c.getShotsLeft()); 
		
		//reload all weapons
		p.reload();
		pC.reload();
		c.reload();
		
		//fire all weapons 5 times (everyone runs out of shots)
		
		//fire 1
		assertEquals(10, p.fire(10)); 
		assertEquals(50, pC.fire(10)); 
		assertEquals(2,c.fire(10)); 
		
		//check shots left
		assertEquals(1, p.getShotsLeft()); 
		assertEquals(0, pC.getShotsLeft()); 
		assertEquals(3, c.getShotsLeft()); 
		//check current ammo
		assertEquals(9, p.getCurrentAmmo());
		assertEquals(3, pC.getCurrentAmmo()); 
		assertEquals(39, c.getCurrentAmmo()); 
		
		//fire 2
		assertEquals(10, p.fire(10)); 
		assertEquals(0, pC.fire(10)); //no shots left to fire this round
		assertEquals(2,c.fire(10)); 
		
		//check shots left
		assertEquals(0, p.getShotsLeft()); 
		assertEquals(0, pC.getShotsLeft()); 
		assertEquals(2, c.getShotsLeft());
		//check current ammo
		assertEquals(8, p.getCurrentAmmo());
		assertEquals(3, pC.getCurrentAmmo()); 
		assertEquals(38, c.getCurrentAmmo()); 
		
		//fire 3
		assertEquals(0, p.fire(10)); //no shots left to fire this round
		assertEquals(0, pC.fire(10)); //no shots left to fire this round
		assertEquals(2, c.fire(10)); 
		
		//check shots left
		assertEquals(0, p.getShotsLeft()); 
		assertEquals(0, pC.getShotsLeft()); 
		assertEquals(1, c.getShotsLeft());
		//check current ammo
		assertEquals(8, p.getCurrentAmmo());
		assertEquals(3, pC.getCurrentAmmo()); 
		assertEquals(37, c.getCurrentAmmo()); 
		
		//fire 4
		assertEquals(0, p.fire(10)); //no shots left to fire this round
		assertEquals(0, pC.fire(10)); //no shots left to fire this round
		assertEquals(2, c.fire(10)); 
		
		//check shots left
		assertEquals(0, p.getShotsLeft()); 
		assertEquals(0, pC.getShotsLeft()); 
		assertEquals(0, c.getShotsLeft());
		//check current ammo
		assertEquals(8, p.getCurrentAmmo());
		assertEquals(3, pC.getCurrentAmmo()); 
		assertEquals(36, c.getCurrentAmmo()); 
		
		//fire 5
		assertEquals(0, p.fire(10)); //no shots left to fire this round
		assertEquals(0, pC.fire(10)); //no shots left to fire this round
		assertEquals(0, c.fire(10)); //no shots left to fire this round
		
		//check shots left
		assertEquals(0, p.getShotsLeft()); 
		assertEquals(0, pC.getShotsLeft()); 
		assertEquals(0, c.getShotsLeft());
		//check current ammo
		assertEquals(8, p.getCurrentAmmo());
		assertEquals(3, pC.getCurrentAmmo()); 
		assertEquals(36, c.getCurrentAmmo()); 
	}
	
	@Test(expected = AttachmentException.class)
	public void testTooManyAttachments() throws AttachmentException
	{
		MockWeapon m = new MockWeapon();
		
		//add one attachment
		Stabilizer s = new Stabilizer(m); 
		
		//add two attachments
		Stabilizer s2 = new Stabilizer(s); 
		
		//add three attachments
		Stabilizer s3 = new Stabilizer(s2); 
		
	}
}

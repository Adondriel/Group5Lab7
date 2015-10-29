/**
 * 
 */
package weapon;

import static org.junit.Assert.*;
import exceptions.AttachmentException;
import exceptions.WeaponException;
import gameplay.SimpleTimer;

import org.junit.Test;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class TestScope {

	/**
	 * tests wrapping a Scope around a Pistol
	 * @throws WeaponException 
	 */
	@Test
	public void testScopePistol() throws AttachmentException, WeaponException {
		Scope scopePistol = new Scope(new Pistol()); 
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(scopePistol);
		timer.timeChanged();
		
		//check that the number of attachments was updated
		assertEquals(1, scopePistol.getNumAttachments()); 
		
		//check that the maxRange has been updated
		assertEquals(60, scopePistol.getMaxRange()); 
		
		//check toString()
		assertEquals("Pistol +Scope", scopePistol.toString()); 
		
		//check that the damage is amped appropriately
		//fire the Pistol once at something in range (30)
		//damage should be 6
		//with the scope, damage should be 6+(1+((60-30)/60)) =9
		assertEquals(9, scopePistol.fire(30)); 
		timer.timeChanged();
		
		//test at base.maxRange (50)
		//Pistol damage is 2
		//with Scope: 2*(1+(1/5)) = 2.4 = 2
		assertEquals(2, scopePistol.fire(50)); 
		timer.timeChanged();
		
		//test out of base.maxRange, but within scope range (55)
		//Pistol damage at 50 is 2
		//with scope boost = 2+5 = 7
		assertEquals(7, scopePistol.fire(55)); 
		timer.timeChanged();
		
		//test at edge of scope range (60)
		assertEquals(7, scopePistol.fire(60)); 
		timer.timeChanged();
		
		//test out of scope range (70)
		assertEquals(0, scopePistol.fire(70)); 
		timer.timeChanged();
	}


	/*
	 *
	 * tests wrapping a Scope around a PlasmaCannon
	 * @throws WeaponException 
	 *
	@Test
	public void testScopePlasmaCannon() throws AttachmentException, WeaponException {
		Scope scopePlasma = new Scope(new PlasmaCannon()); 
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(scopePlasma);
		timer.timeChanged();
		
		//check that the number of attachments was updated
		assertEquals(1, scopePlasma.getNumAttachments()); 
		
		//check that the maxRange has been updated
		assertEquals(50, scopePlasma.getMaxRange()); 
		
		//check toString()
		assertEquals("PlasmaCannon +Scope", scopePlasma.toString()); 
		
		//fire the PlasmaCannon once at something in range (30)
		//power weakens with each shot
		//damage should be 50
		//with the scope, damage should be 50*(1+((50-30)/50) = 70
		assertEquals(70, scopePlasma.fire(30)); 
		timer.timeChanged();
		
		//reload, because PlasmaCannon weakens with each shot
		scopePlasma.reload();
		
		//test at base.maxRange (40)
		//PlasmaCannon damage is 50
		//with Scope: 50*(1+((50-40)/50) = 60
		assertEquals(60, scopePlasma.fire(40)); 
		timer.timeChanged();
		//reload, because PlasmaCannon weakens with each shot
		scopePlasma.reload();
		
		//test out of base.maxRange, but within scope range (45)
		//PlasmaCannon damage is 50
		//with scope boost = 50+5 = 55
		assertEquals(55, scopePlasma.fire(45)); 
		timer.timeChanged();
		//reload, because PlasmaCannon weakens with each shot
		scopePlasma.reload();
		
		//test at edge of scope range (50)
		assertEquals(55, scopePlasma.fire(50)); 
		timer.timeChanged();
		
		//test out of scope range (70)
		assertEquals(0, scopePlasma.fire(70)); 
		timer.timeChanged();
	}
	*/
	
	/**
	 * tests wrapping a Scope around a ChainGun
	 * 
	 * @throws WeaponException 
	 * @throws AttachmentException 
	 */
	/*@Test
	public void testScopeChainGun() throws AttachmentException, WeaponException {
		Scope scopeChain = new Scope(new ChainGun()); 
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(scopeChain);
		timer.timeChanged();
		
		//check that the number of attachments was updated
		assertEquals(1, scopeChain.getNumAttachments()); 
		
		//check that the maxRange has been updated
		assertEquals(70, scopeChain.getMaxRange());
		
		//check toString()
		assertEquals("ChainGun +Scope", scopeChain.toString()); 
		
		//fire the ChainGun once at something in range (30)
		//damage should be 7 = 15*30/60
		//with the scope, damage should be 7*(1+((70-30)/70) = 11
		assertEquals(11, scopeChain.fire(30)); 
		timer.timeChanged();
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with Scope: 15*(1+((70-60)/70) = 17
		assertEquals(17, scopeChain.fire(60)); 
		timer.timeChanged();
		
		//test out of base.maxRange, but within scope range (65)
		//ChainGun damage is max at maxRange: 15
		//with scope boost = 15+5 = 20
		assertEquals(20, scopeChain.fire(65)); 
		timer.timeChanged();
		
		//test at edge of scope range (70)
		assertEquals(20, scopeChain.fire(70)); 
		timer.timeChanged();
		
		//test out of scope range (90)
		assertEquals(0, scopeChain.fire(90)); 
		timer.timeChanged();
	}
	*/
	
	/**
	 * Yes, a scope and stack with itself to make an extremely good scope
	 * @throws AttachmentException 
	 * @throws WeaponException 
	 */
	@Test
	public void testScopeScopePistol() throws AttachmentException, WeaponException
	{
		Scope scopePistol = new Scope(new Pistol()); 
		Scope scopeScopePistol = new Scope(scopePistol); 
		
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(scopeScopePistol);
		timer.timeChanged();
		
		//check that the number of attachments was updated
		assertEquals(2, scopeScopePistol.getNumAttachments()); 
		
		//check that the maxRange has been updated
		assertEquals(70, scopeScopePistol.getMaxRange()); 
		
		//check toString()
		assertEquals("Pistol +Scope +Scope", scopeScopePistol.toString()); 
		
		//check that the damage is amped appropriately
		//fire the ScopePistol once at something in range (30), damage is 9
		//with the extra scope, the damage is: 9*(1+((70-30)/70)) = 14
		assertEquals(14, scopeScopePistol.fire(30)); 
		timer.timeChanged();
		
		//test at base.maxRange (60) (scopePistol's maxRange)
		//ScopePistol damage is 2
		//with Scope: 2*(1+(1/6)) = 2.3 = 2
		assertEquals(2, scopeScopePistol.fire(50)); 
		timer.timeChanged();
		
		//test out of base.maxRange, but within scope range (65)
		//ScopePistol damage at 60 is 7
		//with scope boost = 7+5 = 12
		assertEquals(12, scopeScopePistol.fire(65)); 
		timer.timeChanged();
		
		//test at edge of scope range (70)
		assertEquals(12, scopeScopePistol.fire(70)); 
		timer.timeChanged();
		
		//test out of scope range (80)
		assertEquals(0, scopeScopePistol.fire(80)); 
		timer.timeChanged();
	}
	
	@Test
	public void testScopeStabilizerPistol() throws AttachmentException, WeaponException
	{
		Stabilizer stabilizerPistol = new Stabilizer(new Pistol()); 
		Scope scopeStabilizerPistol = new Scope(stabilizerPistol); 
		
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(scopeStabilizerPistol);
		timer.timeChanged();
		
		//check that the number of attachments was updated
		assertEquals(2, scopeStabilizerPistol.getNumAttachments()); 
		//check that the clip is full
		assertEquals(10, scopeStabilizerPistol.getCurrentAmmo()); 
		
		//check that the maxRange has been updated
		assertEquals(60, scopeStabilizerPistol.getMaxRange()); 
		
		//check toString()
		assertEquals("Pistol +Stabilizer +Scope", scopeStabilizerPistol.toString()); 
		
		//check that the damage is amped appropriately
		//fire the stabilizerPistol once at something in range (30)
		//damage should be 10*((50-30+10)/50)*1.25 = 7
		//with the scope, damage should be 7+(1+((60-30)/60)) =10
		assertEquals(10, scopeStabilizerPistol.fire(30));
		assertEquals(9, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test at base.maxRange (50)
		//Pistol damage is 2
		//with stabilizer: 2*1.25 = 2
		//with Scope: 2*(1+(1/5)) = 2.4 = 2
		assertEquals(2, scopeStabilizerPistol.fire(50)); 
		assertEquals(8, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test out of base.maxRange, but within scope range (55)
		//Pistol damage at 50 is 2
		//with Stabilizer: 2
		//with scope boost = 2+5 = 7
		assertEquals(7, scopeStabilizerPistol.fire(55));
		assertEquals(7, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test at edge of scope range (60)
		assertEquals(7, scopeStabilizerPistol.fire(60)); 
		assertEquals(6, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test out of scope range (70)
		assertEquals(0, scopeStabilizerPistol.fire(70)); 
		assertEquals(5, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//clip size is 10, and we have shot 5
		//shot 4
		assertEquals(10, scopeStabilizerPistol.fire(30));
		assertEquals(4, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		assertEquals(10, scopeStabilizerPistol.fire(30));
		assertEquals(3, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		assertEquals(10, scopeStabilizerPistol.fire(30));
		assertEquals(2, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		assertEquals(10, scopeStabilizerPistol.fire(30));
		//check that clip is at 1
		assertEquals(1, scopeStabilizerPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//shoot last buller
		//stabilizer should reload the clip
		assertEquals(10, scopeStabilizerPistol.fire(30));
		assertEquals(10, scopeStabilizerPistol.getCurrentAmmo()); 
	}
	
	@Test
	public void testScopePowerBoosterPistol() throws AttachmentException, WeaponException
	{
		PowerBooster boosterPistol = new PowerBooster(new Pistol()); 
		Scope scopeBoosterPistol = new Scope(boosterPistol); 
		
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(scopeBoosterPistol);
		timer.timeChanged();
		
		//check that the number of attachments was updated
		assertEquals(2, scopeBoosterPistol.getNumAttachments()); 
		//check that the clip is full
		assertEquals(10, scopeBoosterPistol.getCurrentAmmo()); 
		
		//check that the maxRange has been updated
		assertEquals(60, scopeBoosterPistol.getMaxRange()); 
		
		//check toString()
		assertEquals("Pistol +PowerBooster +Scope", scopeBoosterPistol.toString()); 
		
		//check that the damage is amped appropriately
		//fire the boosterPistol once at something in range (30)
		//damage should be 10*((50-30+10)/50)*(1+10/10) = 12
		//with the scope, damage should be 12*(1+((60-30)/60)) =18
		assertEquals(18, scopeBoosterPistol.fire(30));
		assertEquals(9, scopeBoosterPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test at base.maxRange (50)
		//Pistol damage is 2
		//with powerbooster & 9 bullets: 2*(1+9/10) = 8
		//with Scope: 3*(1+(1/5)) = 3.6 = 3
		assertEquals(3, scopeBoosterPistol.fire(50)); 
		assertEquals(8, scopeBoosterPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test out of base.maxRange, but within scope range (55)
		//Pistol damage at 50 is 2
		//with powerbooster & 8 bullets: 2*(1+8/10) = 3
		//with scope boost = 3+5 = 8
		assertEquals(8, scopeBoosterPistol.fire(55));
		assertEquals(7, scopeBoosterPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test at edge of scope range (60)
		//Pistol damage at 50 is 2
		//with powerbooster & 7 bullets: 2*(1+7/10) = 3
		//with scope boost = 3+5 = 8
		assertEquals(8, scopeBoosterPistol.fire(60)); 
		assertEquals(6, scopeBoosterPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test out of scope range (70)
		assertEquals(0, scopeBoosterPistol.fire(70)); 
		assertEquals(5, scopeBoosterPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//clip size is 10, and we have shot 5
		//shoot 4
		//test at base.maxRange (50)
		//Pistol damage is 2
		//with powerbooster & 5 bullets: 2*(1+5/10) = 3
		//with Scope: 3*(1+(1/5)) = 3.6 = 3
		assertEquals(3, scopeBoosterPistol.fire(50)); 
		assertEquals(4, scopeBoosterPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test at base.maxRange (50)
		//Pistol damage is 2
		//with powerbooster & 4 bullets: 2*(1+4/10) = 2
		//with Scope: 2*(1+(1/5)) = 2.4 = 2
		assertEquals(2, scopeBoosterPistol.fire(50)); 
		assertEquals(3, scopeBoosterPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test at base.maxRange (50)
		//Pistol damage is 2
		//with powerbooster & 3 bullets: 2*(1+3/10) = 2
		//with Scope: 2*(1+(1/5)) = 2.4 = 2
		assertEquals(2, scopeBoosterPistol.fire(50)); 
		assertEquals(2, scopeBoosterPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test at base.maxRange (50)
		//Pistol damage is 2
		//with powerbooster & 4 bullets: 2*(1+2/10) = 2
		//with Scope: 2*(1+(1/5)) = 2.4 = 2
		assertEquals(2, scopeBoosterPistol.fire(50)); 
		assertEquals(1, scopeBoosterPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test at base.maxRange (50)
		//Pistol damage is 2
		//with powerbooster & 4 bullets: 2*(1+1/10) = 2
		//with Scope: 2*(1+(1/5)) = 2.4 = 2
		assertEquals(2, scopeBoosterPistol.fire(50)); 
		assertEquals(0, scopeBoosterPistol.getCurrentAmmo()); 
		timer.timeChanged();
		
		//test on empty
		//test at base.maxRange (50)
		//Pistol damage is 2
		//with powerbooster & 4 bullets: 2*(1+4/10) = 2
		//with Scope: 2*(1+(1/5)) = 2.4 = 2
		assertEquals(0, scopeBoosterPistol.fire(50)); 
		assertEquals(0, scopeBoosterPistol.getCurrentAmmo()); 
		timer.timeChanged();
	}
}

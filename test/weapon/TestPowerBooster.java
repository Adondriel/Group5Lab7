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
public class TestPowerBooster {

	/**
	 * tests wrapping a PowerBooster around a ChainGun
	 * 
	 * @throws WeaponException 
	 * @throws AttachmentException 
	 */
	@Test
	public void testPowerBoosterChainGun() throws AttachmentException, WeaponException {
		PowerBooster powerChain = new PowerBooster(new ChainGun()); 
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(powerChain);
		timer.timeChanged();
		
		//check that the number of attachments was updated
		assertEquals(1, powerChain.getNumAttachments()); 
		
		//check toString()
		assertEquals("ChainGun +PowerBooster", powerChain.toString()); 
		
		//fire the ChainGun once at something in range (30)
		//damage should be 7 = 15*30/60
		//with the powerBooster, damage should be 7*(1+(40/40) = 14
		assertEquals(14, powerChain.fire(30)); 
		timer.timeChanged();
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with PowerBooster: 15*(1+(39/40) = 29
		assertEquals(29, powerChain.fire(60)); 
		timer.timeChanged();
		
		//test out of scope range (90)
		assertEquals(0, powerChain.fire(90)); 
		timer.timeChanged();
		
		//fire a bunch of times, then make sure that the powerboost is responding
		for(int i = 0; i < 15; i++)
		{
			powerChain.fire(30); 
			timer.timeChanged();
		}
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with PowerBooster: 15*(1+(22/40) = 23
		assertEquals(23, powerChain.fire(60)); 
		timer.timeChanged();
		
		//fire a bunch of times, then make sure that the powerboost is responding
		for(int i = 0; i < 15; i++)
		{
			powerChain.fire(30); 
			timer.timeChanged();
		}
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with PowerBooster: 15*(1+(7/40) = 17
		assertEquals(17, powerChain.fire(60)); 
		timer.timeChanged();
	}
	
	@Test
	public void testPowerBoosterScopeChainGun() throws AttachmentException, WeaponException
	{
		Scope scopeChain = new Scope(new ChainGun());
		PowerBooster powerScopeChain = new PowerBooster(scopeChain); 
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(powerScopeChain);
		timer.timeChanged();
		
		//check that the number of attachments was updated
		assertEquals(2, powerScopeChain.getNumAttachments()); 
		
		//check toString()
		assertEquals("ChainGun +Scope +PowerBooster", powerScopeChain.toString()); 
		
		//fire the ChainGun once at something in range (30)
		//damage should be 7 = 15*30/60
		//with the scope, damage should be 7*(1+((70-30)/70) = 11
		//with the powerBooster, damage should be 11*(1+(40/40) = 22
		assertEquals(22, powerScopeChain.fire(30)); 
		timer.timeChanged();
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with Scope: 15*(1+((70-60)/70) = 17
		//with PowerBooster: 17*(1+(39/40) = 33
		assertEquals(33, powerScopeChain.fire(60)); 
		timer.timeChanged();
		
		//test out of scope range (90)
		assertEquals(0, powerScopeChain.fire(90)); 
		timer.timeChanged();
		
		//fire a bunch of times, then make sure that the powerboost is responding
		for(int i = 0; i < 15; i++)
		{
			powerScopeChain.fire(30); 
			timer.timeChanged();
		}
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with Scope: 15*(1+((70-60)/70) = 17
		//with PowerBooster: 17*(1+(22/40) = 26
		assertEquals(26, powerScopeChain.fire(60)); 
		timer.timeChanged();
		
		//fire a bunch of times, then make sure that the powerboost is responding
		for(int i = 0; i < 15; i++)
		{
			powerScopeChain.fire(30); 
			timer.timeChanged();
		}
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with Scope: 15*(1+((70-60)/70) = 17
		//with PowerBooster: 17*(1+(7/40) = 19
		assertEquals(19, powerScopeChain.fire(60)); 
		timer.timeChanged();
	}
	
	@Test
	public void testPowerBoosterStabilizerChainGun() throws AttachmentException, WeaponException
	{
		PowerBooster powerStabilizerChain = new PowerBooster(new Stabilizer(new ChainGun())); 
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(powerStabilizerChain);
		timer.timeChanged();
		
		//check that the number of attachments was updated
		assertEquals(2, powerStabilizerChain.getNumAttachments()); 
		
		//check toString()
		assertEquals("ChainGun +Stabilizer +PowerBooster", powerStabilizerChain.toString()); 
		
		//fire the ChainGun once at something in range (30)
		//damage should be 7 = 15*30/60
		//with Stabilizer: 7*1.25 = 8
		//with the powerBooster, damage should be 8*(1+(40/40) = 16
		assertEquals(16, powerStabilizerChain.fire(30)); 
		timer.timeChanged();
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with Stabilizer: 15*1.25 = 18
		//with PowerBooster: 18*(1+(39/40) = 35
		assertEquals(35, powerStabilizerChain.fire(60)); 
		timer.timeChanged();
		
		//test out of scope range (90)
		assertEquals(0, powerStabilizerChain.fire(90)); 
		timer.timeChanged();
		
		//fire a bunch of times, then make sure that the powerboost is responding
		for(int i = 0; i < 15; i++)
		{
			powerStabilizerChain.fire(30); 
			timer.timeChanged();
		}
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with Stabilizer: 18
		//with PowerBooster: 18*(1+(22/40) = 27
		assertEquals(22, powerStabilizerChain.getCurrentAmmo()); 
		assertEquals(27, powerStabilizerChain.fire(60)); 
		timer.timeChanged();
		
		//fire a bunch of times, then make sure that the powerboost is responding
		for(int i = 0; i < 15; i++)
		{
			powerStabilizerChain.fire(30); 
			timer.timeChanged();
		}
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with stabilizer: 18
		//with PowerBooster: 18*(1+(76/40) = 20
		assertEquals(6, powerStabilizerChain.getCurrentAmmo()); 
		assertEquals(20, powerStabilizerChain.fire(60)); 
		timer.timeChanged();
	}
	
	@Test
	public void testPowerBoosterPowerBoosterChainGun() throws AttachmentException, WeaponException
	{
		PowerBooster powerPowerChain = new PowerBooster(new PowerBooster(new ChainGun())); 
		SimpleTimer timer = new SimpleTimer(); 
		timer.addTimeObserver(powerPowerChain);
		timer.timeChanged();
		
		//check that the number of attachments was updated
		assertEquals(2, powerPowerChain.getNumAttachments()); 
		
		//check toString()
		assertEquals("ChainGun +PowerBooster +PowerBooster", powerPowerChain.toString()); 
		
		//fire the ChainGun once at something in range (30)
		//damage should be 7 = 15*30/60
		//with the powerBooster, damage should be 7*(1+(40/40) = 14
		//with the powerBooster, damage should be 14*(1+(40/40) = 28
		assertEquals(28, powerPowerChain.fire(30)); 
		timer.timeChanged();
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with PowerBooster: 15*(1+(39/40) = 29
		//with PowerBooster: 29*(1+(39/40) = 57
		assertEquals(57, powerPowerChain.fire(60)); 
		timer.timeChanged();
		
		//test out of scope range (90)
		assertEquals(0, powerPowerChain.fire(90)); 
		timer.timeChanged();
		
		//fire a bunch of times, then make sure that the powerboost is responding
		for(int i = 0; i < 15; i++)
		{
			powerPowerChain.fire(30); 
			timer.timeChanged();
		}
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with PowerBooster: 15*(1+(22/40) = 23
		//with PowerBooster: 23*(1+(22/40) = 35
		assertEquals(35, powerPowerChain.fire(60)); 
		timer.timeChanged();
		
		//fire a bunch of times, then make sure that the powerboost is responding
		for(int i = 0; i < 15; i++)
		{
			powerPowerChain.fire(30); 
			timer.timeChanged();
		}
		
		//test at base.maxRange (60)
		//ChainGun damage is max: 15
		//with PowerBooster: 15*(1+(7/40) = 17
		//with PowerBooster: 17*(1+(7/40) = 19
		assertEquals(19, powerPowerChain.fire(60)); 
		timer.timeChanged();
	}
}

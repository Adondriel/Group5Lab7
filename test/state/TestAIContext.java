package state;

import static org.junit.Assert.*;

import org.junit.Test;

import lifeform.Human;

/**
 * @author Adam Pine
 * Provides the testing for AIContext.
 */
public class TestAIContext {

	/**
	 * Test that we can set the AIContext state.
	 */
	@Test
	public void testCanChangeActiveState() {
		Human bob = new Human("bob",30,30);
		AIContext ac = new AIContext(bob);
		assertTrue(ac.getCurrentState() instanceof NoWeaponState);
		ac.setState(ac.getDeadState());
		assertTrue(ac.getCurrentState() instanceof DeadState);
	}
	
	/**
	 * Test that we can get the states of the AIContext.
	 */
	@Test
	public void testCanGetStates(){
		Human bob = new Human("bob",30,30);
		AIContext ac = new AIContext(bob);
		assertTrue(ac.getNoWeaponState() instanceof NoWeaponState);
		assertTrue(ac.getDeadState() instanceof DeadState);
		assertTrue(ac.getHasWeaponState() instanceof HasWeaponState);
		assertTrue(ac.getOutOfAmmoState() instanceof OutOfAmmoState);
	}

}

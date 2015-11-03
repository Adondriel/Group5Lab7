package state;

import static org.junit.Assert.*;
import lifeform.Human;
import lifeform.LifeForm;

import org.junit.Test;

import weapon.Pistol;
import environment.Environment;

/**
 * @author Benjamin Uleau
 *
 */
public class TestOutOfAmmoState
{

	Environment e=Environment.getEnvironment(10, 10);
	/**
	 * Test OutOfAmmoState
	 */
	@Test
	public void test()
	{
		e.clearBoard();
		LifeForm h1=new Human("H1", 20, 0);
		Pistol p=new Pistol();		
		AIContext ai=new AIContext(h1);	//New AIContext
		OutOfAmmoState oas=new OutOfAmmoState(ai);	//New OutOfAmmoState
		ai.setState(ai.getOutOfAmmoState());	//Set the AI state to OutOfAmmoState
		
		e.addLifeForm(h1, 0, 0);	//Add the lifeform
		assertEquals(h1, e.getLifeForm(0, 0));	//Make sure the lifeform got added properly
		e.addWeapon(p, 0, 0);	//Add the pistol to the cell
		
		h1.pickUpWeapon(p); //have the lifeform pick up the weapon
		
		assertEquals(ai.getOutOfAmmoState(), ai.getCurrentState());	//Make sure the lifeform is in the out of ammo state
		
		ai.executeAction();	//Execute action
		assertEquals(ai.getCurrentState(), ai.getHasWeaponState());	//Make sure the current state is now has weapon state
	}

	/**
	 * Test OutOfAmmoState when the lifeform dies
	 */
	@Test
	public void testWhenDead(){
		e.clearBoard();	//Clear the board
		LifeForm h1=new Human("H1", 20, 0);
		Pistol p=new Pistol();
		AIContext ai=new AIContext(h1);
		OutOfAmmoState oas=new OutOfAmmoState(ai);
		ai.setState(ai.getOutOfAmmoState());
		
		e.addLifeForm(h1, 0, 0);
		e.addWeapon(p, 0, 0);
		h1.pickUpWeapon(p);
		
		h1.takeHit(50);		//Human should take a hit for the damage
		ai.executeAction();	//Execute the action
		assertEquals(ai.getDeadState(), ai.getCurrentState());	//Make sure the state is now DeadState
	}
}

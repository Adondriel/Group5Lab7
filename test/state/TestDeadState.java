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
public class TestDeadState
{

	Environment e=Environment.getEnvironment(10, 10);
	/**
	 * Test dead state
	 */
	@Test
	public void testDeadStateWithoutWeapon()
	{
		
		e.clearBoard();
		
		LifeForm h1=new Human("H1", 20, 0);
		
		e.addLifeForm(h1, 0, 0);
		AIContext ai=new AIContext(h1);	//New AIContext
		DeadState ds=new DeadState(ai);	//New DeadState
		ai.setState(ai.getDeadState());	//Set the AI state to DeadState
		
		int prevRow=h1.getRow();		//The row the lifeform was in before entering DeadState
		int prevCol=h1.getCol();		//The col the lifeform was in before entering DeadState
		
		assertEquals(h1, ai.getLifeForm());
		h1.takeHit(50);
		assertEquals(0, h1.getCurrentLifePoints());
		assertEquals(ai.getDeadState(), ai.getCurrentState());	//Make sure the state was set to DeadState
		ds.executeAction();	
		assertNull(e.getLifeForm(prevRow, prevCol));	//Make sure the cell was vacated
	}
	
	/**
	 * Test moving a lifeform to a new cell and moving a weapon to a new cell when a lifeform enters DeadSsate
	 */
	@Test
	public void testWithWeaponState(){
		e.clearBoard();
		
		LifeForm h1=new Human("H1", 20, 0);
		Pistol p=new Pistol();
		
		e.addWeapon(p, 0, 0);
		e.addLifeForm(h1, 0, 0);
		h1.pickUpWeapon(p);
		AIContext ai=new AIContext(h1);	//New AIContext
		DeadState ds=new DeadState(ai);	//New DeadState
		ai.setState(ai.getDeadState());	//Set the AI state to DeadState
		
		int prevRow=h1.getRow();		//The row the lifeform was in before entering DeadState
		int prevCol=h1.getCol();		//The col the lifeform was in before entering DeadState
		
		assertEquals(h1, ai.getLifeForm());
		h1.takeHit(50);
		assertEquals(0, h1.getCurrentLifePoints());
		assertEquals(ai.getDeadState(), ai.getCurrentState());	//Make sure the state was set to DeadState
		ds.executeAction();	
		assertNull(e.getLifeForm(prevRow, prevCol));	//Make sure the cell was vacated
		
		int numInstances=0;	//Number of instances of the weapon found in the environment
		//Scan every cell in the environment for the weapon. If the weapon is found, numInstances increases by 1. The weapon should be found once
		for(int i=0; i<10; i++){
			for(int j=0; j<10; j++){
				if(e.getCell(i, j).getWeapon1()==p || e.getCell(i, j).getWeapon2()==p){
					numInstances++;
				}
			}
		}
		assertEquals(1, numInstances);
	}
}
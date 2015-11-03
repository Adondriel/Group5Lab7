package state;

import java.util.Random;

import command.AttackCmd;
import command.MoveCmd;
import weapon.Weapon;
import lifeform.LifeForm;
import environment.Cell;
import environment.Environment;

/**
 * @author Benjamin Uleau, Jason LoBianco
 *
 */
public abstract class ActionState
{
	int maxRow=10;
	int maxCol=10;
	Environment e=Environment.getEnvironment(maxRow, maxCol);
	AIContext ai;
	LifeForm l;
	
	/**
	 * @return ai's lifeform
	 */
	public LifeForm getLifeForm(){
		return ai.l;
	}
	
	/**
	 * Execute the state's action
	 */
	public abstract void executeAction();
}
package state;

import java.util.Random;

import command.MoveCmd;

/**
 * Provides functionality to the AI when in NoWeaponState.
 * @author Jason LoBianco
 */
public class NoWeaponState extends ActionState
{
	AIContext ai;
	
	/**
	 * Passes in the AI to be able to change the state.
	 * @param ai
	 */
	public NoWeaponState(AIContext ai)
	{
		this.ai = ai;
	}
	
	/**
	 * When called, checks to see if the LifeForm is alive, if it is and 
	 * there is a weapon for the LifeForm to pick up, the LifeForm picks up the weapon. 
	 * If there is no weapon the LifeForm will turn a random direction and move.
	 * If dead moves to the DeadState.
	 */
	@Override
	public void executeAction()
	{
		if (alive() == true)
		{
			if (weaponAtFeet() == true)
			{
				aquireWeapon();
				ai.setState(ai.getHasWeaponState());
			}
			else
			{
				search();
			}
		}
		else
		{
			ai.setState(ai.getDeadState());
		}
	}
	
	/**
	 * @return false if the current life points of a LifeForm is 0.
	 */
	private boolean alive() 
	{
		if (ai.l.getCurrentLifePoints() == 0)
		{
		return false;
		}
		return true;
	}

	/**
	 * @return true if there is a weapon in the same row and column as a LifeForm.
	 */
	private boolean weaponAtFeet() 
	{
		if (e.getWeapons(ai.l.getRow(), ai.l.getCol())[0] != null || e.getWeapons(ai.l.getRow(), ai.l.getCol())[1] != null)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Turns a LifeForm a random direction and then moves.
	 */
	private void search() 
	{
		String [] direction = {"North", "South", "East", "West"};
        int random = (int)(Math.random()*4);
		ai.l.setDirection(direction[random]);
		MoveCmd move = new MoveCmd();
		move.execute(ai.l.getRow(), ai.l.getCol());
	}
	
	/**
	 * Picks up a weapon from the environment for a LifeForm.
	 */
	private void aquireWeapon() 
	{
		int wepRow = ai.l.getRow();
		int wepCol = ai.l.getCol();
		ai.l.pickUpWeapon(e.getWeapons(wepRow, wepCol)[0]);
		e.removeWeapon(e.getWeapons(wepRow, wepCol)[0], wepRow, wepCol);
	}
	
}
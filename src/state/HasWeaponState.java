package state;

import java.util.Random;

import lifeform.Alien;
import lifeform.Human;
import lifeform.LifeForm;
import command.AttackCmd;
import command.MoveCmd;

/**
 * Provides functionality to the AI when in HasWeaponState.
 * @author Jason LoBianco
 */
public class HasWeaponState extends ActionState
{
	private int canMove = 0;
	private LifeForm target;
	AIContext ai;
	
	/**
	 * Passes in the AI to be able to change the state.
	 * @param ai
	 */
	public HasWeaponState(AIContext ai)
	{
		this.ai = ai;
	}
	
	/**
	 * When called, checks to see if the LifeForm is alive, if it is and 
	 * there is a target for the LifeForm to attack, the LifeForm attacks 
	 * the target.  If the LifeFrom's weapon is out of ammo, the LifeForm moves 
	 * to OutOFAmmoState. If no target the LifeForm will turn a random direction 
	 * and move.  If dead moves to the DeadState.
	 */
	@Override
	public void executeAction()
	{
		if (alive() == true)
		{
			if (target() == true)
			{
				if (checkAmmo() == false)
				{
					ai.setState(ai.getOutOfAmmoState());
				}
				else
				{
					attackTarget();
				}
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
	 * Turns a LifeForm a random direction and then moves.
	 */
	private void search() 
	{
		String oldDirection = ai.l.getDirection();
		String [] direction = {"North", "South", "East", "West"};
        int random = (int)(Math.random()*4);
		ai.l.setDirection(direction[random]);
		if (oldDirection == ai.l.getDirection())
		{
			search();
		}
		canMove++;
		if (canMove == 2)
		{
			MoveCmd move = new MoveCmd();
			move.execute(ai.l.getRow(), ai.l.getCol());
			canMove = 0;
		}
		
	}

	/**
	 * @return false if the current ammo of the LifeForm's weapon is 0.
	 */
	private boolean checkAmmo() 
	{
		if (ai.l.getWeapon().getCurrentAmmo() == 0)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * @return true if there is a target for a LifeForm to attack.
	 */
	private boolean target() 
	{
		int i = ai.l.getRow();
		int j = ai.l.getCol();
		if (ai.l.getDirection() == "North")
		{
			while (i>0)
			{
				if (e.getLifeForm(i-1, ai.l.getCol()) != null)
				{
					target = e.getLifeForm(i-1, ai.l.getCol());
					return true;
				}
				i--;
			}
		}
		else if(ai.l.getDirection() == "South")
		{
			while (i<e.getNumRows())
			{
				if (e.getLifeForm(i+1, ai.l.getCol()) != null)
				{
					target = e.getLifeForm(i+1, ai.l.getCol());
					return true;
				}
				i++;
			}
		}
		else if (ai.l.getDirection() == "East")
		{
			while (j>0)
			{
				if (e.getLifeForm(ai.l.getRow(), j-1) != null)
				{
					target = e.getLifeForm(ai.l.getRow(), j-1);
					return true;
				}
				j--;
			}
		}
		else if (ai.l.getDirection() == "West")
		{
			while (j<0)
			{
				if (e.getLifeForm(ai.l.getRow(), j+1) != null)
				{
					target = e.getLifeForm(ai.l.getRow(), j+1);
					return true;
				}
				j++;
			}
		}
		return false;
	}
	
	/**
	 * The LifeForm will attack its target.
	 */
	private void attackTarget() 
	{
		AttackCmd attack = new AttackCmd();
		if (ai.getLifeForm() instanceof Human && target instanceof Alien)
		{
			attack.execute(ai.l.getRow(), ai.l.getCol());
		}
		else if (ai.getLifeForm() instanceof Human && target instanceof Human)
		{
			search();
		}
		else if (ai.getLifeForm() instanceof Alien && target instanceof Human)
		{
			attack.execute(ai.l.getRow(), ai.l.getCol());
		}
		else
		{
			search();
		}
	}
}
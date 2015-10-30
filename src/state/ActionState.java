package state;

import java.util.Random;

import command.AttackCmd;
import command.MoveCmd;
import weapon.Weapon;
import lifeform.LifeForm;
import environment.Environment;

/**
 * @author Benjamin Uleau, Jason LoBianco
 *
 */
public abstract class ActionState 
{
	Environment e=Environment.getEnvironment(10, 10);
	LifeForm l;
	
	/**
	 * Remove the lifeform and put its weapon in a random cell
	 */
	public void respawn(){
		int newR=(int) Math.floor(Math.random()*11);
		int newC=(int) Math.floor(Math.random()*11);
		if(e.getWeapons(l.getRow(), l.getCol()).length!=2){
			l.dropWeapon();
		}
		else{
			l.removeWeapon();
		}
		l.removeWeapon();
		e.removeLifeForm(l.getRow(), l.getCol());
		while(e.getLifeForm(newR, newC)==null){
			newR=(int) Math.floor(Math.random()*11);
			newC=(int) Math.floor(Math.random()*11);
		}
		e.addLifeForm(l, newR, newC);
	}

	/**
	 * @return true if there is a weapon in the same row and column as a LifeForm.
	 */
	public boolean weaponAtFeet() 
	{
		if (e.getWeapons(l.getRow(), l.getCol()) != null)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * @return false if the current life points of a LifeForm is 0.
	 */
	public boolean alive() 
	{
		if (l.getCurrentLifePoints() == 0)
		{
		return false;
		}
		return true;
	}

	/**
	 * @return false if the current ammo of the LifeForm's weapon is 0.
	 */
	public boolean checkAmmo() 
	{
		if (l.getWeapon().getCurrentAmmo() == 0)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * @return true if there is a target for a LifeForm to attack.
	 */
	public boolean target() 
	{
		int i = l.getRow();
		int j = l.getCol();
		if (l.getDirection() == "North")
		{
			while (i>0)
			{
				if (e.getLifeForm(i-1, l.getCol()) != null)
				{
					return true;
				}
				i--;
			}
		}
		else if(l.getDirection() == "South")
		{
			while (i<e.getNumRows())
			{
				if (e.getLifeForm(i+1, l.getCol()) != null)
				{
					return true;
				}
				i++;
			}
		}
		else if (l.getDirection() == "East")
		{
			while (j>0)
			{
				if (e.getLifeForm(l.getRow(), j-1) != null)
				{
					return true;
				}
				j--;
			}
		}
		else if (l.getDirection() == "West")
		{
			while (j<0)
			{
				if (e.getLifeForm(l.getRow(), j+1) != null)
				{
					return true;
				}
				j++;
			}
		}
		return false;
	}
	
	/**
	 * Turns a LifeForm a random direction and then moves.
	 */
	public void search() 
	{
		String [] direction = {"North", "South", "East", "West"};
        Random random = new Random();
        int select = random.nextInt(direction.length);
		l.setDirection(direction[select]);
		MoveCmd move = new MoveCmd();
		move.execute(l.getRow(), l.getCol());
	}
	
	/**
	 * Picks up a weapon from the environment for a LifeForm.
	 */
	public void aquireWeapon() 
	{
		int wepRow = l.getRow();
		int wepCol = l.getCol();
		l.pickUpWeapon(e.getWeapons(wepRow, wepCol)[0]);
		e.removeWeapon(e.getWeapons(wepRow, wepCol)[0], wepRow, wepCol);
	}
	
	/**
	 * The LifeForm will attack its target.
	 */
	public void attackTarget() 
	{
		AttackCmd attack = new AttackCmd();
		attack.execute(l.getRow(), l.getCol());
	}
}
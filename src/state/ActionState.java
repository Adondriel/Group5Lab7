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
	int maxRow=10;
	int maxCol=10;
	Environment e=Environment.getEnvironment(maxRow, maxCol);
	//LifeForm l;
	AIContext ai;
	
	/**
	 * @param l lifeform
	 */
	/*public ActionState(LifeForm l){
		this.l=l;
	}*/
	
	/**
	 * Remove the lifeform and put its weapon in a random cell
	 */
	public void respawn(){
		int newR=(int) Math.floor(Math.random()*maxRow+1);
		int newC=(int) Math.floor(Math.random()*maxCol+1);
		Weapon temp;
		if(ai.l.hasWeapon()){
			temp=ai.l.getWeapon();
			ai.l.removeWeapon();
			while(e.getWeapons(newR, newC).length==2){
				newR=(int) Math.floor(Math.random()*maxRow+1);
				newC=(int) Math.floor(Math.random()*maxCol+1);
			}
			e.addWeapon(temp, newR, newC);
		}
		e.removeLifeForm(ai.l.getRow(), ai.l.getCol());
		while(e.getLifeForm(newR, newC)!=null){
			newR=(int) Math.floor(Math.random()*maxRow+1);
			newC=(int) Math.floor(Math.random()*maxCol+1);
		}
		e.addLifeForm(ai.l, newR, newC);
		
	}

	/**
	 * @return true if there is a weapon in the same row and column as a LifeForm.
	 */
	public boolean weaponAtFeet() 
	{
		if (e.getWeapons(ai.l.getRow(), ai.l.getCol()) != null)
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
		if (ai.l.getCurrentLifePoints() == 0)
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
		if (ai.l.getWeapon().getCurrentAmmo() == 0)
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
		int i = ai.l.getRow();
		int j = ai.l.getCol();
		if (ai.l.getDirection() == "North")
		{
			while (i>0)
			{
				if (e.getLifeForm(i-1, ai.l.getCol()) != null)
				{
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
		ai.l.setDirection(direction[select]);
		MoveCmd move = new MoveCmd();
		move.execute(ai.l.getRow(), ai.l.getCol());
	}
	
	/**
	 * Picks up a weapon from the environment for a LifeForm.
	 */
	public void aquireWeapon() 
	{
		int wepRow = ai.l.getRow();
		int wepCol = ai.l.getCol();
		ai.l.pickUpWeapon(e.getWeapons(wepRow, wepCol)[0]);
		e.removeWeapon(e.getWeapons(wepRow, wepCol)[0], wepRow, wepCol);
	}
	
	/**
	 * The LifeForm will attack its target.
	 */
	public void attackTarget() 
	{
		AttackCmd attack = new AttackCmd();
		attack.execute(ai.l.getRow(), ai.l.getCol());
	}
	
	public LifeForm getLifeForm(){
		return ai.l;
	}
	
	public abstract void executeAction();
}
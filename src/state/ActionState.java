package state;

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
		Weapon temp=l.getWeapon();
		int newR=(int) Math.floor(Math.random()*11);
		int newC=(int) Math.floor(Math.random()*11);
		l.removeWeapon();
		while(e.getWeapons(newR, newC).length==2){
			newR=(int) Math.floor(Math.random()*11);
			newC=(int) Math.floor(Math.random()*11);
		}
		e.addWeapon(temp, newR, newC);
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
		return true;
	}
}
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
}
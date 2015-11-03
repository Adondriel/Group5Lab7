package state;

import weapon.Weapon;

/**
 * @author Benjamin Uleau
 *
 */
public class DeadState extends ActionState
{
	AIContext ai;
	/**
	 * Passes in the AI to be able to change the state.
	 * @param ai
	 */
	public DeadState(AIContext ai)
	{
		this.ai = ai;
	}
	
	/**
	 * Remove the lifeform and put its weapon in a random cell
	 */
	private void respawn(){
		int newR=(int) Math.floor(Math.random()*maxRow);	//Random number between 0 and 9 for the new row
		int newC=(int) Math.floor(Math.random()*maxCol);	//Random number between 0 and 9 for the new col
		Weapon temp;	//Temporary slot for weapon
		if(ai.l.hasWeapon()){
			temp=ai.l.getWeapon();	//temp=lifeform's weapon
			ai.l.removeWeapon();	//Remove the weapon from the lifeform
			e.removeWeapon(temp, ai.l.getRow(), ai.l.getCol());
			while(e.getCell(newR, newC).getWeapon1()!=null && e.getCell(newR, newC).getWeapon2()!=null){	//If there is a weapon in both slots of the cell...
				newR=(int) Math.floor(Math.random()*maxRow);	//Rerandomize row
				newC=(int) Math.floor(Math.random()*maxCol);	//Rerandomize col
			}
			e.addWeapon(temp, newR, newC);	//Add weapon at randomized row, col
		}
		e.removeLifeForm(ai.l.getRow(), ai.l.getCol());	//Remove the lifeform
		while(e.getLifeForm(newR, newC)!=null){	//If there's a lifeform in the cell...
			newR=(int) Math.floor(Math.random()*maxRow);	//Rerandomize row
			newC=(int) Math.floor(Math.random()*maxCol);	//Rerandomize col
		}
		ai.l.refillLife();	//Set the lifeform's health to max
		ai.l.setLocation(newR, newC);	//Set lifeform's new location
		e.addLifeForm(ai.l, newR, newC);	//Add the lifeform to the new spot in the environment
	}
	
	/**
	 * Checks whether or not the lifeform is dead. If it is, it loses a turn then respawns.
	 */
	@Override
	public void executeAction(){
		respawn();
		ai.setState(ai.getNoWeaponState());
	}
}

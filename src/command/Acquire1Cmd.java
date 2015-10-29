/**
 * 
 */
package command;

import weapon.Weapon;
import lifeform.LifeForm;
import environment.Environment;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class Acquire1Cmd implements Command {
	private Environment e; 
	
	public Acquire1Cmd()
	{
		//This is sloppy, but there is no way a call to a command constructor should be the first call to getEnvironment()
				e = Environment.getEnvironment(1, 1); 
	}
	
	/**
	 * If a lifeform is in cell (row, col), the lifeform will pick up weapon#1 (if available)
	 * If the lifeform is currently holding a weapon, it will be swapped for weapon#1
	 * 
	 * If there is no lifeform in the cell, nothing happens. 
	 * IF there is no weapon#1, nothing happens
	 * @see command.Command#execute(int, int)
	 */
	@Override
	public void execute(int row, int col) {
		LifeForm life = e.getLifeForm(row, col); 
		Weapon[] weapons = e.getWeapons(row, col);
		
		if (life != null)
		{
			if (weapons[0] != null)
			{
				e.removeWeapon(weapons[0], row, col); 
				e.addWeapon(life.getWeapon(), row, col); 
				life.dropWeapon(); 
				life.pickUpWeapon(weapons[0]); 
				//update environment observers
				e.updateCell(row, col);
			}
			
		}

	}

}

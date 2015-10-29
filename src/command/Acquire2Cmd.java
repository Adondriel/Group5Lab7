/**
 * 
 */
package command;

import lifeform.LifeForm;
import weapon.Weapon;
import environment.Environment;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class Acquire2Cmd implements Command {
	private Environment e; 
	
	public Acquire2Cmd()
	{
		//This is sloppy, but there is no way a call to a command constructor should be the first call to getEnvironment()
		e = Environment.getEnvironment(1, 1);  
	}
	
	/**
	 * If a lifeform is in cell (row, col), the lifeform will pick up weapon#2 (if available)
	 * If the lifeform is currently holding a weapon, it will be swapped for weapon#2
	 * 
	 * If there is no lifeform in the cell, nothing happens. 
	 * IF there is no weapon#2, nothing happens
	 * @see command.Command#execute(int, int)
	 */
	@Override
	public void execute(int row, int col) {
		LifeForm life = e.getLifeForm(row, col); 
		Weapon[] weapons = e.getWeapons(row, col);
		
		if (life != null)
		{
			if (weapons[1] != null)
			{
				e.removeWeapon(weapons[1], row, col); 
				e.addWeapon(life.getWeapon(), row, col); 
				life.dropWeapon(); 
				life.pickUpWeapon(weapons[1]);
				
				//update environment observers
				e.updateCell(row, col);
			}
			
		}

	}
}

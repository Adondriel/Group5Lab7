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
public class DropCmd implements Command {

	private Environment e; 
	
	public DropCmd()
	{
		//This is sloppy, but there is no way a call to a command constructor should be the first call to getEnvironment()
		e = Environment.getEnvironment(1, 1); 
	}
	
	/**
	 * If there is a lifeform holding a weapon in cell (row, col), try to drop the weapon. 
	 * If there is room for another weapon in the cell, the lifeform can drop its weapon. 
	 * 
	 * If there is no room for another weapon in the cell, the lifeform keeps its weapon. 
	 * If there is no lifeform, or if the lifeform does not have a weapon, nothing happens. 
	 * @see command.Command#execute(int, int)
	 */
	@Override
	public void execute(int row, int col) {
		LifeForm life = e.getLifeForm(row, col); 
		
		if (life != null && life.hasWeapon())
		{
			Weapon[] weapons = e.getWeapons(row, col); 
			
			if(weapons[0] == null || weapons[1]==null)
			{
				e.addWeapon(life.dropWeapon(), row, col); 
				
				//update the environment observers
				e.updateCell(row, col);
			}
			
		}

	}

}

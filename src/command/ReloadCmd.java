/**
 * 
 */
package command;

import lifeform.LifeForm;
import environment.Environment;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class ReloadCmd implements Command {

	private Environment e; 
	
	/**
	 * 
	 */
	public ReloadCmd()
	{
		//This is sloppy, but there is no way a call to a command constructor should be the first call to getEnvironment()
		e = Environment.getEnvironment(1, 1); 
	}
	
	/**
	 * if the cell at (row, col) contains a lifeform with a weapon, the weapon is realoded
	 * if the cell has no lifeform, or the lifeform has no weapon, nothing happens
	 * @see command.Command#execute(int, int)
	 */
	@Override
	public void execute(int row, int col) {
		LifeForm temp = e.getLifeForm(row, col); 
		
		if (temp != null && temp.hasWeapon())
		{
			temp.getWeapon().reload();
			//let the environment observers know that this cell has changed
			e.updateCell(row, col);
		}

	}

}

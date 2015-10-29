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
public class MoveCmd implements Command {

	Environment e; 
	
	/**
	 * creates a move command that operates in an environment size: rows x cols
	 */
	public MoveCmd()
	{
		//This is sloppy, but there is no way a call to a command constructor should be the first call to getEnvironment()
		e = Environment.getEnvironment(1, 1); 
	}
	
	/**
	 * If a lifeform exists in the cell (row, col), moves an object at max speed in it's current direction
	 * the lifeform will not move off the board or into an occupied square. It will get as close as possible to its destination
	 * @see command.Command#execute(int, int)
	 */
	@Override
	public void execute(int row, int col) {
		LifeForm life = e.getLifeForm(row, col); 
		
		if (life != null)
		{
			int distanceTravelled = e.move(life); 
			
			if (distanceTravelled > 0)
			{
				if (life.getDirection().equals("North"))
				{
					//update environment observers about the change
					e.updateCell(row, col);
					e.updateCell(row-distanceTravelled, col);
				}
				else if (life.getDirection().equals("South"))
				{
					//update environment observers about the change
					e.updateCell(row, col);
					e.updateCell(row+distanceTravelled, col);
				}
				else if (life.getDirection().equals("West"))
				{
					//update environment observers about the change
					e.updateCell(row, col);
					e.updateCell(row, col-distanceTravelled);
				}
				else if (life.getDirection().equals("East"))
				{
					//update environment observers about the change
					e.updateCell(row, col);
					e.updateCell(row, col+distanceTravelled);
				}
			}
		}
	}

}

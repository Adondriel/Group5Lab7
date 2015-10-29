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
public class AttackCmd implements Command {

	private Environment e; 
	
	/**
	 * 
	 */
	public AttackCmd()
	{
		//This is sloppy, but there is no way a call to a command constructor should be the first call to getEnvironment()
		e = Environment.getEnvironment(1, 1); 
	}
	
	/**
	 * if a lifeform exists at the row, col coordinates
	 * it will attack the first lifeform encountered in a straight line, as long as it is in its attack range
	 */
	@Override
	public void execute(int row, int col) {
		LifeForm player = e.getLifeForm(row, col); 
		
		//if there is a player in this cell
		if (player != null) 
		{
			int attackRange = e.getLifeForm(row, col).getAttackRange(); 
			int cellDistance = attackRange/5; 
			
			//look in the direction the lifeform is facing
			//the first lifeform found in range is attacked
			for (int i = 1; i <= cellDistance; i++)
			{
				if (player.getDirection().equals("North"))
				{
					//check for a lifeform in this cell
					if (row - i >= 0 && e.getLifeForm(row-i, col) != null)
					{
						//found an opponent in range
						LifeForm opponent = e.getLifeForm(row-i, col); 
						
						//attack this opponent, we are just doing the straight line distance
						player.attack(opponent, i*5);
						
						//let the environment signal that the player and the opponent have potentially changed their status
						e.updateCell(row, col);
						e.updateCell(row-i, col);
						
						//exit this loop
						break; 
					}				
				}
				
				if (player.getDirection().equals("South"))
				{
					//check for a lifeform in this cell
					if (row + i < e.getNumRows() && e.getLifeForm(row+i, col) != null)
					{
						//found an opponent in range
						LifeForm opponent = e.getLifeForm(row+i, col); 
						
						//attack this opponent, we are just doing the straight line distance
						player.attack(opponent, i*5);
						
						//let the environment signal that the player and the opponent have potentially changed their status
						e.updateCell(row, col);
						e.updateCell(row+i, col);
						
						//exit this loop
						break; 
					}
				}
				if (player.getDirection().equals("East"))
				{
					//check for a lifeform in this cell
					if (col + i < e.getNumCols() && e.getLifeForm(row, col+i) != null)
					{
						//found an opponent in range
						LifeForm opponent = e.getLifeForm(row, col+i); 
						
						//attack this opponent, we are just doing the straight line distance
						player.attack(opponent, i*5);
						
						//let the environment signal that the player and the opponent have potentially changed their status
						e.updateCell(row, col);
						e.updateCell(row, col+i);
						
						//exit this loop
						break; 
					}
				}
				
				if (player.getDirection().equals("West"))
				{
					//check for a lifeform in this cell
					if (col - i >= 0 && e.getLifeForm(row, col-i) != null)
					{
						//found an opponent in range
						LifeForm opponent = e.getLifeForm(row, col-i); 
						
						//attack this opponent, we are just doing the straight line distance
						player.attack(opponent, i*5);
						
						//let the environment signal that the player and the opponent have potentially changed their status
						e.updateCell(row, col);
						e.updateCell(row, col-i);
						
						//exit this loop
						break; 
					}
				}
			}
		}

	}

}

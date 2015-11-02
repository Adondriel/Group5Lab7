package environment;
import java.util.ArrayList;

import exceptions.EnvironmentException;
import gameplay.EnvironmentObserver;
import gameplay.TimerObserver;
import weapon.Pistol;
import weapon.Weapon;
import lifeform.LifeForm;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class Environment {
	
	private Cell[][] cells; //the 2D array of cells in the environment
	private static Environment e; //the singleton Environment
	private ArrayList<EnvironmentObserver> observers; //observers of the Environment
	public static int focusRow; //use these to tell the Invoker GUI which cell to send with commands 
	public static int focusCol; 
	
	/**
	 * creates a new Environment
	 * @param rows the number of rows in the Environment
	 * @param cols the number of columns in the Environment
	 */
	private Environment(int rows, int cols)
	{
		cells = new Cell[rows][cols]; 
		
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j <cols; j++)
			{
				cells[i][j] = new Cell(); 
			}
		}
		
		observers = new ArrayList<EnvironmentObserver>(); 
	}
	
	/**
	 * 
	 * @param rows
	 * @param cols
	 * @return the Singleton instance of the Environment
	 */
	public static Environment getEnvironment(int rows, int cols)
	{
		if (e == null)
		{
			e = new Environment(rows, cols); 
		}

	return e; 
	}
	
	/**
	 * returns a specified cell in the Environment
	 * @param row
	 * @param col
	 * @return the LifeForm at the location, will return null if the cell was off the board
	 * 
	 */
	public LifeForm getLifeForm(int row, int col)
	{
		try {
			return e.cells[row][col].getLifeForm();
		} catch (ArrayIndexOutOfBoundsException e) {
			return null; 
		} 
	}

	/**
	 * adds a LifeForm to a Cell in the Environment
	 * @param entity
	 * @param row
	 * @param col
	 * @return true is LifeForm was added
	 * reasons for failure: A LifeForm is already in the cell. OR the cell was off the board. 
	 */
	public boolean addLifeForm(LifeForm entity, int row, int col)
	{
		try
		{
			//tell the LifeForm where it will be
			entity.setLocation(row, col);
			return e.cells[row][col].addLifeForm(entity); 
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return false; 
		}
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return the LifeForm removed. Will return null if the cell was off the grid. 
	 */
	public LifeForm removeLifeForm(int row, int col)
	{
		try {
			//reset the location of the LifeForm to (-1, -1)
			LifeForm entity = e.cells[row][col].removeLifeForm();
			if (entity != null)
			{
				entity.setLocation(-1, -1);
			}
			return entity; 
		} catch (ArrayIndexOutOfBoundsException e) {
			return null; 
		} 
	}

	/**
	 * @return the number of rows in the Environment
	 */
	public int getNumRows() {
		return e.cells.length;
	}

	/**
	 * @return the number of columns in the Environment
	 */
	public int getNumCols() {
		return e.cells[0].length;
	}

	/**
	 * @param weapon
	 * @param row
	 * @param col
	 * @return true if the Weapon was added, false if the Weapon could not be added. 
	 * There are several reasons for failure: There are no spots available. 
	 * The Weapon instance is already in the cell. OR the cell was off the board. 
	 */
	public boolean addWeapon(Weapon weapon, int row, int col) {
		try
		{
			return e.cells[row][col].addWeapon(weapon); 
			
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return false; 
		}
		
	}

	/**
	 * @param p
	 * @param i
	 * @param j
	 * @return the Weapon removed from the Cell. Returns null of the cell was off the grid
	 */
	public Weapon removeWeapon(Weapon weapon, int row, int col) {
		try
		{
			return e.cells[row][col].removeWeapon(weapon); 
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return null; 
		}
	}

	/**
	 * @return an array containing up to two weapons
	 */
	public Weapon[] getWeapons(int row, int col) {
		try
		{
			Weapon[] list  = new Weapon[2];
			list[0] = e.cells[row][col].getWeapon1(); 
			list[1] = e.cells[row][col].getWeapon2(); 
			return list;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			return null; 
		}
		
	}
	
	/**
	 * 
	 * @param row1 cell1
	 * @param col1 cell1
	 * @param row2 cell2
	 * @param col2 cell2
	 * @return the distance between two cell locations
	 */
	public double getDistance(int row1, int col1, int row2, int col2) throws EnvironmentException
	{
		if (row1 < 0 || row1 > getNumRows()-1 || row2 < 0 || row2 > getNumRows()-1 ||
			col1 < 0 || col1 > getNumCols()-1 || col2 < 0 || col2 > getNumCols()-1)
		{
			throw new EnvironmentException("Cell indicies are out of bounds."); 
		}
		double distance = 0.0; 
		//cells are in the same row
		if (row1 == row2)
		{
			distance = Math.abs(col1 - col2); 
		}
		//cells are in the same column
		else if (col1 == col2)
		{
			distance = Math.abs(row1 - row2);
		}
		//cells are on a diagonal
		else
		{
			double a = row1 - row2; 
			double b = col1 - col2; 
			distance = Math.sqrt(a*a + b*b); 
		}
		return distance*5.0; 
	}
	
	/**
	 * 
	 * @param lifeform1
	 * @param lifeform2
	 * @return the distance between 2 Lifeforms 
	 * @throws EnvironmentException
	 */
	public double getDistance(LifeForm lifeform1, LifeForm lifeform2) throws EnvironmentException
	{
		return getDistance(lifeform1.getRow(), lifeform1.getCol(), lifeform2.getRow(), lifeform2.getCol()); 
	}
	
	/**
	 * removes all LifeForms and Weapons from the Environment
	 * useful for tests and game resets
	 */
	public void clearBoard()
	{
		Weapon temp = null; 
		for (int i = 0; i< getNumRows(); i++)
		{
			for (int j = 0; j < getNumCols(); j++)
			{
				e.cells[i][j].removeLifeForm(); 
				temp = e.cells[i][j].getWeapon1(); 
				e.cells[i][j].removeWeapon(temp); 
				temp = e.cells[i][j].getWeapon2(); 
				e.cells[i][j].removeWeapon(temp); 
			}
		}
	}
	
	/**
	 * changes the location of a lifeform by moving it at maxSpeed in the direction its facing
	 * a lifeform can move through other lifeforms to get to its distination, but the target cell must be clear
	 * If the target cell is not clear, or is off the board, the lifeform will get as close as it can
	 * @param lifeform
	 * @return the total number of cells moved
	 */
	public int move(LifeForm lifeform)
	{
		int oldRow = lifeform.getRow(); 
		int oldCol = lifeform.getCol(); 
		int newRow = -1; //not used yet
		int newCol = -1; 
		int howFar = lifeform.getMaxSpeed(); 
		int travelled = howFar; 
		
		//make sure the LifeForm is on the grid
		if(oldRow > -1 && oldCol > -1)
		{
			//move up / north
			if(lifeform.getDirection().equals("North"))
			{
				newRow = oldRow - howFar; 
				//make we haven't run off the grid
				if (newRow < 0)
				{
					//adjust howFar
					howFar = howFar + newRow; 
					travelled = howFar; 
					//adjust newRow
					newRow = 0; 
				}
				newCol = oldCol; 
				
				//probably will want to check the destination first
				//can we put a LifeForm there?
				if(hasLifeForm(newRow, newCol))
				{
					int i = 1; 
					//back down one, if we can, and try again
					while(newRow != oldRow)
					{
						newRow = oldRow - (howFar-i);
						if(!hasLifeForm(newRow, newCol))
						{
							//we've found an open spot
							travelled = howFar-i; 
							break; 
						}
						i++; 
					}
				}
				
				//if we can't move North at all
				if(newRow == oldRow)
				{
					return 0; 
				}
				//otherwise go ahead
			}
			//move down / south
			else if(lifeform.getDirection().equals("South"))
			{
				newRow = oldRow + howFar; 
				//make we haven't run off the grid
				if (newRow > e.cells.length-1)
				{
					//adjust howFar
					howFar = howFar - (newRow - (e.cells.length-1)); 
					travelled = howFar; 
					//adjust newRow
					newRow = e.cells.length-1; 
				}
				newCol = oldCol; 
				
				//probably will want to check the destination first
				//can we put a LifeForm there?
				if(hasLifeForm(newRow, newCol))
				{
					int i = 1; 
					//back up one, if we can, and try again
					while(newRow != oldRow)
					{
						newRow = oldRow + (howFar-i);
						if(!hasLifeForm(newRow, newCol))
						{
							//we've found an open spot
							travelled = howFar -i; 
							break; 
						}
						i++; 
					}
				}
				
				//if we can't move South at all
				if(newRow == oldRow)
				{
					return 0; 
				}
				//otherwise go ahead
			}
			//move left / east
			else if(lifeform.getDirection().equals("East"))
			{
				newRow = oldRow; 
				newCol = oldCol + howFar;
				//make we haven't run off the grid
				if (newCol > e.cells[0].length-1)
				{
					//adjust howFar
					howFar = howFar - (newCol - (e.cells[0].length-1)); 
					travelled = howFar; 
					//adjust newRow
					newCol = e.cells[0].length-1; 
				}
				
				//probably will want to check the destination first
				//can we put a LifeForm there?
				if(hasLifeForm(newRow, newCol))
				{
					int i = 1; 
					//back right one, if we can, and try again
					while(newCol != oldCol)
					{
						newCol = oldCol + (howFar-i);
						if(!hasLifeForm(newRow, newCol))
						{
							//we've found an open spot
							travelled = howFar-i; 
							break; 
						}
						i++; 
					}
				}
				
				//if we can't move East at all
				if(newCol == oldCol)
				{
					return 0; 
				}
				//otherwise go ahead
			}
			//move right / west
			else if(lifeform.getDirection().equals("West"))
			{
				newRow = oldRow; 
				newCol = oldCol - howFar; 
				//make we haven't run off the grid
				if (newCol < 0)
				{
					//adjust howFar
					howFar = howFar + newCol; 
					travelled = howFar; 
					//adjust newRow
					newCol = 0; 
				}
				
				//probably will want to check the destination first
				//can we put a LifeForm there?
				if(hasLifeForm(newRow, newCol))
				{
					int i = 1; 
					//back right one, if we can, and try again
					while(newCol != oldCol)
					{
						newCol = oldCol - (howFar-i);
						if(!hasLifeForm(newRow, newCol))
						{
							//we've found an open spot
							travelled = howFar-i; 
							break; 
						}
						i++; 
					}
				}
				
				//if we can't move West at all
				if(newCol == oldCol)
				{
					return 0; 
				}
				//otherwise go ahead
			}
			//invalid direction
			else
			{
				return 0; 
			}
			
			//remove this LifeForm from it's current location and move it to the new one
			e.removeLifeForm(oldRow, oldCol); 
			//move it to the new location
			e.addLifeForm(lifeform, newRow, newCol);
			return travelled; 
		}
		
		return 0; 
	}
	
	private boolean hasLifeForm(int row, int col)
	{
		if (e.cells[row][col].getLifeForm() == null)
		{
			return false; 
		}
		return true; 
	}
	
	/**
	 * add an observer
	 * @param observer
	 */
	public void addObserver(EnvironmentObserver observer)
	{
		observers.add(observer); 
	}
	/**
	 * remove an observer, if it exists
	 * @param observer
	 */
	public void removeObserver(EnvironmentObserver observer)
	{
		if (observers.contains(observer))
		{
			observers.remove(observers.indexOf(observer));
		}
	}
	
	/**
	 * inform all observers about the new state of a Cell
	 * @param row the row that contains the cell that changed
	 * @param col the columns that contains the cell that changed
	 */
	public void updateCell(int row, int col)
	{
		Cell cell = e.cells[row][col]; 
		for(EnvironmentObserver ob: observers)
		{
			ob.updateCell(row, col, cell.getLifeForm(), cell.getWeapon1(), cell.getWeapon2());		
		}
	}
	
	/**
	 * @param row
	 * @param col
	 * @return the cell at row, col
	 */
	public Cell getCell(int row, int col){
		return cells[row][col];
	}
}

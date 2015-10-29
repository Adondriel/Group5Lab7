package environment;
import static org.junit.Assert.*;
import lifeform.MockLifeForm;

import org.junit.Before;
import org.junit.Test;

import exceptions.EnvironmentException;
import weapon.ChainGun;
import weapon.Pistol;
import weapon.Weapon;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class TestEnvironment 
{
	private int NUMROWS = 100; 
	private int NUMCOLS = 100; 
	private Environment e; 
	/**
	 * clear the board before each test to avoid Singleton collisions
	 * in the Cells of the Environment
	 */
	@Before
	public void clearBoard()
	{
		e = Environment.getEnvironment(NUMROWS, NUMCOLS);
		NUMROWS = e.getNumRows(); 
		NUMCOLS = e.getNumCols(); 
		e.clearBoard();
	}
	
	/************ LAB 6 (Decorator) TESTS BELOW THIS POINT ******************/
	/**
	 * test that a LifeForm moves at speed in all four directions if there are no obstacles on the path
	 */
	@Test
	public void testMoveNoObstaclesNoWalls()
	{
		MockLifeForm m = new MockLifeForm("Bob", 40, 10); 
		e.addLifeForm(m, 0, 0); 
		m.setDirection("South"); 
		
		//move south
		assertEquals(3, e.move(m)); 
		assertEquals(m.getRow(), 3); 
		assertEquals(m.getCol(), 0); 
		assertNull(e.getLifeForm(0, 0));
		assertNotNull(e.getLifeForm(3, 0)); 
		
		//move east
		m.setDirection("East"); 
		assertEquals(3, e.move(m));
		assertEquals(m.getRow(), 3); 
		assertEquals(m.getCol(), 3); 
		assertNull(e.getLifeForm(3, 0));
		assertNotNull(e.getLifeForm(3, 3)); 
		
		//move north
		m.setDirection("North"); 
		assertEquals(3, e.move(m)); 
		assertEquals(m.getRow(), 0); 
		assertEquals(m.getCol(), 3); 
		assertNull(e.getLifeForm(3, 3));
		assertNotNull(e.getLifeForm(0, 3));
		
		//move west
		m.setDirection("West"); 
		assertEquals(3, e.move(m));
		assertEquals(m.getRow(), 0); 
		assertEquals(m.getCol(), 0); 
		assertNull(e.getLifeForm(0, 3));
		assertNotNull(e.getLifeForm(0, 0));
	}
	
	/**
	 * tests that a LifeForm can move through cells that contain other LifeForms
	 */
	@Test
	public void testMoveThroughObstaclesNoWalls()
	{
		MockLifeForm m = new MockLifeForm("Bob", 40, 10);
		MockLifeForm o1 = new MockLifeForm("Obstacle1", 40, 10);
		MockLifeForm o2 = new MockLifeForm("Obstacle2", 40, 10);
		
		e.addLifeForm(m, 0, 0); 
		m.setDirection("South");
		e.addLifeForm(o1, 1, 0); 
		e.addLifeForm(o2, 2, 0); 
		
		//move south
		assertEquals(3, e.move(m)); 
		assertEquals(m.getRow(), 3); 
		assertEquals(m.getCol(), 0); 
		assertNull(e.getLifeForm(0, 0));
		assertNotNull(e.getLifeForm(3, 0)); 
		
		//move east
		m.setDirection("East"); 
		e.removeLifeForm(1, 0); 
		e.removeLifeForm(2, 0); 
		e.addLifeForm(o1, 3, 1); 
		e.addLifeForm(o2, 3, 2); 
		assertEquals(3, e.move(m)); 
		assertEquals(m.getRow(), 3); 
		assertEquals(m.getCol(), 3); 
		assertNull(e.getLifeForm(3, 0));
		assertNotNull(e.getLifeForm(3, 3)); 
		
		//move north
		m.setDirection("North"); 
		e.removeLifeForm(3, 1); 
		e.removeLifeForm(3, 2); 
		e.addLifeForm(o1, 1, 3); 
		e.addLifeForm(o2, 2, 3); 
		assertEquals(3, e.move(m));
		assertEquals(m.getRow(), 0); 
		assertEquals(m.getCol(), 3); 
		assertNull(e.getLifeForm(3, 3));
		assertNotNull(e.getLifeForm(0, 3));
		
		//move west
		m.setDirection("West"); 
		e.removeLifeForm(1, 3); 
		e.removeLifeForm(2, 3); 
		e.addLifeForm(o1, 0, 1); 
		e.addLifeForm(o2, 0, 2); 
		assertEquals(3, e.move(m));
		assertEquals(m.getRow(), 0); 
		assertEquals(m.getCol(), 0); 
		assertNull(e.getLifeForm(0, 3));
		assertNotNull(e.getLifeForm(0, 0));
	}
	
	/**
	 * Tests how LifeForms moves when there is an obstacle at the destination square
	 * A->|   |   | O |
	 */
	@Test
	public void testObstacleBlocksLanding()
	{
		MockLifeForm m = new MockLifeForm("Bob", 40, 10);
		MockLifeForm o1 = new MockLifeForm("Obstacle1", 40, 10);
		
		e.addLifeForm(m, 0, 0); 
		m.setDirection("South");
		e.addLifeForm(o1, 3, 0); 
		
		//*****obstacle only at destination *****\\
		//move south, destination 3, 0
		//lands at 2, 0
		assertEquals(2, e.move(m)); 
		assertEquals(m.getRow(), 2); 
		assertEquals(m.getCol(), 0); 
		assertNull(e.getLifeForm(0, 0));
		assertNotNull(e.getLifeForm(3, 0)); 
		assertNotNull(e.getLifeForm(2, 0)); 
		
		//move east, destination 2, 3
		//lands at 2, 2
		m.setDirection("East"); 
		e.removeLifeForm(3, 0);
		e.addLifeForm(o1, 2, 3); 
		assertEquals(2, e.move(m)); 
		assertEquals(m.getRow(), 2); 
		assertEquals(m.getCol(), 2); 
		assertNull(e.getLifeForm(2, 0));
		assertNotNull(e.getLifeForm(2, 3)); 
		assertNotNull(e.getLifeForm(2, 2));
		
		//move m East to 2, 5 then South to 5, 5
		//so we can maneuver back West and North
		e.move(m); 
		m.setDirection("South"); 
		e.move(m);
		
		
		//move north, destination 2, 5
		//lands 3, 5
		m.setDirection("North"); 
		e.removeLifeForm(2, 3);
		e.addLifeForm(o1, 2, 5); 
		assertEquals(2, e.move(m));  
		assertEquals(m.getRow(), 3); 
		assertEquals(m.getCol(), 5); 
		assertNull(e.getLifeForm(5, 5));
		assertNotNull(e.getLifeForm(2, 5)); 
		assertNotNull(e.getLifeForm(3, 5));
		
		//move west, destination 3, 2
		//lands 3, 3
		m.setDirection("West"); 
		e.removeLifeForm(2, 5);
		e.addLifeForm(o1, 3, 2); 
		assertEquals(2, e.move(m)); 
		assertEquals(m.getRow(), 3); 
		assertEquals(m.getCol(), 3); 
		assertNull(e.getLifeForm(3, 5));
		assertNotNull(e.getLifeForm(3, 3)); 
		assertNotNull(e.getLifeForm(3, 2));
		
	}
	
	/**
	 * Tests how LifeForms moves when there is an obstacle at the destination square
	 * and one blocking the one before the destination
	 * 
	 * A->|   | O | O |
	 */
	@Test
	public void testObstacleBlocksLandingAndPath()
	{
		MockLifeForm m = new MockLifeForm("Bob", 40, 10);
		MockLifeForm o1 = new MockLifeForm("Obstacle1", 40, 10);
		MockLifeForm o2 = new MockLifeForm("Obstacle2", 40, 10);
		
		e.addLifeForm(m, 0, 0); 
		m.setDirection("South");
		e.addLifeForm(o1, 3, 0); 
		e.addLifeForm(o2, 2, 0);
		
		//*****obstacle only at destination *****\\
		//move south, destination 3, 0
		//lands at 1, 0
		assertEquals(1, e.move(m));   
		assertEquals(m.getRow(), 1); 
		assertEquals(m.getCol(), 0); 
		
		//move east, destination 1, 3
		//lands at 1, 1
		m.setDirection("East"); 
		e.removeLifeForm(3, 0);
		e.removeLifeForm(2, 0); 
		e.addLifeForm(o1, 1, 3);
		e.addLifeForm(o2, 1, 2);
		assertEquals(1, e.move(m));
		assertEquals(m.getRow(), 1); 
		assertEquals(m.getCol(), 1); 
		
		//move m East to 1, 4 then South to 4, 4
		//so we can maneuver back West and North
		e.move(m); 
		m.setDirection("South"); 
		e.move(m);
		
		
		//move north, destination 1, 4
		//lands 3, 4
		m.setDirection("North"); 
		e.removeLifeForm(1, 3);
		e.removeLifeForm(1, 2);
		e.addLifeForm(o1, 1, 4); 
		e.addLifeForm(o1, 2, 4);
		assertEquals(1, e.move(m)); 
		assertEquals(m.getRow(), 3); 
		assertEquals(m.getCol(), 4); 
		
		//move west, destination 3, 1
		//lands 3, 3
		m.setDirection("West"); 
		e.removeLifeForm(1, 4);
		e.removeLifeForm(2, 4);
		e.addLifeForm(o1, 3, 1); 
		e.addLifeForm(o1, 3, 2); 
		assertEquals(1, e.move(m));
		assertEquals(m.getRow(), 3); 
		assertEquals(m.getCol(), 3); 
		
	}
	
	/**
	 * Tests how LifeForms moves when there is an obstacle at the destination square
	 * and one blocking the one before the inception
	 * 
	 * A->| O |   | O |
	 */
	@Test
	public void testObstacleBlocksLandingAndPathPartial()
	{
		MockLifeForm m = new MockLifeForm("Bob", 40, 10);
		MockLifeForm o1 = new MockLifeForm("Obstacle1", 40, 10);
		MockLifeForm o2 = new MockLifeForm("Obstacle2", 40, 10);
		MockLifeForm o3 = new MockLifeForm("Obstacle3", 40, 10);
		
		e.addLifeForm(m, 0, 0); 
		m.setDirection("South");
		e.addLifeForm(o1, 3, 0); 
		e.addLifeForm(o3, 1, 0);
		
		//*****obstacle only at destination *****\\
		//move south, destination 3, 0
		//lands at 2, 0
		assertEquals(2, e.move(m)); 
		assertEquals(m.getRow(), 2); 
		assertEquals(m.getCol(), 0); 
		
		//move east, destination 2, 3
		//lands at 2, 2
		m.setDirection("East"); 
		e.removeLifeForm(3, 0);
		e.removeLifeForm(1, 0); 
		e.addLifeForm(o1, 2, 3);
		e.addLifeForm(o2, 2, 1);
		assertEquals(2, e.move(m));
		assertEquals(m.getRow(), 2); 
		assertEquals(m.getCol(), 2); 
		
		//move m East to 2, 5 then South to 5, 5
		//so we can maneuver back West and North
		e.move(m); 
		m.setDirection("South"); 
		e.move(m);
		
		
		//move north, destination 2, 5
		//lands 3, 5
		m.setDirection("North"); 
		e.removeLifeForm(2, 3);
		e.removeLifeForm(2, 1);
		e.addLifeForm(o1, 2, 5); 
		e.addLifeForm(o1, 4, 5);
		assertEquals(2, e.move(m)); 
		assertEquals(m.getRow(), 3); 
		assertEquals(m.getCol(), 5); 
		
		//move west, destination 3, 2
		//lands 3, 3
		m.setDirection("West"); 
		e.removeLifeForm(2, 5);
		e.removeLifeForm(4, 5);
		e.addLifeForm(o1, 3, 2); 
		e.addLifeForm(o1, 3, 4); 
		assertEquals(2, e.move(m));
		assertEquals(m.getRow(), 3); 
		assertEquals(m.getCol(), 3); 
	}
	
	/**
	 * Tests how LifeForms moves when there is an obstacle at the destination square
	 * and the entire path is blocked
	 * 
	 * A->| O | O | O |
	 */
	@Test
	public void testObstacleBlocksLandingAndPathFull()
	{
		MockLifeForm m = new MockLifeForm("Bob", 40, 10);
		MockLifeForm o1 = new MockLifeForm("Obstacle1", 40, 10);
		MockLifeForm o2 = new MockLifeForm("Obstacle2", 40, 10);
		MockLifeForm o3 = new MockLifeForm("Obstacle3", 40, 10);
		
		e.addLifeForm(m, 0, 0); 
		m.setDirection("South");
		e.addLifeForm(o1, 3, 0); 
		e.addLifeForm(o2, 2, 0);
		e.addLifeForm(o3, 1, 0); 
		
		//*****obstacle only at destination *****\\
		//move south, destination 3, 0
		//lands at 0, 0
		assertEquals(0, e.move(m)); 
		assertEquals(m.getRow(), 0); 
		assertEquals(m.getCol(), 0); 
		
		//move east, destination 0, 3
		//lands at 0, 0
		m.setDirection("East"); 
		e.removeLifeForm(3, 0);
		e.removeLifeForm(2, 0); 
		e.removeLifeForm(1, 0); 
		e.addLifeForm(o1, 0, 3);
		e.addLifeForm(o2, 0, 2);
		e.addLifeForm(o3, 0, 1);
		assertEquals(0, e.move(m)); 
		assertEquals(m.getRow(), 0); 
		assertEquals(m.getCol(), 0); 
		
		//move m South to 3, 0 then East to 3, 3
		//so we can maneuver back West and North
		m.setDirection("South"); 
		e.move(m);
		m.setDirection("East"); 
		e.move(m);
		
		
		//move north, destination 0, 3
		//lands 3, 3
		m.setDirection("North"); 
		e.removeLifeForm(0, 3);
		e.removeLifeForm(0, 2);
		e.removeLifeForm(0, 1);
		e.addLifeForm(o1, 0, 3); 
		e.addLifeForm(o2, 1, 3);
		e.addLifeForm(o3, 2, 3);
		assertEquals(0, e.move(m)); 
		assertEquals(m.getRow(), 3); 
		assertEquals(m.getCol(), 3); 
		
		//move west, destination 3, 0
		//lands 3, 3
		m.setDirection("West"); 
		e.removeLifeForm(0, 3);
		e.removeLifeForm(1, 3);
		e.removeLifeForm(2, 3);
		e.addLifeForm(o1, 3, 0); 
		e.addLifeForm(o2, 3, 1); 
		e.addLifeForm(o2, 3, 2); 
		assertEquals(0, e.move(m)); 
		assertEquals(m.getRow(), 3); 
		assertEquals(m.getCol(), 3); 
	}
	
	/**
	 * tests how LifeForms handle approaching the edge of the grid
	 * 
	 * A->|   |   |X| <--- end of grid, can only move 2 spaces
	 */
	@Test
	public void testMoveIntoWallAfter2()
	{
		MockLifeForm m = new MockLifeForm("Bob", 40, 10);
		
		//start at (2, 0), move North
		e.addLifeForm(m, 2, 0); 
		m.setDirection("North"); 
		assertEquals(2, e.move(m)); 
		assertEquals(0, m.getRow()); 
		assertEquals(0, m.getCol()); 
		
		
		//relocate m to (0, 2), move west
		e.removeLifeForm(0, 0); 
		e.addLifeForm(m, 0, 2); 
		m.setDirection("West"); 
		assertEquals(2, e.move(m));  
		assertEquals(0, m.getRow()); 
		assertEquals(0, m.getCol()); 
		
		//relocate to (0, 97), move East
		e.removeLifeForm(0, 0); 
		e.addLifeForm(m, 0, NUMCOLS-3); 
		m.setDirection("East"); 
		assertEquals(2, e.move(m)); 
		assertEquals(0, m.getRow()); 
		assertEquals(NUMCOLS-1, m.getCol()); 
		
		//relocate to (97, 0), move South
		e.removeLifeForm(0, NUMCOLS-3); 
		e.addLifeForm(m, NUMROWS-3, 0); 
		m.setDirection("South"); 
		assertEquals(2, e.move(m));  
		assertEquals(NUMROWS-1, m.getRow()); 
		assertEquals(0, m.getCol()); 
	}
	
	/**
	 * tests how LifeForms handle approaching the edge of the grid
	 * 
	 * A->|   |X| <--- end of grid, can only move 1 space
	 */
	@Test
	public void testMoveIntoWallAfter1()
	{
		MockLifeForm m = new MockLifeForm("Bob", 40, 10);
		
		//start at (1, 0), move North
		e.addLifeForm(m, 1, 0); 
		m.setDirection("North"); 
		assertEquals(1, e.move(m)); 
		assertEquals(0, m.getRow()); 
		assertEquals(0, m.getCol()); 
		
		
		//relocate m to (0, 1), move west
		e.removeLifeForm(0, 0); 
		e.addLifeForm(m, 0, 1); 
		m.setDirection("West"); 
		assertEquals(1, e.move(m)); 
		assertEquals(0, m.getRow()); 
		assertEquals(0, m.getCol()); 
		
		//relocate to (0, 98), move East
		e.removeLifeForm(0, 0); 
		e.addLifeForm(m, 0, NUMCOLS-2); 
		m.setDirection("East"); 
		assertEquals(1, e.move(m)); 
		assertEquals(0, m.getRow()); 
		assertEquals(NUMCOLS-1, m.getCol()); 
		
		//relocate to (98, 0), move South
		e.removeLifeForm(0, NUMCOLS-2); 
		e.addLifeForm(m, NUMROWS-2, 0); 
		m.setDirection("South"); 
		assertEquals(1, e.move(m)); 
		assertEquals(NUMROWS-1, m.getRow()); 
		assertEquals(0, m.getCol()); 
	}
	
	/************ LAB 5 (Singleton) TESTS BELOW THIS POINT ******************/
	/**
	 * creates an Environment of a different size
	 */
	@Test
	public void testSingleton()
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS); 
		assertEquals(NUMROWS, e.getNumRows()); 
		assertEquals(NUMCOLS, e.getNumCols()); 
		
		//now try to create a second instance with a different size
		Environment f = Environment.getEnvironment(2, 3); 
		assertEquals(NUMROWS, f.getNumRows()); 
		assertEquals(NUMCOLS, f.getNumCols()); 
	}
	
	/**
	 * tests how LifeForms handle approaching the edge of the grid
	 * 
	 * A->|X| <--- end of grid, cannot move
	 */
	@Test
	public void testMoveAtWall()
	{
		MockLifeForm m = new MockLifeForm("Bob", 40, 10);
		
		//start at (0, 0), move North
		e.addLifeForm(m, 0, 0); 
		m.setDirection("North"); 
		assertEquals(0, e.move(m));  
		assertEquals(0, m.getRow()); 
		assertEquals(0, m.getCol()); 
		
		
		//still at (0, 0), move west
		m.setDirection("West"); 
		assertEquals(0, e.move(m));
		assertEquals(0, m.getRow()); 
		assertEquals(0, m.getCol()); 
		
		//relocate to (0, 99), move East
		e.removeLifeForm(0, 0); 
		e.addLifeForm(m, 0, 99); 
		m.setDirection("East"); 
		assertEquals(0, e.move(m));
		assertEquals(0, m.getRow()); 
		assertEquals(99, m.getCol()); 
		
		//relocate to (99, 0), move South
		e.removeLifeForm(0, 99); 
		e.addLifeForm(m, 99, 0); 
		m.setDirection("South"); 
		assertEquals(0, e.move(m));
		assertEquals(99, m.getRow()); 
		assertEquals(0, m.getCol()); 
	}
	
	/**
	 * test that weapons and lifeforms can be added and removed successfully
	 * tests that entries outside of the bounds of the board return false or null as appropriate
	 */
	@Test
	public void testAddLifeFormAndWeapons()
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS);
		MockLifeForm m = new MockLifeForm("bob", 40); 
		MockLifeForm s = new MockLifeForm("sheryl", 50); 
		Pistol p = new Pistol(); 
		ChainGun c = new ChainGun();
		Pistol p1 = new Pistol(); 
		
		//fill the cell
		assertTrue(e.addLifeForm(m, 3, 4)); 
		assertTrue(e.addWeapon(p, 3, 4)); 
		assertTrue(e.addWeapon(c, 3, 4)); 
		
		//try adding an extra LifeForm
		assertFalse(e.addLifeForm(s, 3, 4));
		//try adding an extra Weapon
		assertFalse(e.addWeapon(p1, 3, 4)); 
		
		//remove the LifeForm
		assertEquals(m, e.removeLifeForm(3, 4)); 
		//remove Pistol p
		assertEquals(p, e.removeWeapon(p, 3, 4)); 
		
		//try to remove Pistol p1 (not in Cell)
		assertNull(e.removeWeapon(p1, 3, 4)); 
		
		//try to remove Pistol p (already removed)
		assertNull(e.removeWeapon(p, 3, 4)); 
		
		//remove the last Weapon
		assertEquals(c, e.removeWeapon(c, 3, 4));
		
		//add a new Weapon to the cell
		assertTrue(e.addWeapon(p1, 3, 4)); 
		//add a new LifeForm
		assertTrue(e.addLifeForm(s, 3, 4)); 
		
		//test for off the board requests
		assertFalse(e.addLifeForm(s, -1, -1)); 
		assertFalse(e.addWeapon(p, NUMROWS, NUMCOLS)); 
		assertNull(e.removeLifeForm(-1, -1)); 
		assertNull(e.removeWeapon(p, NUMROWS, NUMCOLS)); 
		
	}
	
	@Test
	public void testGetters()
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS);
		MockLifeForm m = new MockLifeForm("bob", 40); 
		Pistol p = new Pistol(); 
		ChainGun c = new ChainGun();
		
		//fill the cell
		assertTrue(e.addLifeForm(m, 5, 6)); 
		assertTrue(e.addWeapon(p, 5, 6)); 
		assertTrue(e.addWeapon(c, 5, 6)); 
		
		//get the elements in the cell
		assertEquals(m, e.getLifeForm(5, 6)); 
		
		Weapon[] list = new Weapon[2];
		list = e.getWeapons(5, 6); 
		assertEquals(p, list[0]); 
		assertEquals(c, list[1]); 
		
		//get size of the environment
		assertEquals(NUMROWS, e.getNumRows()); 
		assertEquals(NUMCOLS, e.getNumCols());
	}
	
	/**
	 * tests the distance given Cell coordinates
	 * @throws EnvironmentException
	 */
	@Test
	public void testDistance() throws EnvironmentException
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS);
		//the distance between a cell and itself
		assertEquals(0.0, e.getDistance(3, 3, 3, 3), 0.1); 
		
		//the distance between a cell and another cell that is only one away (NSEW)
		//north
		assertEquals(5.0, e.getDistance(3, 3, 2, 3), 0.1);
		//south
		assertEquals(5.0, e.getDistance(3, 3, 4, 3), 0.1);
		//east
		assertEquals(5.0, e.getDistance(3, 3, 3, 4), 0.1);
		//west
		assertEquals(5.0, e.getDistance(3, 3, 3, 2), 0.1);
		
		//the distance between a cell and another cell that is  only one away diagonally
		//northwest
		assertEquals(7.07, e.getDistance(3, 3, 2, 2),0.01); 
		//northeast
		assertEquals(7.07, e.getDistance(3, 3, 2, 4),0.01);
		//southwest
		assertEquals(7.07, e.getDistance(3, 3, 4, 2),0.01);
		//southeast
		assertEquals(7.07, e.getDistance(3, 3, 4, 4),0.01);
		
		//the distance between a cell and another cell that is farther away orthoganally
		//north
		assertEquals(15.0, e.getDistance(3, 3, 0, 3), 0.1);
		//south
		assertEquals(10.0, e.getDistance(3, 3, 5, 3), 0.1);
		//east
		assertEquals(15.0, e.getDistance(3, 3, 3, 6), 0.1);
		//west
		assertEquals(10.0, e.getDistance(3, 3, 3, 1), 0.1);
		
		//the distance between a cell and another cell that is farther away diagonally
		//northwest
		assertEquals(11.18, e.getDistance(3, 3, 2, 1),0.01); 
		//northeast
		assertEquals(11.18, e.getDistance(3, 3, 2, 5),0.01);
		//southwest
		assertEquals(11.18, e.getDistance(3, 3, 5, 2),0.01);
		//southeast
		assertEquals(11.18, e.getDistance(3, 3, 4, 5),0.01);
	}
	
	/**
	 * tests the distance given two LifeForms
	 * @throws EnvironmentException
	 */
	@Test
	public void testDistanceLifeForms() throws EnvironmentException
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS);
		MockLifeForm bob = new MockLifeForm("Bob", 10, 2);
		MockLifeForm sheryl = new MockLifeForm("Sheryl", 30, 5); 
		
		//put bob and sheryl in the same location (can't really happen in game)
		bob.setLocation(3, 3);
		sheryl.setLocation(3, 3);
		
		//the distance between a cell and itself
		assertEquals(0.0, e.getDistance(bob, sheryl), 0.1); 
		
		//the distance between a cell and another cell that is only one away (NSEW)
		//north
		sheryl.setLocation(2, 3);
		assertEquals(5.0, e.getDistance(bob, sheryl), 0.1);
		//south
		sheryl.setLocation(4, 3);
		assertEquals(5.0, e.getDistance(bob, sheryl), 0.1);
		//east
		sheryl.setLocation(3, 4);
		assertEquals(5.0, e.getDistance(bob, sheryl), 0.1);
		//west
		sheryl.setLocation(3, 2);
		assertEquals(5.0, e.getDistance(bob, sheryl), 0.1);
		
		//the distance between a cell and another cell that is  only one away diagonally
		//northwest
		sheryl.setLocation(2, 2);
		assertEquals(7.07, e.getDistance(bob, sheryl),0.01); 
		//northeast
		sheryl.setLocation(2, 4);
		assertEquals(7.07, e.getDistance(bob, sheryl),0.01);
		//southwest
		sheryl.setLocation(4, 2);
		assertEquals(7.07, e.getDistance(bob, sheryl),0.01);
		//southeast
		sheryl.setLocation(4, 4);
		assertEquals(7.07, e.getDistance(bob, sheryl),0.01);
		
		//the distance between a cell and another cell that is farther away orthoganally
		//north
		sheryl.setLocation(0, 3);
		assertEquals(15.0, e.getDistance(bob, sheryl), 0.1);
		//south
		sheryl.setLocation(5, 3);
		assertEquals(10.0, e.getDistance(bob, sheryl), 0.1);
		//east
		sheryl.setLocation(3, 6);
		assertEquals(15.0, e.getDistance(bob, sheryl), 0.1);
		//west
		sheryl.setLocation(3, 1);
		assertEquals(10.0, e.getDistance(bob, sheryl), 0.1);
		
		//the distance between a cell and another cell that is farther away diagonally
		//northwest
		sheryl.setLocation(2, 1);
		assertEquals(11.18, e.getDistance(bob, sheryl),0.01); 
		//northeast
		sheryl.setLocation(2, 5);
		assertEquals(11.18, e.getDistance(bob, sheryl),0.01);
		//southwest
		sheryl.setLocation(5, 2);
		assertEquals(11.18, e.getDistance(bob, sheryl),0.01);
		//southeast
		sheryl.setLocation(4, 5);
		assertEquals(11.18, e.getDistance(bob, sheryl),0.01);
	}
	
	/**
	 * tests that cells with negative indicies throws an exception
	 * @throws EnvironmentException
	 */
	@Test(expected = EnvironmentException.class)
	public void testNonNegDistance() throws EnvironmentException
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS);
		//check that distance will NOT accept negative coordinates
		//while the math should work fine, cell numbers are always nonnegative
		assertEquals(1.0, e.getDistance(-1, -1, -2, -2),0.01); 
	}
	
	/**
	 * makes sure LifeForm locations are updated on add and remove
	 */
	@Test
	public void testAddRemoveLocation()
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS);
		MockLifeForm bob = new MockLifeForm("Bob", 10, 2);
		
		e.addLifeForm(bob, 6, 7); 
		assertEquals(bob.getRow(), 6); 
		assertEquals(bob.getCol(), 7); 
		
		e.removeLifeForm(6, 7); 
		assertEquals(-1, bob.getRow()); 
		assertEquals(-1, bob.getCol()); 
	}
	/**
	 * tests that cells with positive indicies beyond the bounds of the Environment throws an exception
	 * @throws EnvironmentException
	 */
	@Test(expected = EnvironmentException.class)
	public void testOutOfBoundsDistance() throws EnvironmentException
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS);
		//check that distance will NOT accept coordinate beyond the bounds of the environment
		assertEquals(1.0, e.getDistance(1, 1, 200, 200),0.01); 
	}
	
	/************ LAB 5 (Singleton) REFACTORED THESE TESTS from Lab 2 ******************/
	/**
	 * checks that we can add a LifeForm to a mulitdimensional Environment
	 */
	@Test
	public void testAddLifeForm()
	{
		
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS);
		MockLifeForm entity; 
        entity = new MockLifeForm("Bob", 40); 
        
        //border case 1,2
        assertTrue(e.addLifeForm(entity, 1, 2));
        MockLifeForm checkLF = (MockLifeForm) e.getLifeForm(1, 2); 
        assertEquals("Bob", checkLF.getName()); 
        assertEquals(40, checkLF.getCurrentLifePoints()); 
        
        //border case 0,2
        assertTrue(e.addLifeForm(entity, 0, 2));
        checkLF = null; 
        checkLF = (MockLifeForm) e.getLifeForm(0, 2); 
        assertEquals("Bob", checkLF.getName()); 
        assertEquals(40, checkLF.getCurrentLifePoints());
        
        //border case 0,0
        assertTrue(e.addLifeForm(entity, 0, 0));
        checkLF = null; 
        checkLF = (MockLifeForm) e.getLifeForm(0, 0); 
        assertEquals("Bob", checkLF.getName()); 
        assertEquals(40, checkLF.getCurrentLifePoints());
        
        //border case 1, 0
        assertTrue(e.addLifeForm(entity, 1, 0));
        checkLF = null; 
        checkLF = (MockLifeForm) e.getLifeForm(1, 0); 
        assertEquals("Bob", checkLF.getName()); 
        assertEquals(40, checkLF.getCurrentLifePoints());
	}

	/**
	 * checks that we can create an Environment without any LifeForms
	 */
	@Test
	public void testInitialization() {
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS); 
		assertNull(e.getLifeForm(10, 20)); 
	}


	
	/**
	 * adds a LifeFormto the Environment, then removes it. 
	 * checks that removing from an empty cell is OK
	 */
	@Test
	public void testRemoveLifeForm()
	{
		//Environment e = Environment.getEnvironment(NUMROWS, NUMCOLS); 
		MockLifeForm entity; 
        entity = new MockLifeForm("Bob", 40); 
        
        //add a LF to 1,2
        assertTrue(e.addLifeForm(entity, 1, 2));
        MockLifeForm checkLF = (MockLifeForm) e.getLifeForm(1, 2); 
        assertEquals("Bob", checkLF.getName()); 
        assertEquals(40, checkLF.getCurrentLifePoints()); 
        
        //remove the LF
        e.removeLifeForm(1,2); 
        assertNull(e.getLifeForm(1, 2)); 
        
        //try removing from the same cell again
        e.removeLifeForm(1,2); 
        assertNull(e.getLifeForm(1, 2));
        
        //try removing from a cell that never had a LF in it
        assertNull(e.getLifeForm(0, 0)); 
        e.removeLifeForm(0, 0); 
        assertNull(e.getLifeForm(0, 0)); 
       
		
	}
}

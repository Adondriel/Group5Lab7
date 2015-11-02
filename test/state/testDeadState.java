package state;

import static org.junit.Assert.*;
import lifeform.Human;
import lifeform.LifeForm;

import org.junit.Test;

import environment.Environment;

/**
 * @author Benjamin Uleau
 *
 */
public class testDeadState
{

	/**
	 * Test dead state
	 */
	@Test
	public void test()
	{
		Environment e=Environment.getEnvironment(2, 2);
		LifeForm h1=new Human("H1", 20, 0);
		LifeForm h2=new Human("H2", 20, 0);
		LifeForm h3=new Human("H3", 20, 0);
		e.addLifeForm(h1, 0, 0);
		e.addLifeForm(h2, 0, 1);
		e.addLifeForm(h3, 1, 0);
		AIContext ai=new AIContext(h1);
		assertEquals(h1, ai.getLifeForm());
		h1.takeHit(50);
		assertEquals(0, h1.getCurrentLifePoints());
		System.out.println(ai.getCurrentState());
	}

}

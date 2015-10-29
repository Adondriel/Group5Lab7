
package lifeform;

/**
 * This is a mock class for the abstract LifeForm class
 * @author Dr. Alice Armstrong
 *
 */
public class MockLifeForm extends LifeForm {
	
	public MockLifeForm(String name, int points)
	{
		super(name, points); 
		maxSpeed = 3; 
	}
	
	public MockLifeForm(String name, int points, int attack)
    {
        super(name, points, attack);
        maxSpeed = 3; 
    }

}

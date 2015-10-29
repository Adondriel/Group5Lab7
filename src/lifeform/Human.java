/**
 * 
 */
package lifeform;

/**
 * Human is a subclass of LifeForm
 * 
 * Humans have armor.
 * Right now, that doesn't really effect behavior in any way
 * 
 * @author Dr. Alice Armstrong
 *
 */
public class Human extends LifeForm {
	
	private int armorPoints; //armor
	
	/**
	 * Humans have a default attack strength of 5
	 * Humans have a max speed of 3
	 * @author Dr. Alice Armstrong
	 * @param name Name of the life form
	 * @param lifepoints life points
	 * @param armor armor points cannot be less than zero. If a number less than 0 is entered, the armor points are set to exactly 0. 
	 */
	public Human(String name, int lifepoints, int armor)
	{
		//call the superclass constructor with 5 attack strength
		super(name, lifepoints, 5); 
		
		//add armor point value
		if (armor >=0)
		{
			armorPoints = armor; 
		}
		else
		{
			armor = 0; 
		}
		
		maxSpeed = 3; 
		
	}
	
	/**
	 * This overrides LifeForm.takeHit() to account for armor
	 */
	public void takeHit(int damage)
	{
		int totalDamage = damage - armorPoints; 
		
		//if there was any damage done, apply it to currentLifePoints
		if (totalDamage > 0)
		{
			currentLifePoints -= totalDamage; 
			
			//if the damage was very great, make sure the human is only dead, not negative
			if (currentLifePoints <0)
			{
				currentLifePoints = 0; 
			}
		}
	}
	
	/**
	 * Getter
	 * @author Dr. Alice Armstrong
	 * @return current armor points
	 */
	public int getArmorPoints()
	{
		return armorPoints; 
	}
	
	/**
	 * sets the Human's armor points. 
	 * armor points cannot be less than 0. If a value less than 0 is given, the armor points remain unchanged. 
	 * @param points
	 */
	public void setArmorPoints(int points)
	{
		if (points >= 0)
		{
			armorPoints = points; 
		}
	}

}

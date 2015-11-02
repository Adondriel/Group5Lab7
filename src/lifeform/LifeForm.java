package lifeform;

import exceptions.WeaponException;
import weapon.Weapon;

/** 
 * Keeps track of the information associated with a simple life form.
 * Also provides the functionality related to the life form. 
 * 
 * This is now an abstract class. 
 * 
 * @author Dr. Dudley Girard -- first author
 * @author Dr. Alice Armstrong -- revisions
 * @author Benjamin Uleau
 */ 
public abstract class LifeForm 
{ 
    private String myName;
    protected int currentLifePoints; 
    protected int attackStrength; 
    protected Weapon weapon; 
    protected int row; //Cell in the Environment, unplaced LifeForms are at (-1, -1)
    protected int col; 
    protected String direction; 
    protected int maxSpeed;
	protected int maxLifePoints; 
	protected int attackRange; 
 
 /** 
  * Create an instance with default attack strength of 1
  *  
  * @param name the name of the life form
  * @param points the current starting life points of the life form. 
  */ 
    public LifeForm(String name, int points) 
    { 
    	//refactored to make sure there is only one meaningful constructor
    	this(name, points, 1); 
    } 
 
    /**
     * Create an instance
     * @param name
     * @param points the life points 
     * @param attack the attack strength
     */
    public LifeForm(String name, int points, int attack) 
    { 
    	myName = name; 
    	currentLifePoints = points; 
    	maxLifePoints = points; 
    	attackStrength = attack;
    	weapon = null; 
    	row = -1; 
    	col = -1; 
    	direction = "North"; 
    	maxSpeed = 0; 
    	attackRange = 5; 
    } 
    
    
    /**
     * The LifeForm's lifepoints are reduced by the damage
     * A LifeForm cannot have life points less than zero. If the damage is greater than the remaining lifepoint, 
     * the LifeForm's lifepoints will be zero. 
     * @param damage
     */
    public void takeHit(int damage)
    {
    	if ((currentLifePoints - damage) >=0)
    	{
    		currentLifePoints -= damage; 
    	}
    	else
    	{
    		currentLifePoints = 0; 
    	}
    }
    
    /**
     * attack another Lifeform with full attack strength
     * if the attacker has a weapon with ammo, the weapon will be used
     * if the attacker has no weapon or the weapon is out of ammo, the ranged touch attack (hand to hand attack strength) will be used
     * ranged touch attack will only work within a distance <= 5
     * weapon attacks will work only within the range of the weapon used
     * @param opponent
     * @param distance the distance between two LifeForms
     */
    public void attack(LifeForm opponent, double distance)
    {
    	if (currentLifePoints >  0)
    	{
    		//if there is no weapon AND the opponent is within 5 feet
    		//use the ranged touch attack
    		if (!hasWeapon() && distance <= 5)
    		{
    			
    			//it's important that the oppenent take the hit because
	    		//humans will have armour that reduces the effect of the attack
	        	opponent.takeHit(attackStrength);
    		}
    		
    		//if there is a weapon, but it is out of ammo
    		//AND the opponent is within 5 feet
    		//use the ranged touch attack
    		else if (hasWeapon() && weapon.getCurrentAmmo() == 0 && distance <= 5)
    		{
    			//it's important that the oppenent take the hit because
	    		//humans will have armour that reduces the effect of the attack
	        	opponent.takeHit(attackStrength);
    		}
    		
    		//if there is a weapon
    		//use weapon's attack 
    		//there may not be any damage if the opponent is out of range
    		//there may not be any damage is the weapon is out of ammo
    		else if (hasWeapon())
    		{
    			try
    			{
    				opponent.takeHit(weapon.fire(distance));
    			}
    			catch(WeaponException e)
    			{
    				//the distance is negative, which it should not ever be
    				//just print the error message, but do not execute the attack
    				System.out.println(e.getMessage()); 
    			}
    		}
    	}   	
    }
    
    /**
     * If this LifeForm does not already hold a weapon, it will take possession of this one
     * @param weapon
     * @return true if the weapon was acquired
     */
    public boolean pickUpWeapon(Weapon weapon)
    {
    	if(!hasWeapon())
    	{
    		this.weapon = weapon; 
    		return true; 
    	}
    	else
    	{
    		return false; 
    	}
    }
    
    /**
     * drops this LifeForm's Weapon and returns it to the caller
     * @return the Weapon this lifeform was holding
     */
    public Weapon dropWeapon()
    {
    	if (hasWeapon())
    	{
    		Weapon temp = weapon; 
    		//the lifeform no holds no weapon
    		weapon = null; 
    		//return the old weapon
    		return temp; 
    	}
    	
    	//otherwise there is nothing to return
    	return null; 
    }
    
    /**
     * Remove the weapon in case you don't want to drop it
     */
    public void removeWeapon(){
    	if(hasWeapon()){
    		weapon=null;
    	}
    }
    /**
	 * @return
	 */
	public boolean hasWeapon() {
		if (weapon != null)
		{
			return true; 
		}
		return false;
	}

	/** 
     * @return the name of the life form. 
     */ 
       public String getName() 
       { 
           return myName; 
       } 
    
    /** 
     * @return the amount of current life points the life form has. 
     */ 
       public int getCurrentLifePoints() 
       { 
           return currentLifePoints; 
       } 
       
       /**
        * 
        * @return this LifeForm's attack strength
        */
       public int getAttackStrength()
       {
    	   return attackStrength; 
       }
   
   /**
    * set the location of the LifeForm in the Environment
    * LifeForms should be at -1, -1 when they are not in the Environment
    * 
    * @param row anything < -1 will leave the coordinates UNCHANGED
    * @param col anything < -1 will leave the coordinates UNCHANGED
    */
   public void setLocation(int row, int col)
   {
	   if (row >= -1 && col >= -1)
	   {
		   this.row = row; 
		   this.col = col; 
	   }
   }
   
   public int getRow()
   {
	   return row; 
   }
   
   public int getCol()
   {
	   return col; 
   }
   
   /**
    * 
    * @return the direction the LifeForm is currently facing
    */
   public String getDirection()
   {
	   return direction; 
   }
   
   /**
    * 
    * @return the rate of movement
    */
   public int getMaxSpeed()
   {
	   return maxSpeed; 
   }
   
   /**
    * Sets the direction of this LifeForm, if a valid direction is given. The direction is not changed if the input was invalid.
    * @param direction
    * @return true if a valid direction was given (North, South, East, West), false if an invalid direction was entered
    */
   public boolean setDirection(String direction)
   {
	   //if a valid direction was given, change the direction
	   if (direction.equals("North") || direction.equals("South") || 
		   direction.equals("East") || direction.equals("West"))
	   {
		   this.direction = direction; 
		   return true; 
	   }
	   
	   return false; 
   }
   
   /**
    * @return the LifeForm's Weapon
    */
   public Weapon getWeapon()
   {
	    return weapon; 
   }

	/**
	 * 
	 * @return the maximum life points the LifeForm has
	 */
	public int getMaxLifePoints() {
		return maxLifePoints; 
	}

	/**
	 * 
	 * @return the lifeforms attack range. If the lifeform has no weapon, then the touch attack range (5) is return
	 * if the weapon has no ammo, the touch attack range is returned
	 * if the weapon is loaded, but can't shoot again this round, the touch attack range is returned
	 * if the weapon is loaded, then the weapon's attack range is returned
	 */
	public int getAttackRange()
	{
		//no weapon or unloaded weapon
		if (weapon == null || weapon.getCurrentAmmo() == 0 || weapon.getShotsLeft() == 0)
		{
			return attackRange; 
		}
		else
		{
			return weapon.getMaxRange(); 
		}
	}

	/**
	 * set current lifepoints to max lifepoints
	 */
	public void refillLife(){
		currentLifePoints=maxLifePoints;
	}
}

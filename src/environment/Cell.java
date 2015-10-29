package environment;
import weapon.MockWeapon;
import weapon.Weapon;
import lifeform.LifeForm;
/** 
 * A Cell that can hold a LifeForm. 
 * @author Dr. Dudley Girard -- first author
 * @author Dr. Alice Armstrong -- revisions
 */ 
public class Cell 
{ 
  private LifeForm cellLife;  //the LifeForm that exists in this cell
  private int weaponsCount; //how many weapons (not held by LifeForms) are in this Cell
  private Weapon weapon1;   //a cell can hold up to 2 Weapons
  private Weapon weapon2;   //Any Weapons held by LifeForms are not counted

 /** 
  * @return the LifeForm in this Cell. 
  */ 
    public LifeForm getLifeForm() 
    { 
        return cellLife; 
    }  
    
/** 
 * Tries to add the LifeForm to the Cell.  Will not add if a 
 * LifeForm is already present.
 * @return true if the LifeForm was added the Cell, false otherwise. 
 */ 
    public boolean addLifeForm(LifeForm entity) 
    { 
    	//if there is not LifeForm in this cell, put entity here
    	if (cellLife == null)
    	{
    		cellLife = entity; 
    		return true; 
    	}
    	
    	//otherwise, the cell is already full. Do not place entity here
        return false; 
    }  

    /**
     * Removes any LifeForm from this Cell. 
     * @author Dr. Alice Armstrong
     * @return the LifeForm that was removed
     */
    public LifeForm removeLifeForm()
    {
    	LifeForm temp = cellLife; 
    	cellLife = null;
    	return temp; 
    }

	/**
	 * @return
	 */
	public int getWeaponsCount() {
		return weaponsCount;
	}

	/**
	 * @param weapon1
	 */
	public boolean addWeapon(Weapon weapon) {
		//if slot 1 is open, and the cell does not already contain this instance
		//make it weapon 1
		if(weapon1 == null && weapon != weapon2)
		{
			weapon1 = weapon; 
			weaponsCount++; 
			return true; 
		}
		
		//if slot 2 is open, and the cell does not already contain this instance
		//make it weapon 2
		else if(weapon2 == null && weapon1 != weapon)
		{
			weapon2 = weapon; 
			weaponsCount++; 
			return true; 
		}
		
		return false; 
	}

	/**
	 * @param weapon3
	 * @return
	 */
	public Weapon removeWeapon(Weapon weapon) {
		
		//remove nothing from this cell
		if (weapon == null)
		{
			//used in the clearBoard method in the Environment
			return null; 
		}
		//try to find the specific instance
		else if(weapon == weapon1)
		{
			Weapon temp = weapon1; 
			weaponsCount--; 
			weapon1 = null; 
			return temp; 
		}
		else if (weapon == weapon2)
		{
			Weapon temp = weapon2; 
			weaponsCount--; 
			weapon2 = null; 
			return temp; 
		}
		//this instance is not in this Cell
		else
		{
			return null; 
		}
		
	}

	/**
	 * @return the weapon in the first slot
	 */
	public Weapon getWeapon1() {
		return weapon1;
	}
	
	/**
	 * @return the weapon in the first slot
	 */
	public Weapon getWeapon2() {
		return weapon2;
	}
}


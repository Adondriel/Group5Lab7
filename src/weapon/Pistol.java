/**
 * 
 */
package weapon;

import exceptions.WeaponException;


/**
 * @author Dr. Alice Armstrong
 *
 */
public class Pistol extends GenericWeapon {
	
	public Pistol()
	{
		baseDamage = 10; 
		maxRange = 50; 
		rateOfFire = 2; 
		maxAmmo = 10; 
		currentAmmo = maxAmmo; 
		//start with full firing capacity when ever instantiated
		shotsLeft = rateOfFire; 
	}
	/** 
	 * @see weapon.Weapon#fire(int)
	 */
	@Override
	public int fire(double distance) throws WeaponException {
		
		if (distance < 0)
		{
			throw new WeaponException("Distance to target must be at least 0."); 
		}
		double damage = 0.0; 
		
		//if the target is in range 
		//AND there is still ammo in the clip
		//AND this weapon can still fire this round
		//calculate damage
		if (distance <= maxRange && currentAmmo > 0 && shotsLeft >0)
		{
			//calculate the damage
			damage = baseDamage*((maxRange - (int)distance +10.0)/maxRange);
		}
		 
		
		
		//if there was ammo in the clip
		//and this weapon was able to fire this round
		//reduce the ammo and the shots left
		if (currentAmmo >0 && shotsLeft >0)
		{
			//reduce the ammo by one, even is target was out of range
			currentAmmo--; 
			
			//reduce the number of shots left for this round
			shotsLeft--; 	
		}
		
		
		return (int)damage;
	}


	/** 
	 * @see weapon.GenericWeapon#toString()
	 */
	@Override
	public String toString() {
		
		return "Pistol";
	}

}

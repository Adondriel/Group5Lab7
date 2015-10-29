/**
 * 
 */
package weapon;

import exceptions.WeaponException;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class MockWeapon extends GenericWeapon {

	/**
	 * MockWeapons have the following stats
	 * 
	 * baseDamage = 5; 
	 * maxRange = 40; 
	 * rateOfFire = 3; 
	 * maxAmmo = 20; 
	 * currentAmmo = 20; 
	 */
	public MockWeapon() {
		baseDamage = 5; 
		maxRange = 40; 
		rateOfFire = 3; 
		maxAmmo = 20; 
		currentAmmo = 20; 
		shotsLeft = rateOfFire; 
	}



	/**
	 * @see weapon.GenericWeapon#toString()
	 */
	@Override
	public String toString() {
		return "MockWeapon"; 
	}


	/**
	 * a simple damage calculation: if in range & there is ammo, damage = baseDamage
	 * @see weapon.GenericWeapon#fire(int)
	 */
	@Override
	public int fire(double distance) throws WeaponException {
		double damage = 0.0; 
		
		if (distance < 0)
		{
			throw new WeaponException("Distance to target must be at least zero."); 
		}
		
		//if the target is in range AND there is still ammo in the clip, calculate damage
		if (distance <= maxRange && currentAmmo > 0)
		{
			//calculate the damage
			damage = baseDamage; 
		}
		 
		
		//if there was ammo in the clip
		if (currentAmmo >0)
		{
			//reduce the ammo by one, even is target was out of range
			currentAmmo--; 
		}
		
		
		return (int)damage;
	}

}

/**
 * 
 */
package weapon;

import exceptions.WeaponException;

/**
 * @author Dr. Alice Armstrong
 *
 */
public abstract class GenericWeapon implements Weapon {

	protected int baseDamage; //the damage that this weapon does
	protected int maxRange; //the maximum range of this weapon
	protected int rateOfFire; //the number of times a weapon may fire during a single round
	protected int maxAmmo; //the maximum amount of ammo in a clip for this weapon
	protected int currentAmmo; //the current amount of ammo in the clip
	
	protected int shotsLeft; //how many shots are left this round

	/**
	 * fire at a target that is some distance away. 
	 * @throws WeaponException 
	 * @see weapon.Weapon#fire(int)
	 */
	@Override
	abstract public int fire(double distance) throws WeaponException;

	/**
	 * @see weapon.Weapon#toString()
	 */
	abstract public String toString(); 

	/* (non-Javadoc)
	 * @see weapon.Weapon#reload()
	 */
	/**
	 * @see weapon.Weapon#getNumAttachments()
	 */
	@Override
	public int getNumAttachments() {
		return 0;
	}

	/**
	 * @see weapon.Weapon#reload()
	 */
	@Override
	public void reload() {
		currentAmmo = maxAmmo; 
	}

	/**
	 * @see weapon.Weapon#getBaseDamage()
	 */
	@Override
	public int getBaseDamage() {
		return baseDamage;
	}

	/**
	 * @see weapon.Weapon#getMaxRange()
	 */
	@Override
	public int getMaxRange() {
		return maxRange;
	}

	/**
	 * @see weapon.Weapon#getRateOfFire()
	 */
	@Override
	public int getRateOfFire() {
		return rateOfFire;
	}

	/**
	 * @see weapon.Weapon#getMaxAmmo()
	 */
	@Override
	public int getMaxAmmo() {
		return maxAmmo;
	}

	/**
	 * @see weapon.Weapon#getCurrentAmmo()
	 */
	@Override
	public int getCurrentAmmo() {
		return currentAmmo;
	}
	
	@Override
	public int getShotsLeft()
	{
		return shotsLeft; 
	}
	
	@Override 
	public void updateTime(int time)
	{
		shotsLeft = rateOfFire; 
	}
	

}

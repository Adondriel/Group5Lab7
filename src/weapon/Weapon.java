/**
 * 
 */
package weapon;

import exceptions.WeaponException;
import gameplay.TimerObserver;


/**
 * @author Dr. Alice Armstrong
 *
 */
public interface Weapon extends TimerObserver {

	// key methods
	/**
	 * Fire the weapon once, this reduces the current ammo by one bullet
	 * @param distance the distance to the target, for now, if the target is in range, it will be hit
	 * @return the damage the weapon deals to the target, the target's armor may mitigate this damage. The damage will be zero if 
	 * a) the target is out of range (the ammo is still used in this case) OR
	 * b) the weapon was out of ammo (ammo cannot be negative)
	 */
	public int fire(double distance) throws WeaponException; 
	
	/**
	 * 
	 * @return the number of attachments on this Weapon
	 */
	public int getNumAttachments(); 
	
	/**
	 * reload the clip to full capacity. 
	 * For now, we assume a limitless supply of clips. 
	 * This may be refactored in the future to take ammo reserves into account. 
	 */
	public void reload(); 
	
	/**
	 * Displays the Weapon and any attachments. 
	 * @return
	 */
	public String toString();  //print full description for the weapon & attachments

	// basic getters
	/**
	 * 
	 * @return the base damage of this weapon
	 */
	public int getBaseDamage();
	
	/**
	 * 
	 * @return the maximum range of the weapon
	 */
	public int getMaxRange();
	
	/**
	 * 
	 * @return the number of times this weapon may fire during a round
	 */
	public int getRateOfFire(); 
	
	/**
	 * 
	 * @return the clip size of this weapon
	 */
	public int getMaxAmmo();
	
	/**
	 * 
	 * @return the current number of bullets in the clip
	 */
	public int getCurrentAmmo();

	/**
	 * 
	 * @return the number of shots left in this round
	 */
	public int getShotsLeft(); 
	
}

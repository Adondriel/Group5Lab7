/**
 * 
 */
package weapon;

import exceptions.AttachmentException;
import exceptions.WeaponException;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class PowerBooster extends Attachment {

	/**
	 * Power Boosters increase the base Weapon's damage based on how much ammo is left
	 * the more ammo left, the more powerful the boost
	 * @param baseWeapon
	 * @throws AttachmentException
	 */
	public PowerBooster(Weapon baseWeapon) throws AttachmentException
	{
		this.base = baseWeapon; 
		
		if(this.base.getNumAttachments() == 2)
		{
			throw new AttachmentException("Only 2 attachment allowed per base Weapon."); 
		}
	}
	
	public String toString()
	{
		return base.toString()+" +PowerBooster"; 
	}
	
	/**
	 * @see weapon.Attachment#fire(int)
	 */
	@Override
	public int fire(double distance) throws WeaponException {
		double boosterDamage = base.fire(distance)*(1+((double)base.getCurrentAmmo()+1)/(double)base.getMaxAmmo()); 
		return (int)boosterDamage;
	}

}

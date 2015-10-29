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
public class Stabilizer extends Attachment {

	public Stabilizer(Weapon baseWeapon) throws AttachmentException
	{
		this.base = baseWeapon; 
		if(this.base.getNumAttachments() == 2)
		{
			throw new AttachmentException("Only 2 attachment allowed per base Weapon."); 
		}
	}
	
	/**
	 * A Stabilizer auto reloads if ammo is at 0 after firing
	 * A Stabilizer also increases the Weapon's damage by 25%
	 * @see weapon.Attachment#fire(int)
	 */
	@Override
	public int fire(double distance) throws WeaponException {
		double stabilizerDamage = base.fire(distance)*1.25; 
		
		if (base.getCurrentAmmo() == 0)
		{
			base.reload();
		}
		return (int)stabilizerDamage;
	}

	/**
	 * @see weapon.GenericWeapon#toString()
	 */
	@Override
	public String toString() {
		return base.toString()+" +Stabilizer";
	}
}

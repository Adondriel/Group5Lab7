/**
 * 
 */
package weapon;

import exceptions.AttachmentException;
import exceptions.WeaponException;

/**
 * the Scope attachment
 * @author Dr. Alice Armstrong
 *
 */
public class Scope extends Attachment {

	/**
	 * creates an instance of a Scope wrapped around a base GenericWeapon
	 * Scopes add 10 to the maxRange of the base Weapon
	 * @param base
	 * @throws AttachmentException if there are already 2 attachments on the base Weapon
	 */
	public Scope(Weapon base) throws AttachmentException
	{
		this.base = base; 
		
		if(this.base.getNumAttachments() == 2)
		{
			throw new AttachmentException("Only 2 attachment allowed per base Weapon."); 
		}
	}
	
	/**
	 * Scopes increase the maxRange of the base Weapon, which may or may not be used to calculate
	 * its damage
	 * Scopes also increase a weapon's power
	 * ScopeDamage = BaseDamage*((maxRange - distance)/maxRange)
	 * @see weapon.Attachment#fire(int)
	 */
	@Override
	public int fire(double distance) throws WeaponException {
		double scopeDamage; 
		//if distance is with the base range
		if (distance <= base.getMaxRange())
		{
			scopeDamage = base.fire(distance)*(1+((double)getMaxRange() - distance)/(double)getMaxRange()); 
		}
		//if the distance is within the extended range
		else if (distance <= this.getMaxRange())
		{
			//calculate the base weapon's damage at the base maxRange and add 5 to that damage
			scopeDamage = base.fire(base.getMaxRange())+5; 
		}
		//otherwise it's just too far away, call base.fire() should do 0 damage, but update ammo and shots fired
		else
		{
			scopeDamage = base.fire(distance);
		}
		return (int)scopeDamage;
	}

	/**
	 * @see weapon.GenericWeapon#toString()
	 */
	@Override
	public String toString() {
		return base.toString()+" +Scope";
	}
	
	@Override
	public int getMaxRange()
	{
		return base.getMaxRange() +10; 
	}
}

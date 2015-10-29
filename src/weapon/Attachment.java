/**
 * 
 */
package weapon;

import exceptions.WeaponException;

/**
 * @author Dr. Alice Armstrong
 *
 */
public abstract class Attachment implements Weapon {
	protected Weapon base; //by making this protected, attachments can modify base attributes, if needed
								 //without having to supply public setters, which could be dangerous

	/**
	 * @see gameplay.TimerObserver#updateTime(int)
	 */
	@Override
	public void updateTime(int time) {
		base.updateTime(time);
	}

	/**
	 * Most attachments will change the damage the weapon does when it fires
	 * @see weapon.Weapon#fire(int)
	 */
	@Override
	abstract public int fire(double distance) throws WeaponException;

	/**
	 * @see weapon.Weapon#getNumAttachments()
	 */
	@Override
	public int getNumAttachments() {
		return base.getNumAttachments()+1;
	}

	/**
	 * @see weapon.Weapon#reload()
	 */
	@Override
	public void reload() {
		base.reload();
	}

	/**
	 * returns the damage of the unadorned base Weapon
	 * @see weapon.Weapon#getBaseDamage()
	 */
	@Override
	public int getBaseDamage() {
		return base.getBaseDamage();
	}

	/**
	 * returns the maximum range of the unadorned base Weapon
	 * @see weapon.Weapon#getMaxRange()
	 */
	@Override
	public int getMaxRange() {
		return base.getMaxRange();
	}

	/**
	 * returns the rate of fire of the unadorned base Weapon
	 * @see weapon.Weapon#getRateOfFire()
	 */
	@Override
	public int getRateOfFire() {
		return base.getRateOfFire();
	}

	/**
	 * @see weapon.Weapon#getMaxAmmo()
	 */
	@Override
	public int getMaxAmmo() {
		return base.getMaxAmmo();
	}

	/**
	 * returns the current ammo of the unadorned base Weapon
	 * @see weapon.Weapon#getCurrentAmmo()
	 */
	@Override
	public int getCurrentAmmo() {
		return base.getCurrentAmmo();
	}

	/**
	 * @see weapon.Weapon#getShotsLeft()
	 */
	@Override
	public int getShotsLeft() {
		return base.getShotsLeft();
	}

}

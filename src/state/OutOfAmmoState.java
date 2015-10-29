package state;

/**
 * @author Benjamin Uleau
 *
 */
public class OutOfAmmoState extends ActionState
{
	/**
	 * Checks whether or not the lifeform is out of ammo. If it is, reload.
	 */
	public void executeAction(){
		l.getWeapon().reload();
	}
}

package state;

/**
 * @author Benjamin Uleau
 *
 */
public class OutOfAmmoState extends ActionState
{
	AIContext ai;
	/**
	 * Passes in the AI to be able to change the state.
	 * @param ai
	 */
	public OutOfAmmoState(AIContext ai)
	{
		this.ai = ai;
	}
	/**
	 * Checks whether or not the lifeform is out of ammo. If it is, reload.
	 */
	public void executeAction(){
		l.getWeapon().reload();
	}
}

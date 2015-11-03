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
	@Override
	public void executeAction(){
		if(ai.l.getCurrentLifePoints()==0){	//If the lifeform doesn't have any health, set its state do dead
			ai.setState(ai.getDeadState());
		}
		if(ai.l.getCurrentLifePoints()!=0){ //If the lifeform is alive, reload its weapon and change its state to HasWeaponState
			ai.l.getWeapon().reload();
			ai.setState(ai.getHasWeaponState());
		}
	}
}

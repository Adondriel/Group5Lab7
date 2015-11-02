package state;

import lifeform.LifeForm;
import gameplay.TimerObserver;

/**
 * @author Adam Pine, Benjamin Uleau
 * Used to handle the many possible states that the AI will need to consider.
 */
public class AIContext implements TimerObserver{
	private ActionState deadState = new DeadState(this);
	private ActionState hasWeapon = new HasWeaponState(this);
	private ActionState noWeapon = new NoWeaponState(this);
	private ActionState outOfAmmo = new OutOfAmmoState(this);
	private int myTime = 0;
	private ActionState currentState = noWeapon;
	LifeForm l;
	
	/**
	 * Constructor of the AIContext.
	 * Takes in the LifeForm for which it will be the AI for.
	 * @param l
	 */
	public AIContext(LifeForm l){
		this.l=l;
	}
	
	/**
	 * Calls the executeAction() method of the Context at the beginning of every turn.
	 */
	@Override
	public void updateTime(int time) {
		myTime = time;
		executeAction();
	}

	/**
	 * Get this context's OutOfAmmoState.
	 * @return ActionState: this context's OutOfAmmoState.
	 */
	public ActionState getOutOfAmmoState() {
		return outOfAmmo;
	}
	/**
	 * Get this context's NoWeaponState
	 * @return ActionState: this context's NoWeaponState.
	 */
	public ActionState getNoWeaponState() {
		return noWeapon;
	}
	/**
	 * Get this context's HasWeaponState.
	 * @return ActionState: this context's HasWeaponState.
	 */
	public ActionState getHasWeaponState() {
		return hasWeapon;
	}
	/**
	 * Get this context's DeadState.
	 * @return ActionState: this context's DeadState.
	 */
	public ActionState getDeadState() {
		return deadState;
	}	
	/**
	 * Used to set the currentState.
	 * Pass in an ActionState returned from either of this context's get methods.
	 * @param an ActionState returned from either of this context's get methods.
	 */
	public void setState(ActionState state) {
		currentState = state;		
	}
	
	/**
	 * Get the currentState of the Context.
	 * @return ActionState: The current State of this context
	 */
	public ActionState getCurrentState(){
		return currentState;
	}
	
	/**
	 * Executes the currentState's executeAction() method.
	 */
	public void executeAction(){
		currentState.executeAction();
	}
}
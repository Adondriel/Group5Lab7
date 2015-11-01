package state;

import lifeform.LifeForm;
import gameplay.TimerObserver;

/**
 * @author Adam Pine, Benjamin Uleau
 *
 */
public class AIContext implements TimerObserver{
	private ActionState deadState = new DeadState(this);
	private ActionState hasWeapon = new HasWeaponState(this);
	private ActionState noWeapon = new NoWeaponState(this);
	private ActionState outOfAmmo = new OutOfAmmoState(this);
	private int myTime = 0;
	private ActionState currentState = noWeapon;
	LifeForm l;
	
	public AIContext(LifeForm l){
		this.l=l;
	}
	@Override
	public void updateTime(int time) {
		myTime = time;
		currentState.executeAction();
	}

	public ActionState getOutOfAmmoState() {
		return outOfAmmo;
	}
	public ActionState getNoWeaponState() {
		return noWeapon;
	}
	public ActionState getHasWeaponState() {
		return hasWeapon;
	}
	public ActionState getDeadState() {
		return deadState;
	}
	
	public void setState(ActionState state) {
		currentState = state;		
	}
	
	public ActionState getCurrentState(){
		return currentState;
	}
}
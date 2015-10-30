package state;

/**
 * Provides functionality to the AI when in NoWeaponState.
 * @author Jason LoBianco
 */
public class NoWeaponState extends ActionState
{
	AIContext ai;
	
	/**
	 * Passes in the AI to be able to change the state.
	 * @param ai
	 */
	public NoWeaponState(AIContext ai)
	{
		this.ai = ai;
	}
	
	/**
	 * When called, checks to see if the LifeForm is alive, if it is and 
	 * there is a weapon for the LifeForm to pick up, the LifeForm picks up the weapon. 
	 * If there is no weapon the LifeForm will turn a random direction and move.
	 * If dead moves to the DeadState.
	 */
	public void executeAction()
	{
		if (alive() == true)
		{
			if (weaponAtFeet() == true)
			{
				aquireWeapon();
				ai.setState(ai.getHasWeaponState());
			}
			else
			{
				search();
			}
		}
		else
		{
			ai.setState(ai.getDeadState());
		}
	}
}
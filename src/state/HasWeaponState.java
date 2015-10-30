package state;

/**
 * Provides functionality to the AI when in HasWeaponState.
 * @author Jason LoBianco
 */
public class HasWeaponState extends ActionState
{
	AIContext ai;
	
	/**
	 * Passes in the AI to be able to change the state.
	 * @param ai
	 */
	public HasWeaponState(AIContext ai)
	{
		this.ai = ai;
	}
	
	/**
	 * When called, checks to see if the LifeForm is alive, if it is and 
	 * there is a target for the LifeForm to attack, the LifeForm attacks 
	 * the target.  If the LifeFrom's weapon is out of ammo, the LifeForm moves 
	 * to OutOFAmmoState. If no target the LifeForm will turn a random direction 
	 * and move.  If dead moves to the DeadState.
	 */
	public void executeAction()
	{
		if (alive() == true)
		{
			if (target() == true)
			{
				if (checkAmmo() == false)
				{
					ai.setState(ai.getOutOfAmmoState());
				}
				else
				{
					attackTarget();
				}
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
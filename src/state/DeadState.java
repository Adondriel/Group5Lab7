package state;

/**
 * @author Benjamin Uleau
 *
 */
public class DeadState extends ActionState
{
	AIContext ai;
	/**
	 * Passes in the AI to be able to change the state.
	 * @param ai
	 */
	public DeadState(AIContext ai)
	{
		this.ai = ai;
	}
	/**
	 * Checks whether or not the lifeform is dead. If it is, it loses a turn then respawns.
	 */
	public void executeAction(){
		respawn();
	}
}

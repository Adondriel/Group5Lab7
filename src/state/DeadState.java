package state;

/**
 * @author Benjamin Uleau
 *
 */
public class DeadState extends ActionState
{
	/**
	 * Checks whether or not the lifeform is dead. If it is, it loses a turn then respawns.
	 */
	public void executeAction(){
		respawn();
	}
}

/**
 * 
 */
package recovery;

/**
 * @author Dr. Alice Armstrong
 *
 */
public interface RecoveryBehavior {
	/**
	 * 
	 * @param currentLife the lifeform's current life points
	 * @param maxLife the maximum amount of life point the life form can have
	 * @return the amount of life points to be recovered
	 */
	public int calculateRecovery(int currentLife, int maxLife);
}

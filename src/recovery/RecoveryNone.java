/**
 * 
 */
package recovery;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class RecoveryNone implements RecoveryBehavior {

	/**
	 * @see RecoveryBehavior.calculateRecovery
	 * @return the current life points (no life points are recovered)
	 */
	public int calculateRecovery(int currentLife, int maxLife) {
		// TODO Auto-generated method stub
		return currentLife;
	}

}

/**
 * 
 */
package recovery;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class RecoveryFractional implements RecoveryBehavior {

	private double recoveryFraction; //how much of the currentLife is recovered
	
	public RecoveryFractional(double recoveryFraction)
	{
		this.recoveryFraction = recoveryFraction; 
	}
	
	/** 
	 * @see recovery.RecoveryBehavior#calculateRecovery(int, int)
	 * recover the fractional amount of currentLife without exceeding maxLife
	 * fractional amount is always rounded up, for example, 10% of 9 points is 0.9. Amount to recover is 1 life point. 
	 */
	public int calculateRecovery(int currentLife, int maxLife) {
		int recoverAmt = (int) Math.ceil(recoveryFraction*currentLife) + currentLife; 
		
		//can't recover to more than maxLife
		if (recoverAmt > maxLife)
		{
			return maxLife; 
		}
			
		return recoverAmt;
	}

}

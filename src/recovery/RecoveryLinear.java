/**
 * 
 */
package recovery;

/**
 * @author Dr. Alice Armstrong
 * RecoveryLinear objects recover a fixed number of life points each time
 * Life points cannot exceed the maximum number of life points
 */
public class RecoveryLinear implements RecoveryBehavior{

	private int recoveryAmount; //the amount recovered for this behavior
	
	public RecoveryLinear(int recoveryAmount)
	{
		this.recoveryAmount = recoveryAmount; 
	}
	
	
	/** 
	 * @see recovery.RecoveryBehavior#calculateRecovery(int, int)
	 */
	public int calculateRecovery(int currentLife, int maxLife) {
		int temp = currentLife + recoveryAmount; 
		
		//can't recover from death
		if (currentLife <= 0)
		{
			return 0;
		}
		
		//recover recoveryAmount
		else if (temp < maxLife)
		{
			return temp; 
		}
		
		//otherwise recover to max
		else
		{
			return maxLife; 
		}
	}

}

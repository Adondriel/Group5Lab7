/**
 * 
 */
package recovery;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class TestRecovery 
{
	
	 /************ LAB 2 (Strategy) TESTS BELOW THIS POINT ******************/

	/**
	 * tests the RecoveryNone behavior
	 */
	@Test
	public void testRecoveryNone() {
		//max stays at max
		RecoveryNone rN = new RecoveryNone(); 
		assertEquals(12, rN.calculateRecovery(12, 12)); 
		
		//hurt does not recover
		RecoveryNone rN2 = new RecoveryNone(); 
		assertEquals(10, rN2.calculateRecovery(10, 12));
	}

	/**
	 * tests RecoveryLinear 
	 * tests Recovery Fractional
	 */
	@Test 
	public void testNoRecoveryWhenNotHurt()
	{
	    RecoveryLinear rl= new RecoveryLinear(3);
	    RecoveryFractional rf = new RecoveryFractional(.2); 
	    
	    int maxLifePts = 30;
	    
	    //recovery linear
	    int result = rl.calculateRecovery(maxLifePts, maxLifePts);
	    assertEquals(maxLifePts,result);
	    
	    //recovery fractional
	    result = rf.calculateRecovery(maxLifePts, maxLifePts);
	    assertEquals(maxLifePts, maxLifePts); 
	}
	
	/**
	 * tests recoveryLinear
	 * tests recoveryFractional
	 */
	@Test
	public void testRecoverFullAmount()
	{
		RecoveryLinear rl= new RecoveryLinear(3);
		RecoveryFractional rf = new RecoveryFractional(.2); 
	    int maxLifePts = 30;
	    
	    //damage > recovery amount, full amount recovered
	    int result = rl.calculateRecovery(25, maxLifePts);
	    assertEquals(28,result);
	    
	    //20% of 20 is 4, 24 < maxLife, so recover full amount
	    result = rf.calculateRecovery(20, maxLifePts); 
	    assertEquals(24, result); 
	    
	    //20% of 21 is 4.2, recover 5 points, 26 < 30 so recover full amount
	    result = rf.calculateRecovery(21, maxLifePts); 
	    assertEquals(26, result); 
	}

	/**
	 * tests recoveryLinear
	 * tests recoveryFractional
	 */
	@Test
	public void testRecoveryPartial()
	{
		RecoveryLinear rl= new RecoveryLinear(3);
		RecoveryFractional rf = new RecoveryFractional(.2); 
	    int maxLifePts = 30;
	    
	    //damage < recovery amount, recover until maxPoints is reached
	    int result = rl.calculateRecovery(28, maxLifePts);
	    assertEquals(maxLifePts,result);
	    
	    //20% of 28 is 5.6, recover 6 points, 34 > 30 so recover to 30
	    result = rf.calculateRecovery(28, maxLifePts); 
	    assertEquals(maxLifePts, result); 
	}
	
	/**
	 * tests recoveryLinear
	 * tests recoveryFractional
	 */
	@Test
	public void testNoReturnFromDead()
	{
		RecoveryLinear rl= new RecoveryLinear(3);
		RecoveryFractional rf = new RecoveryFractional(.2); 
	    int maxLifePts = 30;
	    
	    //damage < recovery amount, recover until maxPoints is reached
	    int result = rl.calculateRecovery(0, maxLifePts);
	    assertEquals(0,result);
	    
	    result = rf.calculateRecovery(0, maxLifePts);
	    assertEquals(0,result);
	}
	
	
	
}

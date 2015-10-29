/**
 * 
 */
package gameplay;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class TestSimpleTimer {

	/**
	 * test initial set up where the timer has no observers
	 */
	@Test
	public void testInitialization() {
		SimpleTimer s = new SimpleTimer(); 
		assertEquals(s.getRound(), 0); 
		assertEquals(s.getNumObservers(), 0); 
	}

	/**
	 * add one observer and make sure it's observing the timer
	 */
	@Test
	public void testAddOneAndUpdate()
	{
		MockSimpleTimerObserver mock1 = new MockSimpleTimerObserver(); 
		SimpleTimer s = new SimpleTimer(); 
		s.addTimeObserver(mock1); 
		assertEquals(s.getNumObservers(), 1); 
		
		//update the time
		s.timeChanged();
		assertEquals(s.getRound(), 1); 
		
		//check the mock1 is also on round 1
		//we don't need to add a getter because these classes are in the same file. 
		assertEquals(mock1.myTime, 1); 
		
		//update time again
		s.timeChanged();
		assertEquals(mock1.myTime, 2);
	}
	
	@Test
	public void testAddThreeAndUpdate()
	{

		MockSimpleTimerObserver mock1 = new MockSimpleTimerObserver(); 
		MockSimpleTimerObserver mock2 = new MockSimpleTimerObserver();
		MockSimpleTimerObserver mock3 = new MockSimpleTimerObserver();
		
		SimpleTimer s = new SimpleTimer(); 
		s.addTimeObserver(mock1); 
		s.addTimeObserver(mock2); 
		s.addTimeObserver(mock3); 
		assertEquals(s.getNumObservers(), 3); 
		
		//update the time
		s.timeChanged();
		
		//check the mock1 is also on round 1
		//we don't need to add a getter because these classes are in the same file. 
		assertEquals(mock1.myTime, 1); 
		assertEquals(mock2.myTime, 1);
		assertEquals(mock3.myTime, 1);
		
		//update time again
		s.timeChanged();
		assertEquals(mock1.myTime, 2);
		assertEquals(mock2.myTime, 2);
		assertEquals(mock3.myTime, 2);
	}
	
	@Test
	public void testRemoveObserver()
	{
		MockSimpleTimerObserver mock1 = new MockSimpleTimerObserver(); 
		MockSimpleTimerObserver mock2 = new MockSimpleTimerObserver();
		MockSimpleTimerObserver mock3 = new MockSimpleTimerObserver();
		
		SimpleTimer s = new SimpleTimer(); 
		s.addTimeObserver(mock1); 
		s.addTimeObserver(mock2); 
		s.addTimeObserver(mock3); 
		assertEquals(s.getNumObservers(), 3); 
		
		//update the time
		s.timeChanged();
		
		//check that all observers on at round 1
		//we don't need to add a getter because these classes are in the same file. 
		assertEquals(mock1.myTime, 1); 
		assertEquals(mock2.myTime, 1);
		assertEquals(mock3.myTime, 1);
		
		//remove #2
		s.removeTimeObserver(mock2);
		
		//update the time
		s.timeChanged();
		
		//now #1 & #3 should be at time 2, while #2 is still at time 1
		//we don't need to add a getter because these classes are in the same file. 
		assertEquals(mock1.myTime, 2); 
		assertEquals(mock2.myTime, 1);
		assertEquals(mock3.myTime, 2);
		
	}
	
	/**
	 * This tests that SimpleTimer will update time once
	 * every second.
	 * @author Dr. Girard
	 */
	@Test
	public void testSimpleTimerAsThread() throws InterruptedException
	{
	   SimpleTimer st = new SimpleTimer(1000);
	   st.start();
	   Thread.sleep(250); // So we are 1/4th a second different
	   for (int x=0;x<5;x++)
	   {
	       assertEquals(x,st.getRound()); // assumes round starts at 0
	       Thread.sleep(1000); // wait for the next time change
	   } 
	}

}

/**
 * This is a mock object used solely to test Simple Timer
 * @author Dr. Alice Armstrong
 *
 */
class MockSimpleTimerObserver implements TimerObserver
{
    public int myTime = 0;

    public void updateTime(int time)
        {
        myTime = time;
        }
}

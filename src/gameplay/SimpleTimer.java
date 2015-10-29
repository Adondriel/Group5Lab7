/**
 * 
 */
package gameplay;

import java.util.ArrayList;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class SimpleTimer extends Thread implements Timer {

	private int round; //the current round
	private ArrayList<TimerObserver> observers; //the list of observers
	private int sleepTime; //how long between rounds? for threaded version
	
	/**
	 * sets us a SimpleTimer to start at Round 0 with no observers
	 */
	public SimpleTimer()
	{
		//refactored to redirect to more complex constructor
		//round = 0; 
		//observers = new ArrayList<TimerObserver>(); 
		this(0); 
	}
	
	public SimpleTimer(int sleep)
	{
		round = 0; 
		observers = new ArrayList<TimerObserver>();
		sleepTime = sleep; 
	}

	/* (non-Javadoc)
	 * @see gameplay.Timer#addTimeObserver(gameplay.TimerObserver)
	 */
	public void addTimeObserver(TimerObserver observer) {
		observers.add(observer); 

	}

	/* (non-Javadoc)
	 * @see gameplay.Timer#removeTimeObserver(gameplay.TimerObserver)
	 */
	public void removeTimeObserver(TimerObserver observer) {
		
		//this will work as long as we are passing the actual observer, and not an equivalent
		//since TimerObserver does not include any indication of an ID, this should be OK
		observers.remove(observer); 
	}

	/* (non-Javadoc)
	 * @see gameplay.Timer#timeChanged()
	 */
	public void timeChanged() {
		round++; 
		
		//using new iterable for loop
		for(TimerObserver timer: observers)
		{
			timer.updateTime(round);
		}
	}

	/**
	 * updates the round at a fixed interval determined by the number
	 * of milliseconds used in instantiation
	 */
	public void run()
	{
		try {
			//update the round, then sleep for an interval
			for (int i =0; i < 50; i++)
			{
				Thread.sleep(sleepTime);
				timeChanged();
			}
			 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	/**
	 * @return the number of observes this Timer has
	 */
	public int getNumObservers() {
		return observers.size();
	}
	
	/**
	 * 
	 * @return the current round number
	 */
	public int getRound()
	{
		return round; 
	}

}

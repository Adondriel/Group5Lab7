/**
 * 
 */
package gameplay;

/**
 * @author Dr. Alice Armstrong
 *
 */
public interface Timer {
	/**
	 * subscribes a TimerObserver to observe this Timer
	 * @param observer the observer that wants to observe this Timer
	 */
	public void addTimeObserver(TimerObserver observer); 
	
	/**
	 * unsubscribes a TimerObserver from this Timer
	 * @param observer
	 */
	public void removeTimeObserver(TimerObserver observer); 
	
	/**
	 * updates the current round by one round
	 */
	public void timeChanged(); 
}


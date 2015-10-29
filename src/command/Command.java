/**
 * 
 */
package command;

/**
 * @author Dr. Alice Armstrong
 * The interface for a command for an action on a specific cell in the environment
 */
public interface Command {
	public void execute(int row, int col); 
}

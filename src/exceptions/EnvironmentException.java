/**
 * 
 */
package exceptions;

/**
 * @author Dr. Alice Armstrong
 * Throw this exception when the location of the cell is not in the Environment
 */
public class EnvironmentException extends Exception {
	
	public EnvironmentException(String message)
	{
		super(message); 
	}

}

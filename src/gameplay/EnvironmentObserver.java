/**
 * 
 */
package gameplay;

import weapon.Weapon;
import lifeform.LifeForm;

/**
 * @author Dr. Alice Armstrong
 *
 */
public interface EnvironmentObserver {
	
	public void updateCell(int row, int col, LifeForm lifeform, Weapon weapon1, Weapon weapon2); 

}

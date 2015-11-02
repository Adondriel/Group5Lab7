package state;

import java.util.Random;

import command.AttackCmd;
import command.MoveCmd;
import weapon.Weapon;
import lifeform.LifeForm;
import environment.Cell;
import environment.Environment;

/**
 * @author Benjamin Uleau, Jason LoBianco
 *
 */
public abstract class ActionState
{
	int maxRow=10;
	int maxCol=10;
	Environment e=Environment.getEnvironment(maxRow, maxCol);
	AIContext ai;
	LifeForm l;
	
	/**
	 * Remove the lifeform and put its weapon in a random cell
	 */
	public void respawn(){
		int newR=(int) Math.floor(Math.random()*maxRow);
		int newC=(int) Math.floor(Math.random()*maxCol);
		Weapon temp;
		if(ai.l.hasWeapon()){
			temp=ai.l.getWeapon();
			ai.l.removeWeapon();
			Cell tempCell=e.getCell(newR, newC);
			while(tempCell.getWeapon1()!=null && tempCell.getWeapon2()!=null){
				newR=(int) Math.floor(Math.random()*maxRow);
				newC=(int) Math.floor(Math.random()*maxCol);
			}
			e.addWeapon(temp, newR, newC);
		}
		e.removeLifeForm(ai.l.getRow(), ai.l.getCol());
		while(e.getLifeForm(newR, newC)!=null){
			newR=(int) Math.floor(Math.random()*maxRow+1);
			newC=(int) Math.floor(Math.random()*maxCol+1);
		}
		ai.l.refillLife();
		e.addLifeForm(ai.l, newR, newC);
		
		
	}
	
	/**
	 * @return ai's lifeform
	 */
	public LifeForm getLifeForm(){
		return ai.l;
	}
	
	/**
	 * Execute the state's action
	 */
	public abstract void executeAction();
}
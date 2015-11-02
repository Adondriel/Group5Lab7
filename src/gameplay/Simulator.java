package gameplay;

import java.util.LinkedList;

import environment.Environment;
import lifeform.Alien;
import lifeform.Human;
import recovery.RecoveryBehavior;
import weapon.Pistol;
import weapon.Attachment;
import weapon.ChainGun;
import weapon.PlasmaCannon;
import weapon.Weapon;

/**
 * 
 * @author Bradley Solorzano
 * Professor's Armstrong- Design Patterns
 * 10/28/15
 * 
 * Simulator class will construct a number of Human and Alien AI's 
 * to do battle in the provide Environment.
 * Will be informed about the passage of time 
 * through an instance of SimpleTimer.
 */
public class Simulator implements TimerObserver
{

	private int minArmor = 0;
	private int maxArmor = 10;
	private int Colum = 10;
	private int Row = 10;
	private Environment e = Environment.getEnvironment(10, 10);
	private SimpleTimer timer =  new SimpleTimer();
	
	RecoveryBehavior[] recoveryArray = new RecoveryBehavior[3];
	recoveryArray[0] = RecoveryNone;
	recoveryArray[1] = RecoveryFractional;
	recoveryArray[2] = RecoveryLinear;
	
	Weapon[] armory = new Weapon[3];
	armory[0] = Pistol;
	armory[1] = ChainGun;
	armory[2] = PlasmaCannon;
	
	Attachment[] armoryMods = new Attachment[3];
	armoryMods[0] = PowerBooster;
	armoryMods[1] = Scope;
	armoryMods[2] = Stabilizer;	
	
	/**
	 * constructor gets the
	 * @param numberOfHumans
	 * @param numberOfAliens
	 * and creates randomized AI lifeforms
	 * and random weapons
	 * placing everything at random cells in Environment
	 */
	public Simulator(int numberOfHumans, int numberOfAliens)
	{
		
		int numberOfWeapons = numberOfHumans + numberOfAliens;
		
		LinkedList<Human> humanList = new LinkedList<Human>();
		
			for(int x = 0; x < numberOfHumans; x++)
			{
				String humanName = String.format("Human%2d", x+1); //creates the name Human0x
				int armor = minArmor + (int)(Math.random() * ((maxArmor - minArmor) + 1));
				
				humanList.add(new Human(humanName, 100, armor));
			}
			
			for (Human element : humanList) //takes the humans in array list and places them on environment
			{
				int row = (int)Math.floor(Math.random() * 10); // random row 0 - 9
				int col = (int)Math.floor(Math.random() * 10); //random column random 0 - 9
				
				while(e.getLifeForm(row, col) != null) //check that the environment has no lifeForm, if it does re-randomize the number
				{
					row = (int)Math.floor(Math.random() * 10);
					col = (int)Math.floor(Math.random() * 10);
				}
				e.addLifeForm(element, row, col);
			}
		
		LinkedList<Alien> alienList = new LinkedList<Alien>();
		
			for(int x = 0; x < numberOfAliens; x++)
			{
				String alienName = String.format("Alien%2d", x+1); //creats the name Alien0x
				int recoveryID = 0 + (int)Math.random() * ((2 - 0) + 1); //will find random recovery behavior
				int recoveryRate = 0 + (int)Math.random() * ((3- 0) + 1); //setting maximum recovery rate to 3 rounds
				
				alienList.add(new Alien(alienName, 150, recoveryArray[recoveryID], recoveryRate));
			}
			
			for (Alien element : alienList) //takes the Alien in array list and places them on environment
			{
				
				int row = (int)Math.floor(Math.random() * 10); // random row 0 - 9
				int col = (int)Math.floor(Math.random() * 10); //random column random 0 - 9
				
				while(e.getLifeForm(row, col) != null) //check that the enviornment has no lifeForm, if it does re-randomize the number
				{
					row = (int)Math.floor(Math.random() * 10);
					col = (int)Math.floor(Math.random() * 10);
				}
				e.addLifeForm(element, row, col);
			}
			
		LinkedList<Weapon> weaponList = new LinkedList<Weapon>();
		
		for(int x = 0; x < numberOfWeapons; x++)
		{
			int weaponID = (int) Math.floor(Math.random() * 3); //number 0 - 2 
			int numberOfAttachments = (int) Math.floor(Math.random() * 3); //how many attachments 0-2

			Weapon weapon = armory[weaponID];
			
			if(numberOfAttachments > 0)
			{
				for(int i = 0; x < numberOfAttachments; x++)
				{
					int attachmentID = (int) Math.floor(Math.random() * 3); //which attachment
					Attachment attachedWeapon = armoryMods[attachmentID];
				}
			}
			weaponList.add(weapon);
		}
		
		for (Weapon element : weaponList) //takes the Alien in array list and places them on environment
		{
			
			int row = (int)Math.floor(Math.random() * 10); // random row 0 - 9
			int col = (int)Math.floor(Math.random() * 10); //random column random 0 - 9
			
			while(e.getCell(row, col).getWeapon1() != null && e.getCell(row, col).getWeapon2() != null) //check if the cell has 2 weapons all ready , re randomize
			{
				row = (int)Math.floor(Math.random() * 10); //number between 0 - 9
				col = (int)Math.floor(Math.random() * 10); //number between 0 - 9
			}
			e.addWeapon(element, row, col);
		}
	}

	@Override
	public void updateTime(int time)
	{
		
	}

	/**
	 * @returns the number of Aliens in the Environment
	 */
	public int getNumberOfAlien()
	{
		
		return alienList.size();
	}

	/**
	 * @returns the number of Humans in the Environment.
	 */
	public int getNumberOfHuman()
	{
		
		return humanList.size();
	}

}

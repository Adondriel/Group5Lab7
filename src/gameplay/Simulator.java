package gameplay;

import java.util.LinkedList;

import environment.Environment;
import exceptions.AttachmentException;
import exceptions.RecoveryRateException;
import lifeform.Alien;
import lifeform.Human;
import recovery.RecoveryBehavior;
import recovery.RecoveryFractional;
import recovery.RecoveryLinear;
import recovery.RecoveryNone;
import weapon.Pistol;
import weapon.Attachment;
import weapon.ChainGun;
import weapon.PlasmaCannon;
import weapon.PowerBooster;
import weapon.Scope;
import weapon.Stabilizer;
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
	
	LinkedList<Human> humanList = new LinkedList<Human>();
	LinkedList<Alien> alienList = new LinkedList<Alien>();
	LinkedList<Weapon> weaponList = new LinkedList<Weapon>();
	
	private RecoveryBehavior[] recoveryArray = new RecoveryBehavior[3];
	private Weapon[] armory = new Weapon[3];
	private Attachment[] armoryMods = new Attachment[3];
	
	/**
	 * constructor gets the
	 * @param numberOfHumans
	 * @param numberOfAliens
	 * and creates randomized AI lifeforms
	 * and random weapons
	 * placing everything at random cells in Environment
	 * @throws RecoveryRateException 
	 * @throws AttachmentException 
	 */
	public Simulator(int numberOfHumans, int numberOfAliens) throws RecoveryRateException, AttachmentException
	{
	
		int numberOfWeapons = numberOfHumans + numberOfAliens;
		
		recoveryArray[0] = new RecoveryNone();
		recoveryArray[1] = new RecoveryFractional(0.0);
		recoveryArray[2] = new RecoveryLinear(0);
		
		armory[0] = new Pistol();
		armory[1] = new ChainGun();
		armory[2] = new PlasmaCannon();
		
		
		
		
		
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
		
			
			double fractionalHeal;
			int linearHeal;
			
			for(int x = 0; x < numberOfAliens; x++)
			{
				String alienName = String.format("Alien%2d", x+1); //creats the name Alien0x
				int recoveryID = (int)Math.floor(Math.random() * 3); //will find random recovery behavior based on 0-2
				int recoveryRate = (int)Math.floor((Math.random() * 3) + 1); //revovery rate from 1-3
				
				if (recoveryID == 2) //RecoveryLinear returns 
				{
					linearHeal = (int)Math.floor(Math.random() * 6); //setting maximum recovery rate to 5
					recoveryArray[recoveryID] = new RecoveryLinear(linearHeal);
				}else
				if (recoveryID == 1) //RecoveryFractional
				{
					fractionalHeal = 0.1 + (Math.random() * (0.2 - 0.1)); //setting maximum Fractional from .1 to .2
					recoveryArray[recoveryID] = new RecoveryFractional(fractionalHeal);
				}
				
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
			
		
		/*	armoryMods[0] = new Scope();
			armoryMods[1] = new Stabillizer();
			armoryMods[2] = new PowerBooster(); */
		for(int x = 0; x < numberOfWeapons; x++)
		{
			int weaponID = (int) Math.floor(Math.random() * 3); //number 0 - 2 
			Weapon weapon = armory[weaponID]; //creates the weapon
			int numberOfAttachments = (int) Math.floor(Math.random() * 3); //how many attachments 0-2
			//int numberOfAttachments = 1 + (int)(Math.random() * ((2 - 1) + 1));

			
			
			if(numberOfAttachments > 0)
			{
				for(int i = 0; i < numberOfAttachments; i++)
				{
					int attachmentID = (int) Math.floor(Math.random() * 3); //which attachment to add 0-2
					if(attachmentID == 0) //Scope
					{
						weapon = new Scope(weapon);
					}
					if(attachmentID == 1) //Stabilizer
					{
						weapon = new Stabilizer(weapon);
					}
					if(attachmentID == 2) //PowerBooster
					{
						weapon = new PowerBooster(weapon);
					}
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

	public int getNumberOfWeapons()
	{
		
		return weaponList.size();
	}

}

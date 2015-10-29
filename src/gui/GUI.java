/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import command.Invoker;
import command.InvokerBuilder;
import recovery.RecoveryFractional;
import recovery.RecoveryLinear;
import weapon.Attachment;
import weapon.ChainGun;
import weapon.Pistol;
import weapon.PlasmaCannon;
import weapon.PowerBooster;
import weapon.Scope;
import weapon.Stabilizer;
import weapon.Weapon;
import environment.Environment;
import exceptions.AttachmentException;
import exceptions.RecoveryRateException;
import gameplay.EnvironmentObserver;
import lifeform.Alien;
import lifeform.Human;
import lifeform.LifeForm;

/**
 * @author Dr. Alice Armstrong
 *
 */
public class GUI extends JFrame implements ActionListener, EnvironmentObserver {

	JPanel map, legend, statsPanel; 
	JButton[][] mapArray;
	JLabel stats; 
	
	//the game environment
	private static Environment e;  
	
	//current Cell of interest
	int mapFocusRow = 0; 
	int mapFocusCol = 0; 

	
	//color palette
	Color grey = new Color(200, 200, 200); 
    Color black = new Color(0, 0, 0); 
    Color white = new Color(255, 255, 255); 
    Color pink =  new Color(200, 126, 135); //active cell color
    Color weapon_0 = new Color(104, 208, 255); //teal, weapon with no attachments
    Color weapon_1 = new Color(128, 0, 255); //purple, weapon with 1 attachment
    Color weapon_2 = new Color(128, 0, 64);  //magenta, weapon with 2 attachments
    Color fullHealth = new Color(0, 128, 0); //green
    Color halfHealth = new Color(229, 110, 0); //orange
    Color lowHealth = new Color(236, 0, 0); //red
    
    final int GRID_SIZE = 40; //size of a grid square
    final int WEAPON_SIZE = 7; //size of a weapon icon (circle)
    final int LIFEFORM_SIZE = 20; //size of an life form icon
    
    static final int ROWS = 12; 
    static final int COLS = 12; 
    
	public GUI () throws RecoveryRateException {
		
		//create the game environment
		e = Environment.getEnvironment(ROWS, COLS); 
		
		setLayout(new BorderLayout());
		
		//the area for the map grid
		map = new JPanel(new GridLayout(ROWS, COLS));
		map.setPreferredSize(new Dimension(GRID_SIZE*ROWS, GRID_SIZE*COLS));
		mapArray = new JButton[ROWS][COLS];
		for (int r=0;r<ROWS;r++)
        {
            for (int c=0;c<COLS;c++)
            {
            	//each grid cell is listening for a mouse action
                mapArray[r][c] = new JButton(createEmptyCell());
                //the actionCommand will identify which cell received the action
                mapArray[r][c].setActionCommand("cell: "+r+" "+c);
                mapArray[r][c].addActionListener(this);
                map.add(mapArray[r][c]);
            }
        }
		add("Center",map);
		
		//initialize the board
		initializeEnvironment(); 
		
		//the area for the Legend
		legend = new JPanel();
		//legend.setPreferredSize(new Dimension(300, 600)); 
		JPanel icons = new JPanel(new GridLayout(0, 1));
		JPanel descr = new JPanel(new GridLayout(0, 1)); 
		JLabel[][] legendArray = new JLabel[20][2]; 
		Dimension labelDim = new Dimension(GRID_SIZE*10, GRID_SIZE); 
		ImageIcon temp; 
		
		//fill the legend	
		//one weapon only - no attachments
		temp = createEmptyCell(); 
		temp = addFirstWeapon(temp, 0); 
		legendArray[1][0] = new JLabel(temp); 
		icons.add(legendArray[1][0]); 
		legendArray[1][1] = new JLabel("<html>one weapon with no attachments</html>"); 
		legendArray[1][1].setPreferredSize(labelDim);
		descr.add(legendArray[1][1]); 
		
		//one weapon only - one attachment
		//tested & work, removed for layout clarity
		/*temp = createEmptyCell(); 
		temp = addFirstWeapon(temp, 1); 
		legendArray[2][0] = new JLabel(temp); 
		icons.add(legendArray[2][0], BorderLayout.LINE_START); 
		legendArray[2][1] = new JLabel("<html>one weapon with one attachment</html>"); 
		descr.add(legendArray[2][1]); 
		
		//one weapon only - two attachments
		temp = createEmptyCell(); 
		temp = addFirstWeapon(temp, 2); 
		legendArray[3][0] = new JLabel(temp); 
		icons.add(legendArray[3][0], BorderLayout.LINE_START); 
		legendArray[3][1] = new JLabel("<html>one weapon with two attachments</html>"); 
		descr.add(legendArray[3][1]); 
		*/
		
		//two weapons only
		temp = createEmptyCell(); 
		temp = addFirstWeapon(temp, 1); 
		temp = addSecondWeapon(temp, 2); 
		legendArray[4][0] = new JLabel(temp); 
		icons.add(legendArray[4][0]); 
		legendArray[4][1] = new JLabel("<html>two weapons. #1 with one attachment. #2 with two attachments</html>"); 
		legendArray[4][1].setPreferredSize(labelDim);
		descr.add(legendArray[4][1]); 
		
		//two weapons only, then remove first
		/* test & works, removed for layotu clarity
		temp = createEmptyCell(); 
		temp = addFirstWeapon(temp, 0); 
		temp = addSecondWeapon(temp, 2); 
		temp = removeFirstWeapon(temp); 
		legendArray[5][0] = new JLabel(temp); 
		icons.add(legendArray[5][0]); 
		legendArray[5][1] = new JLabel("<html>had two weapons. #1 removed. #2 with two attachments</html>"); 
		descr.add(legendArray[5][1]); 
		
		//two weapons only, then remove second
		temp = createEmptyCell(); 
		temp = addFirstWeapon(temp, 0); 
		temp = addSecondWeapon(temp, 2); 
		temp = removeSecondWeapon(temp); 
		legendArray[6][0] = new JLabel(temp); 
		icons.add(legendArray[6][0]); 
		legendArray[6][1] = new JLabel("<html>had two weapons. #1 with no attachments. #2 removed</html>"); 
		descr.add(legendArray[6][1]); 
		*/
		
		//an alien with no weapons, two weapons in the cell
		LifeForm tempAlien = new Alien("Test Alien", 10); 
		temp = createEmptyCell(); 
		temp = addFirstWeapon(temp, 1); 
		temp = addSecondWeapon(temp, 1);
		temp = addAlien(temp, tempAlien); 
		legendArray[7][0] = new JLabel(temp); 
		icons.add(legendArray[7][0]); 
		legendArray[7][1] = new JLabel("<html>Alien w/o weapon in cell with 2 weapons, facing north</html>"); 
		legendArray[7][1].setPreferredSize(labelDim);
		descr.add(legendArray[7][1]); 
		
		//an alien with one weapon w/o attachments, two weapons in the cell
		tempAlien = new Alien("Test Alien", 10);
		Pistol p = new Pistol(); 
		tempAlien.pickUpWeapon(p); 
		temp = createEmptyCell(); 
		temp = addFirstWeapon(temp, 1); 
		temp = addSecondWeapon(temp, 1);
		temp = addAlien(temp, tempAlien); 
		legendArray[8][0] = new JLabel(temp); 
		icons.add(legendArray[8][0]); 
		legendArray[8][1] = new JLabel("<html>Alien with 1 weapon (no attachments) in cell with 2 weapons, facing north</html>"); 
		legendArray[8][1].setPreferredSize(labelDim);
		descr.add(legendArray[8][1]); 
		
		//an alien with one weapon w/o attachments, then remove alien's weapon, two weapons in the cell
		/*tested & works, removed for layout clarity
		tempAlien = new Alien("Test Alien", 10);
		p = new Pistol(); 
		tempAlien.pickUpWeapon(p); 
		temp = createEmptyCell(); 
		temp = addFirstWeapon(temp, 1); 
		temp = addSecondWeapon(temp, 1);
		temp = addAlien(temp, tempAlien); 
		temp = removeLifeFormWeapon(temp); 
		legendArray[9][0] = new JLabel(temp); 
		icons.add(legendArray[9][0]); 
		legendArray[9][1] = new JLabel("<html>Alien with no weapon in cell with 2 weapons</html>"); 
		descr.add(legendArray[9][1]); 
		*/
		
		//a human (facing north) with one weapon w/o attachments, two weapons in the cell
		Human tempHuman = new Human("Test Human", 10, 4);
		tempHuman.setDirection("South"); 
		p = new Pistol(); 
		tempHuman.pickUpWeapon(p); 
		temp = createEmptyCell(); 
		temp = addFirstWeapon(temp, 0); 
		temp = addSecondWeapon(temp, 2);
		temp = addHuman(temp, tempHuman); 
		legendArray[10][0] = new JLabel(temp); 
		icons.add(legendArray[10][0]); 
		legendArray[10][1] = new JLabel("<html>Human with 1 weapon (no attachments) in cell with 2 weapons, facing south</html>"); 
		legendArray[10][1].setPreferredSize(labelDim);
		descr.add(legendArray[10][1]); 
		
		//a human with one weapon w/o attachments, then remove Human,  two weapons in the cell
		/*tested & works, removed for layout clarity
		tempHuman = new Human("Test Human", 10, 4);
		p = new Pistol(); 
		tempHuman.pickUpWeapon(p); 
		temp = createEmptyCell(); 
		temp = addFirstWeapon(temp, 0); 
		temp = addSecondWeapon(temp, 2);
		temp = addHuman(temp, tempHuman); 
		temp = removeHuman(temp, tempHuman); 
		legendArray[11][0] = new JLabel(temp); 
		icons.add(legendArray[11][0]); 
		legendArray[11][1] = new JLabel("<html>Human with 1 weapon (no attachments), removed, in cell with 2 weapons</html>"); 
		descr.add(legendArray[11][1]); 

		//a human (facing South) with one weapon w/o attachments, two weapons in the cell
		tempHuman = new Human("Test Human", 10, 4);
		tempHuman.setDirection("South"); 
		tempHuman.takeHit(10); 
		p = new Pistol(); 
		tempHuman.pickUpWeapon(p); 
		temp = createEmptyCell(); 
		temp = addHuman(temp, tempHuman); 
		legendArray[12][0] = new JLabel(temp); 
		icons.add(legendArray[12][0]); 
		legendArray[12][1] = new JLabel("<html>Human with 1 weapon (no attachments), facing south</html>"); 
		descr.add(legendArray[12][1]); 
		*/
		
		//a human (facing East) with one weapon w/o attachments, two weapons in the cell
		tempHuman = new Human("Test Human", 10, 4);
		tempHuman.setDirection("East"); 
		p = new Pistol(); 
		tempHuman.pickUpWeapon(p); 
		temp = createEmptyCell(); 
		temp = addHuman(temp, tempHuman); 
		legendArray[13][0] = new JLabel(temp); 
		icons.add(legendArray[13][0]); 
		legendArray[13][1] = new JLabel("<html>Human with 1 weapon (no attachments), facing east</html>"); 
		legendArray[13][1].setPreferredSize(labelDim);
		descr.add(legendArray[13][1]); 

		//a human (facing West) with one weapon w/o attachments, two weapons in the cell
		tempHuman = new Human("Test Human", 10, 4);
		tempHuman.setDirection("West"); 
		p = new Pistol(); 
		tempHuman.pickUpWeapon(p); 
		temp = createEmptyCell(); 
		temp = addHuman(temp, tempHuman); 
		legendArray[14][0] = new JLabel(temp); 
		icons.add(legendArray[14][0]); 
		legendArray[14][1] = new JLabel("<html>Human with 1 weapon (no attachments), facing west</html>"); 
		legendArray[14][1].setPreferredSize(labelDim);
		descr.add(legendArray[14][1]); 
		
		legend.add(icons, BorderLayout.LINE_START); 
		legend.add(descr, BorderLayout.CENTER); 
		
		stats = new JLabel("<html><center><p>Cell Stats Will Go Here...</p>"
						  +"LifeForm Type: name [if LifeForm in Cell]<br>"
						  +"LifeForm LP: <br>"
						  +"Weapon: weapon<br>"
						  +"Direction: direction<br>"
						  +"-------------------------------------------<br>"
						  +"Weapon1 Info: [if present]<br>"
						  +"Weapon2 Info: [if present]</center></html>"); 
		stats.setPreferredSize(new Dimension(600, 200)); 
		statsPanel = new JPanel(); 
		statsPanel.add(stats); 
		add("South", statsPanel); 
		
		add("West", legend);   
		
		
		//padding for the top of the map
		JLabel northPad = new JLabel(createEmptyPad()); 
		add("North", northPad); 
		
		//make everything visible
        pack();
        setVisible(true);
		
	}
	
	/**
	 * create an empty map grid square
	 * @return
	 */
    public ImageIcon createEmptyCell()
    {
        BufferedImage empty = new 
            BufferedImage(GRID_SIZE,GRID_SIZE,BufferedImage.TYPE_3BYTE_BGR);
        Graphics drawer = empty.getGraphics();
       
        drawer.setColor(grey);
        drawer.fillRect(0, 0, GRID_SIZE, GRID_SIZE);
        drawer.setColor(black); 
        drawer.drawRect(0, 0, GRID_SIZE, GRID_SIZE); 
        //drawer.setColor(new Color(0,255,0));
        //drawer.fillOval(20, 20, 10, 10);
        
        return new ImageIcon(empty);
    }
    
    /**
    * create an empty map grid square, with a pick background to indicate that this is focus cell
	 * @return
	 */
   public ImageIcon createActiveCell()
   {
       BufferedImage empty = new 
           BufferedImage(GRID_SIZE,GRID_SIZE,BufferedImage.TYPE_3BYTE_BGR);
       Graphics drawer = empty.getGraphics();
      
       drawer.setColor(pink);
       drawer.fillRect(0, 0, GRID_SIZE, GRID_SIZE);
       drawer.setColor(black); 
       drawer.drawRect(0, 0, GRID_SIZE, GRID_SIZE); 
       //drawer.setColor(new Color(0,255,0));
       //drawer.fillOval(20, 20, 10, 10);
       
       return new ImageIcon(empty);
   }
    
    /* ~~~ ADD ITEMS TO A CELL ~~~ */
    /**
     * adds a weapon in the upper righthand corner of the cell, assumes the first weapon slot is empty
     * @param icon the current image of the cell
     * @param numAttachments number of attachments for the first weapon
     * @return an updated image for a cell
     */
    public ImageIcon addFirstWeapon(ImageIcon icon, int numAttachments)
    {
    	//copy the only icon onto a BufferedImage so we can add to it
    	//from StackOverflow.com
    	//credit Werner Kvalem Vesterås, 
    	//http://stackoverflow.com/questions/15053214/converting-an-imageicon-to-a-bufferedimage
    	BufferedImage bi = new BufferedImage(
    		    icon.getIconWidth(),
    		    icon.getIconHeight(),
    		    BufferedImage.TYPE_3BYTE_BGR);
    		Graphics drawer = bi.getGraphics();
    		// paint the Icon to the BufferedImage.
    		icon.paintIcon(null, drawer, 0,0);
    		
    		//set the appropriate fill color for the weapon icon
	        if (numAttachments == 0)
	        {
	        	drawer.setColor(weapon_0); 
	        }
	        else if (numAttachments == 1)
	        {
	        	drawer.setColor(weapon_1); 
	        }
	        else
	        {
	        	drawer.setColor(weapon_2); 
	        }
	        
	        drawer.fillOval(GRID_SIZE-WEAPON_SIZE, 0, WEAPON_SIZE, WEAPON_SIZE); 
	        
	        return new ImageIcon(bi);	
    }
  
    /**
     * adds a weapon in the lower righthand corner of the cell, assumes the second weapon slot is empty
     * @param icon the current image of the cell
     * @param numAttachments number of attachments for the second weapon
     * @return the update image of the cell
     */
    public ImageIcon addSecondWeapon(ImageIcon icon, int numAttachments)
    {
    	//copy the only icon onto a BufferedImage so we can add to it
    	//from StackOverflow.com
    	//credit Werner Kvalem Vesterås, 
    	//http://stackoverflow.com/questions/15053214/converting-an-imageicon-to-a-bufferedimage
    	BufferedImage bi = new BufferedImage(
    		    icon.getIconWidth(),
    		    icon.getIconHeight(),
    		    BufferedImage.TYPE_3BYTE_BGR);
    		Graphics drawer = bi.getGraphics();
    		// paint the Icon to the BufferedImage.
    		icon.paintIcon(null, drawer, 0,0);
    		
    		//set the appropriate fill color for the weapon icon
	        if (numAttachments == 0)
	        {
	        	drawer.setColor(weapon_0); 
	        }
	        else if (numAttachments == 1)
	        {
	        	drawer.setColor(weapon_1); 
	        }
	        else
	        {
	        	drawer.setColor(weapon_2); 
	        }
	        
	        drawer.fillOval(GRID_SIZE-WEAPON_SIZE, GRID_SIZE-WEAPON_SIZE, WEAPON_SIZE, WEAPON_SIZE);  
	        
	        return new ImageIcon(bi);	
    }
    
    /**
     * places an Alien icon (circle) in the center of the cell
     * the Alien's life point will be a bar "behind" the Alien, i.e. if the Alien is facing North, the bar will appear to the South
     * if the Alien is carrying a weapon, the weapon will appear as a dot in the center of Alien's icon
     * @param icon
     * @param alien
     * @return
     */
    public ImageIcon addAlien(ImageIcon icon, LifeForm alien)
    {
    	//copy the only icon onto a BufferedImage so we can add to it
    	//from StackOverflow.com
    	//credit Werner Kvalem Vesterås, 
    	//http://stackoverflow.com/questions/15053214/converting-an-imageicon-to-a-bufferedimage
    	BufferedImage bi = new BufferedImage(
    		    icon.getIconWidth(),
    		    icon.getIconHeight(),
    		    BufferedImage.TYPE_3BYTE_BGR);
    		Graphics drawer = bi.getGraphics();
    		// paint the Icon to the BufferedImage.
    		icon.paintIcon(null, drawer, 0,0);
    		
    		//set the fill color to white
    		drawer.setColor(white); 
    		//fill the Alien icon
    		drawer.fillOval(GRID_SIZE/4, GRID_SIZE/4, LIFEFORM_SIZE, LIFEFORM_SIZE);
    		
    		//draw the life bar
    		icon = addLifeBar(new ImageIcon(bi), alien); 
    		icon.paintIcon(null,  drawer,  0,  0); 
    		
    		//draw the weapon
    		if (alien.hasWeapon())
    		{
    			Weapon tempWeapon = alien.getWeapon(); 
    			icon = addLifeFormWeapon(new ImageIcon(bi), tempWeapon.getNumAttachments()); 
    			return icon; 
    		}
    		
    		
    		
    		return new ImageIcon(bi);
    }
    
    /**
     * places an Alien icon (circle) in the center of the cell
     * the Alien's life point will be a bar "behind" the Alien, i.e. if the Alien is facing North, the bar will appear to the South
     * if the Alien is carrying a weapon, the weapon will appear as a dot in the center of Alien's icon
     * @param icon
     * @param alien
     * @return
     */
    public ImageIcon addHuman(ImageIcon icon, Human human)
    {
    	//copy the only icon onto a BufferedImage so we can add to it
    	//from StackOverflow.com
    	//credit Werner Kvalem Vesterås, 
    	//http://stackoverflow.com/questions/15053214/converting-an-imageicon-to-a-bufferedimage
    	BufferedImage bi = new BufferedImage(
    		    icon.getIconWidth(),
    		    icon.getIconHeight(),
    		    BufferedImage.TYPE_3BYTE_BGR);
    		Graphics drawer = bi.getGraphics();
    		// paint the Icon to the BufferedImage.
    		icon.paintIcon(null, drawer, 0,0);
    		
    		//set the fill color to white
    		drawer.setColor(white); 
    		//fill the Alien icon
    		drawer.fillRect(GRID_SIZE/4, GRID_SIZE/4, LIFEFORM_SIZE, LIFEFORM_SIZE);
    		
    		//draw the life bar
    		icon = addLifeBar(new ImageIcon(bi), human); 
    		icon.paintIcon(null,  drawer,  0,  0); 
    		
    		//draw the weapon
    		if (human.hasWeapon())
    		{
    			Weapon tempWeapon = human.getWeapon(); 
    			icon = addLifeFormWeapon(new ImageIcon(bi), tempWeapon.getNumAttachments()); 
    			return icon; 
    		}
    		
    		
    		
    		return new ImageIcon(bi);
    }
    
    /**
     * add the icon for a weapon belonging to a lifeform in the center of the cell
     * @param icon the current cell image
     * @param numAttachments how many attachments the lifeform's weapon has
     * @return the updated image
     */
    public ImageIcon addLifeFormWeapon(ImageIcon icon, int numAttachments)
    {
    	//copy the only icon onto a BufferedImage so we can add to it
    	//from StackOverflow.com
    	//credit Werner Kvalem Vesterås, 
    	//http://stackoverflow.com/questions/15053214/converting-an-imageicon-to-a-bufferedimage
    	BufferedImage bi = new BufferedImage(
    		    icon.getIconWidth(),
    		    icon.getIconHeight(),
    		    BufferedImage.TYPE_3BYTE_BGR);
    		Graphics drawer = bi.getGraphics();
    		// paint the Icon to the BufferedImage.
    		icon.paintIcon(null, drawer, 0,0);
    		
    		//set the appropriate fill color for the weapon icon
	        if (numAttachments == 0)
	        {
	        	drawer.setColor(weapon_0); 
	        }
	        else if (numAttachments == 1)
	        {
	        	drawer.setColor(weapon_1); 
	        }
	        else
	        {
	        	drawer.setColor(weapon_2); 
	        }
	        
	        drawer.fillOval(GRID_SIZE/2-WEAPON_SIZE/2, GRID_SIZE/2-WEAPON_SIZE/2, WEAPON_SIZE, WEAPON_SIZE); 
	        return new ImageIcon(bi);
    }
	
    /**
     * add a lifebar "behind" a Lifeform. If the LifeForm is facing North, the lifebar will be on the south side of the lifeform icon
     * @param icon the current cell image
     * @param numAttachments how many attachments the lifeform's weapon has
     * @return the updated image
     */
    public ImageIcon addLifeBar(ImageIcon icon, LifeForm being)
    {
    	//copy the only icon onto a BufferedImage so we can add to it
    	//from StackOverflow.com
    	//credit Werner Kvalem Vesterås, 
    	//http://stackoverflow.com/questions/15053214/converting-an-imageicon-to-a-bufferedimage
    	BufferedImage bi = new BufferedImage(
    		    icon.getIconWidth(),
    		    icon.getIconHeight(),
    		    BufferedImage.TYPE_3BYTE_BGR);
    		Graphics drawer = bi.getGraphics();
    		// paint the Icon to the BufferedImage.
    		icon.paintIcon(null, drawer, 0,0);
    		
    		//get the current life level
    		double life = (double)being.getCurrentLifePoints()/being.getMaxLifePoints(); 
    		
    		//set the length of the life bar
    		int barsize = (int)(life*GRID_SIZE-2); 
    		
    		//set the appropriate fill color for the life bar
	        if (life > 0.8)
	        {
	        	drawer.setColor(fullHealth); 
	        }
	        else if (life > 0.4)
	        {
	        	drawer.setColor(halfHealth); 
	        }
	        else
	        {
	        	drawer.setColor(lowHealth); 
	        }
	        
	        
	        //pick a location
	        if (being.getDirection().equals("North"))
	        {
	        	//place the bar on the south side of the icon
	        	drawer.fillRect(2, GRID_SIZE-WEAPON_SIZE-1, barsize, 2);
	        }
	        else if(being.getDirection().equals("South"))
	        {
	        	//place the bar on the north side of the icon
	        	drawer.fillRect(2, WEAPON_SIZE+1, barsize, 2);
	        }
	        else if(being.getDirection().equals("East"))
	        {
	        	//place the bar on the west side of the icon
	        	drawer.fillRect(WEAPON_SIZE+1, 2, 2, barsize);
	        }
	        else
	        {
	        	//place the bar on the east side of the icon
	        	drawer.fillRect(GRID_SIZE-WEAPON_SIZE-1, 2, 2, barsize);
	        }
	       
	        return new ImageIcon(bi);
    }    
 
    /* ~~~ REMOVE ITEMS FROM A CELL ~~~ */
    /**
     * removes a weapon in the upper righthand corner of the cell
     * @param icon the current image of the cell
     * @return an updated image for a cell
     */
    public ImageIcon removeFirstWeapon(ImageIcon icon)
    {
    	//copy the only icon onto a BufferedImage so we can add to it
    	//from StackOverflow.com
    	//credit Werner Kvalem Vesterås, 
    	//http://stackoverflow.com/questions/15053214/converting-an-imageicon-to-a-bufferedimage
    	BufferedImage bi = new BufferedImage(
    		    icon.getIconWidth(),
    		    icon.getIconHeight(),
    		    BufferedImage.TYPE_3BYTE_BGR);
    		Graphics drawer = bi.getGraphics();
    		// paint the Icon to the BufferedImage.
    		icon.paintIcon(null, drawer, 0,0);
    		
    		//set the background color
    		drawer.setColor(grey); 
	        drawer.fillOval(GRID_SIZE-WEAPON_SIZE, 0, WEAPON_SIZE, WEAPON_SIZE); 
	        
	        return new ImageIcon(bi);	
    }
    
    /**
     * removes a weapon in the lower righthand corner of the cell
     * @param icon the current image of the cell
     * @return the updated image of the cell
     */
    public ImageIcon removeSecondWeapon(ImageIcon icon)
    {
    	//copy the only icon onto a BufferedImage so we can add to it
    	//from StackOverflow.com
    	//credit Werner Kvalem Vesterås, 
    	//http://stackoverflow.com/questions/15053214/converting-an-imageicon-to-a-bufferedimage
    	BufferedImage bi = new BufferedImage(
    		    icon.getIconWidth(),
    		    icon.getIconHeight(),
    		    BufferedImage.TYPE_3BYTE_BGR);
    		Graphics drawer = bi.getGraphics();
    		// paint the Icon to the BufferedImage.
    		icon.paintIcon(null, drawer, 0,0);
    		
    		//set to background color
    		drawer.setColor(grey); 
	        drawer.fillOval(GRID_SIZE-WEAPON_SIZE, GRID_SIZE-WEAPON_SIZE, WEAPON_SIZE, WEAPON_SIZE);  
	        
	        return new ImageIcon(bi);	
    }
    
    /**
     * removes the icon for a weapon belonging to a lifeform in the center of the cell
     * @param icon the current cell image
     * @return the updated image
     */
    public ImageIcon removeLifeFormWeapon(ImageIcon icon)
    {
    	//copy the only icon onto a BufferedImage so we can add to it
    	//from StackOverflow.com
    	//credit Werner Kvalem Vesterås, 
    	//http://stackoverflow.com/questions/15053214/converting-an-imageicon-to-a-bufferedimage
    	BufferedImage bi = new BufferedImage(
    		    icon.getIconWidth(),
    		    icon.getIconHeight(),
    		    BufferedImage.TYPE_3BYTE_BGR);
    		Graphics drawer = bi.getGraphics();
    		// paint the Icon to the BufferedImage.
    		icon.paintIcon(null, drawer, 0,0);
    		
    		//set to white (the life form color)
        	drawer.setColor(white); 
	        
	        drawer.fillOval(GRID_SIZE/2-WEAPON_SIZE/2, GRID_SIZE/2-WEAPON_SIZE/2, WEAPON_SIZE, WEAPON_SIZE); 
	        return new ImageIcon(bi);
    }
    
    /**
     * removes the Human icon from a cell
     * @param icon
     * @return
     */
    public ImageIcon removeHuman(ImageIcon icon, Human human)
    {
    	//copy the only icon onto a BufferedImage so we can add to it
    	//from StackOverflow.com
    	//credit Werner Kvalem Vesterås, 
    	//http://stackoverflow.com/questions/15053214/converting-an-imageicon-to-a-bufferedimage
    	BufferedImage bi = new BufferedImage(
    		    icon.getIconWidth(),
    		    icon.getIconHeight(),
    		    BufferedImage.TYPE_3BYTE_BGR);
    		Graphics drawer = bi.getGraphics();
    		// paint the Icon to the BufferedImage.
    		icon.paintIcon(null, drawer, 0,0);
    		
    		//erase the life bar
    		icon = removeLifeBar(new ImageIcon(bi), human); 
    		icon.paintIcon(null,  drawer,  0,  0); 
 
    		//set the fill color to white
    		drawer.setColor(grey); 
    		//fill the Alien icon
    		drawer.fillRect(GRID_SIZE/4, GRID_SIZE/4, LIFEFORM_SIZE, LIFEFORM_SIZE);

    		return new ImageIcon(bi);
    }
    
    /**
     * removes the lifebar from an image
     * @param icon the current image
     * @param being the lifeform in the cell
     * @return the updated image
     */
    public ImageIcon removeLifeBar(ImageIcon icon, LifeForm being)
    {
    	//copy the only icon onto a BufferedImage so we can add to it
    	//from StackOverflow.com
    	//credit Werner Kvalem Vesterås, 
    	//http://stackoverflow.com/questions/15053214/converting-an-imageicon-to-a-bufferedimage
    	BufferedImage bi = new BufferedImage(
    		    icon.getIconWidth(),
    		    icon.getIconHeight(),
    		    BufferedImage.TYPE_3BYTE_BGR);
    		Graphics drawer = bi.getGraphics();
    		// paint the Icon to the BufferedImage.
    		icon.paintIcon(null, drawer, 0,0);
    		
    		//set color to background grey
    		drawer.setColor(grey); 
    		
    		//pick a location
	        if (being.getDirection().equals("North"))
	        {
	        	//place the bar on the south side of the icon
	        	drawer.fillRect(2, GRID_SIZE-WEAPON_SIZE-1, GRID_SIZE-2, 2);
	        }
	        else if(being.getDirection().equals("South"))
	        {
	        	//place the bar on the north side of the icon
	        	drawer.fillRect(2, WEAPON_SIZE+1, GRID_SIZE-2, 2);
	        }
	        else if(being.getDirection().equals("East"))
	        {
	        	//place the bar on the west side of the icon
	        	drawer.fillRect(WEAPON_SIZE+1, 2, 2, GRID_SIZE-2);
	        }
	        else
	        {
	        	//place the bar on the east side of the icon
	        	drawer.fillRect(GRID_SIZE-WEAPON_SIZE-1, 2, 2, GRID_SIZE-2);
	        }
	       
	        return new ImageIcon(bi);
    }   
    
	/**
	 * create an empty pad 240 wide x GRID_SIZE high
	 * @return
	 */
	public ImageIcon createEmptyPad()
	{
		BufferedImage empty = new 
	            BufferedImage(240,GRID_SIZE,BufferedImage.TYPE_3BYTE_BGR);
	        Graphics drawer = empty.getGraphics();
	        Color grey = new Color(240, 240, 240); 
	       
	        
	        drawer.setColor(grey);
	        drawer.fillRect(0, 0, 600, GRID_SIZE);
	        
	        return new ImageIcon(empty);
	}


	/**
	 * handles button clicks on the map
	 * clicked Cell's info is diplayed below the Map
	 * LATER: cell action options will be displayed to the East of the Map
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		//get the row & col of the cell that was clicked
		String message = arg0.getActionCommand(); 
		if(message.contains("cell: "))
		{
			int oldFocusRow = mapFocusRow; 
			int oldFocusCol = mapFocusCol; 
			
			LifeForm lifeform = e.getLifeForm(oldFocusRow, oldFocusCol); 
			Weapon weapon1 = e.getWeapons(oldFocusRow, oldFocusCol)[0]; 
			Weapon weapon2 = e.getWeapons(oldFocusRow, oldFocusCol)[1]; 
			
			//reset the last highlighted cell to grey background
			ImageIcon temp = createEmptyCell(); 
			if (lifeform != null)
			{
				if (lifeform instanceof Human)
				{
					temp = addHuman(temp, (Human)lifeform); 
				}
				else
				{
					temp = addAlien(temp, (Alien)lifeform); 
				}
				
				if (weapon1 != null)
				{
					temp = addFirstWeapon(temp, weapon1.getNumAttachments());
				}
				
				if (weapon2 != null)
				{
					temp = addSecondWeapon(temp, weapon2.getNumAttachments());
				}
				
			}
			//change the old cell's color
			mapArray[oldFocusRow][oldFocusCol].setIcon(temp); 
			
			
			//this message came from the map
			//get this cell's information and display it on the stats pane
			Scanner s = new Scanner(message); 
			s.next(); //"cell: "
			e.focusRow = s.nextInt(); //get the row
			e.focusCol = s.nextInt(); //get the col
			mapFocusRow = e.focusRow; 
			mapFocusCol = e.focusCol; 
			
			//update the focus cell's background color to pink
			temp = createActiveCell(); 
			
			lifeform = e.getLifeForm(mapFocusRow, mapFocusCol); 
			weapon1 = e.getWeapons(mapFocusRow, mapFocusCol)[0]; 
			weapon2 = e.getWeapons(mapFocusRow, mapFocusCol)[1]; 
			
			String update = "<html><p>Row: "+mapFocusRow+" Col: "+mapFocusCol+"</p>"; 
			if (lifeform != null)
			{
				if (lifeform instanceof Human)
				{
					temp = addHuman(temp, (Human)lifeform); 
				}
				else
				{
					temp = addAlien(temp, (Alien)lifeform); 
				}
				
				update+= "<p>"+lifeform.getName()+": "; 
				if (lifeform instanceof Human)
				{
					update +="HUMAN -- armor: ";
					Human tempH = (Human)lifeform; 
					update +=tempH.getArmorPoints()+"</p>"; 
				}
				else
				{
					update += "ALIEN -- recovers every ";
					Alien tempA = (Alien)lifeform; 
					update += tempA.getRecoveryRate()+" rounds</p>"; 
				}
				update += "<p>life: "+(double)lifeform.getCurrentLifePoints()/lifeform.getMaxLifePoints()*100+"%</p>"; 
				update += "<p>unarmed attack: "+lifeform.getAttackStrength()+"</p>"; 
				update += "<p>weapon: "; 
				if (lifeform.hasWeapon())
				{
					Weapon w = lifeform.getWeapon(); 
					update += w +"; ammo: "+w.getCurrentAmmo()+"</p>";   
				}
				else
				{
					update += "none</p>"; 
				}
			}
			if (weapon1 != null)
			{
				temp = addFirstWeapon(temp, weapon1.getNumAttachments()); 
				update += "<p><p>cell weapon #1: "; 
				update += weapon1 +"; ammo: "+weapon1.getCurrentAmmo()+"</p>";
			}
			if (weapon2 != null)
			{
				temp = addSecondWeapon(temp, weapon2.getNumAttachments()); 
				
				update += "<p><p>cell weapon #2: "; 
				update += weapon2 +"; ammo: "+weapon2.getCurrentAmmo()+"</p>";
			}
			stats.setText(update);
			
			mapArray[mapFocusRow][mapFocusCol].setIcon(temp);
			
		}
		
	}
	
	/**
	 * create an environment with 4 humans, 4 aliens, and 20 Weapons
	 * LifeForms start without Weapons
	 * The Weapons are placed randomly and are generated with random attachments
	 */
	private void initializeEnvironment()
	{
		ImageIcon tempIcon; 
		
		Human h1 = new Human("Human1", 15, 3); 
		Human h2 = new Human("Human2", 10, 5); 
		Human h3 = new Human("Human3", 25, 2); 
		Human h4 = new Human("Human4", 30, 8); 
		
		Human[] hArray = {h1, h2, h3, h4}; 
		
		boolean placed = false; 
		//add humans to random locations
		int randRow = (int)(Math.random()*ROWS); 
		int randCol = (int)(Math.random()*COLS);
		for (int i = 0; i < 4; i++)
		{
			do
			{
				placed = e.addLifeForm(hArray[i], randRow, randCol);
				if (placed)
				{
					tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
					mapArray[randRow][randCol].setIcon(addHuman(tempIcon, hArray[i])); 
				}
				randRow = (int)(Math.random()*ROWS); 
				randCol = (int)(Math.random()*COLS); 
			}while(!placed);
		}
		
		//add Aliens to the Environment
		try {
			RecoveryFractional f = new RecoveryFractional(.2);
			RecoveryLinear l = new RecoveryLinear(3);
			Alien a1 = new Alien("Alien1", 25, f, 3);
			Alien a2 = new Alien("Alien2", 30, l, 2); 
			Alien a3 = new Alien("Alien3", 10, f, 1); 
			Alien a4 = new Alien("Alien4", 15, l, 4); 
			Alien[] aArray = {a1, a2, a3, a4}; 
			
			for (int i = 0; i < 4; i++)
			{
				do
				{
					placed = e.addLifeForm(aArray[i], randRow, randCol);
					if (placed)
					{
						tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
						mapArray[randRow][randCol].setIcon(addAlien(tempIcon, aArray[i])); 
					}
					randRow = (int)(Math.random()*ROWS); 
					randCol = (int)(Math.random()*COLS); 
				}while(!placed);
			}
		
			
		} catch (RecoveryRateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//add Weapons to the Evironment
		Pistol p; 
		PlasmaCannon pC; 
		ChainGun c; 
		Scope s; 
		Stabilizer st; 
		PowerBooster pB; 
		
		int randW; 
		int randA; 
		
		try {
			//add 20 Weapons
			for (int w = 0; w < 20; w++)
			{
				//pick a random location
				randRow = (int)(Math.random()*ROWS); 
				randCol = (int)(Math.random()*COLS);
				
				//check to make sure we could put another weapon there
				while (e.getWeapons(randRow, randCol)[0] != null && e.getWeapons(randRow, randCol)[1] != null)
				{
					randRow = (int)(Math.random()*ROWS); 
					randCol = (int)(Math.random()*COLS);
				}
				
				//pick a random kind of weapon
				randW = (int)(Math.random()*3); 
				if (randW == 0)
				{
					p = new Pistol(); 
					
					//try to add an attachment
					randA = (int)(Math.random()*4); 
					if (randA ==0)
					{
						//add a scope as the first attachment
						s = new Scope(p); 
						randA = (int)(Math.random()*4);
						//try to add a second attachment
						if (randA == 0)
						{
							//add a scope
							s = new Scope(s); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(s, randRow, randCol);
						}
						else if (randA == 1)
						{
							//add a Stabilizer
							st = new Stabilizer(s); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(st, randRow, randCol);
						}
						else if (randA == 2)
						{
							//add a PowerBooster
							pB = new PowerBooster(s);
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(pB, randRow, randCol);
						}
						else
						{
							//no second attachment
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 1));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 1));
							}
							e.addWeapon(s, randRow, randCol);
						}
					}
					else if (randA == 1)
					{
						//add a stabilizer as the first attachment
						st = new Stabilizer(p); 
						randA = (int)(Math.random()*4);
						//try to add a second attachment
						if (randA == 0)
						{
							//add a scope
							s = new Scope(st); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(s, randRow, randCol);
						}
						else if (randA == 1)
						{
							//add a Stabilizer
							st = new Stabilizer(st); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(st, randRow, randCol);
						}
						else if (randA == 2)
						{
							//add a PowerBooster
							pB = new PowerBooster(st);
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(pB, randRow, randCol);
						}
						else
						{
							//no second attachment
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 1));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 1));
							}
							e.addWeapon(st, randRow, randCol);
						}
					}
					else if (randA == 2)
					{
						//add a PowerBooster as the first attachment
						//add a scope as the first attachment
						pB = new PowerBooster(p); 
						randA = (int)(Math.random()*4);
						//try to add a second attachment
						if (randA == 0)
						{
							//add a scope
							s = new Scope(pB); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(s, randRow, randCol);
						}
						else if (randA == 1)
						{
							//add a Stabilizer
							st = new Stabilizer(pB); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(st, randRow, randCol);
						}
						else if (randA == 2)
						{
							//add a PowerBooster
							pB = new PowerBooster(pB);
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(pB, randRow, randCol);
						}
						else
						{
							//no second attachment
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 1));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 1));
							}
							e.addWeapon(pB, randRow, randCol);
						}
					}
					else
					{
						//no attachments
						tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
						if(e.getWeapons(randRow, randCol)[0] == null)
						{
							mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 0));
						}
						else
						{
							mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 0));
						}
						e.addWeapon(p, randRow, randCol);
					}
				}
				else if (randW == 1)
				{
					pC = new PlasmaCannon(); 
					
					//try to add an attachment
					randA = (int)(Math.random()*4); 
					if (randA ==0)
					{
						//add a scope as the first attachment
						s = new Scope(pC); 
						randA = (int)(Math.random()*4);
						//try to add a second attachment
						if (randA == 0)
						{
							//add a scope
							s = new Scope(s); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(s, randRow, randCol);
						}
						else if (randA == 1)
						{
							//add a Stabilizer
							st = new Stabilizer(s); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(st, randRow, randCol);
						}
						else if (randA == 2)
						{
							//add a PowerBooster
							pB = new PowerBooster(s);
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(pB, randRow, randCol);
						}
						else
						{
							//no second attachment
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 1));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 1));
							}
							e.addWeapon(s, randRow, randCol);
						}
					}
					else if (randA == 1)
					{
						//add a stabilizer as the first attachment
						st = new Stabilizer(pC); 
						randA = (int)(Math.random()*4);
						//try to add a second attachment
						if (randA == 0)
						{
							//add a scope
							s = new Scope(st); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(s, randRow, randCol);
						}
						else if (randA == 1)
						{
							//add a Stabilizer
							st = new Stabilizer(st); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(st, randRow, randCol);
						}
						else if (randA == 2)
						{
							//add a PowerBooster
							pB = new PowerBooster(st);
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(pB, randRow, randCol);
						}
						else
						{
							//no second attachment
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 1));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 1));
							}
							e.addWeapon(st, randRow, randCol);
						}
					}
					else if (randA == 2)
					{
						//add a PowerBooster as the first attachment
						//add a scope as the first attachment
						pB = new PowerBooster(pC); 
						randA = (int)(Math.random()*4);
						//try to add a second attachment
						if (randA == 0)
						{
							//add a scope
							s = new Scope(pB); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(s, randRow, randCol);
						}
						else if (randA == 1)
						{
							//add a Stabilizer
							st = new Stabilizer(pB); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(st, randRow, randCol);
						}
						else if (randA == 2)
						{
							//add a PowerBooster
							pB = new PowerBooster(pB);
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(pB, randRow, randCol);
						}
						else
						{
							//no second attachment
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 1));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 1));
							}
							e.addWeapon(pB, randRow, randCol);
						}
					}
					else
					{
						//no attachments
						tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
						if(e.getWeapons(randRow, randCol)[0] == null)
						{
							mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 0));
						}
						else
						{
							mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 0));
						}
						e.addWeapon(pC, randRow, randCol);
					}
				}
				else if (randW == 2)
				{
					//add a Chain Gun
					c = new ChainGun(); 
					
					//try to add an attachment
					randA = (int)(Math.random()*4); 
					if (randA ==0)
					{
						//add a scope as the first attachment
						s = new Scope(c); 
						randA = (int)(Math.random()*4);
						//try to add a second attachment
						if (randA == 0)
						{
							//add a scope
							s = new Scope(s); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(s, randRow, randCol);
						}
						else if (randA == 1)
						{
							//add a Stabilizer
							st = new Stabilizer(s); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(st, randRow, randCol);
						}
						else if (randA == 2)
						{
							//add a PowerBooster
							pB = new PowerBooster(s);
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(pB, randRow, randCol);
						}
						else
						{
							//no second attachment
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 1));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 1));
							}
							e.addWeapon(s, randRow, randCol);
						}
					}
					else if (randA == 1)
					{
						//add a stabilizer as the first attachment
						st = new Stabilizer(c); 
						randA = (int)(Math.random()*4);
						//try to add a second attachment
						if (randA == 0)
						{
							//add a scope
							s = new Scope(st); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(s, randRow, randCol);
						}
						else if (randA == 1)
						{
							//add a Stabilizer
							st = new Stabilizer(st); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(st, randRow, randCol);
						}
						else if (randA == 2)
						{
							//add a PowerBooster
							pB = new PowerBooster(st);
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(pB, randRow, randCol);
						}
						else
						{
							//no second attachment
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 1));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 1));
							}
							e.addWeapon(st, randRow, randCol);
						}
					}
					else if (randA == 2)
					{
						//add a PowerBooster as the first attachment
						//add a scope as the first attachment
						pB = new PowerBooster(c); 
						randA = (int)(Math.random()*4);
						//try to add a second attachment
						if (randA == 0)
						{
							//add a scope
							s = new Scope(pB); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(s, randRow, randCol);
						}
						else if (randA == 1)
						{
							//add a Stabilizer
							st = new Stabilizer(pB); 
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(st, randRow, randCol);
						}
						else if (randA == 2)
						{
							//add a PowerBooster
							pB = new PowerBooster(pB);
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 2));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 2));
							}
							e.addWeapon(pB, randRow, randCol);
						}
						else
						{
							//no second attachment
							tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
							if(e.getWeapons(randRow, randCol)[0] == null)
							{
								mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 1));
							}
							else
							{
								mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 1));
							}
							e.addWeapon(pB, randRow, randCol);
						}
					}
					else
					{
						//no attachments
						tempIcon = (ImageIcon) mapArray[randRow][randCol].getIcon(); 
						if(e.getWeapons(randRow, randCol)[0] == null)
						{
							mapArray[randRow][randCol].setIcon(addFirstWeapon(tempIcon, 0));
						}
						else
						{
							mapArray[randRow][randCol].setIcon(addSecondWeapon(tempIcon, 0));
						}
						e.addWeapon(c, randRow, randCol);
					}
				}
			}
		} catch (AttachmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	/**
	 * redraws the map cell to reflect the new state of the cell in the Environment
	 * @see gameplay.EnvironmentObserver#updateCell(int, int, lifeform.LifeForm, weapon.Weapon, weapon.Weapon)
	 */
	@Override
	public void updateCell(int row, int col, LifeForm lifeform, Weapon weapon1, Weapon weapon2) {
		//clear the cell
		ImageIcon tempIcon = createEmptyCell(); 
		
		//add a life form, if one exists
		//remove any dead lifeforms
		if (lifeform != null && lifeform.getCurrentLifePoints()>0)
		{
			if(lifeform instanceof Human)
			{
				tempIcon = addHuman(tempIcon, (Human)lifeform); 
			}
			else
			{
				tempIcon = addAlien(tempIcon, (Alien)lifeform); 
			}
		}
		else if (lifeform != null && lifeform.getCurrentLifePoints() == 0)
		{
			//remove dead lifeforms from the game
			e.removeLifeForm(row, col); 
		}
		
		//add any weapons
		if(weapon1 != null)
		{
			tempIcon = addFirstWeapon(tempIcon, weapon1.getNumAttachments()); 
		}
		
		if(weapon2 != null)
		{
			tempIcon = addSecondWeapon(tempIcon, weapon2.getNumAttachments()); 
		}
		
		//repaint the cell
		mapArray[row][col].setIcon(tempIcon);
		
		//if the cell if focus has changed, update that into
		if (row == mapFocusRow && col == mapFocusCol)
		{
			Object temp = new Object(); //for Action event
			ActionEvent refocus = new ActionEvent(temp, 0, "cell: "+row+" "+col); 
			actionPerformed(refocus); 
		}
	}
	
 public static void main(String[] args)
    {
    	try {
			GUI gui = new GUI();
			e.addObserver(gui);
			InvokerBuilder builder = new InvokerBuilder(); 
			Invoker inv = builder.loadCommands(); 
		} catch (RecoveryRateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }


}

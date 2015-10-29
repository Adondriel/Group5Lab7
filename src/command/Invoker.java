/**
 * 
 */
package command;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import environment.Environment;

/**
 * @author Dr. Alice Armstrong
 * This is the GUI for the user controls
 * This class creates the button layout and provides methods to load commands into the buttons
 * This class also responds when buttons are pushed by executing and loaded command
 */
public class Invoker extends JFrame implements ActionListener {
	
	JPanel turnAndMovePanel, weaponsPanel; 
	
	//movement buttons
	JButton north, south, east, west, move; 
	
	//weapons & attack buttons
	JButton drop, get1, get2, attack, reload; 
	
	//Button Commands
	Command northCommand, southCommand, eastCommand, westCommand, moveCommand;
	Command dropCommand, get1Command, get2Command, attackCommand, reloadCommand; 
	
	//Environment for commands
	//really this is a work around, and not an ideal one. 
	//if this panel were part of the MapGUI, then we wouldn't need to couple the Environment with this GUI
	Environment e; 
	
	public Invoker()
	{
		//since the Invoker is built after the GUI initializes the Environment, this is OK
		//sloppy, but OK
		e = Environment.getEnvironment(1, 1); 
		
		Color direction = new Color(102, 166, 100); 
		//set overall layout
		this.setLayout(new BorderLayout()); 
		
		//create the move and turn buttons
		turnAndMovePanel = new JPanel(); 
		turnAndMovePanel.setLayout(new BorderLayout());
		north = new JButton("north"); 
		north.setBackground(direction);
		north.addActionListener(this);
		turnAndMovePanel.add("North", north); 
		
		south = new JButton("south"); 
		south.setBackground(direction);
		south.addActionListener(this);
		turnAndMovePanel.add("South", south); 
		
		west = new JButton("west"); 
		west.setBackground(direction);
		west.addActionListener(this);
		turnAndMovePanel.add("West", west); 
		
		east = new JButton("east"); 
		east.setBackground(direction);
		east.addActionListener(this);
		turnAndMovePanel.add("East", east); 
		
		move = new JButton("MOVE"); 
		move.addActionListener(this);
		turnAndMovePanel.add("Center", move); 
		
		this.add("Center", turnAndMovePanel); 
		
		weaponsPanel = new JPanel(); 
		weaponsPanel.setLayout(new GridLayout(0, 1)); 
		drop = new JButton("Drop Weapon"); 
		drop.addActionListener(this);
		weaponsPanel.add(drop); 
		
		get1 = new JButton("Get Weapon 1"); 
		get1.addActionListener(this);
		weaponsPanel.add(get1); 
		
		get2 = new JButton("Get Weapon 2"); 
		get2.addActionListener(this);
		weaponsPanel.add(get2); 
		
		reload = new JButton("Reload"); 
		reload.addActionListener(this);
		weaponsPanel.add(reload); 
		
		this.add("East", weaponsPanel); 
		
		attack = new JButton("ATTACK!");
		attack.addActionListener(this);
		this.add("West", attack); 
		
		pack();
        setVisible(true);
		
	}
	
	//command setting methods
	public void setNorth(Command cmd)
	{
		northCommand = cmd; 
	}
	
	public void setSouth(Command cmd)
	{
		southCommand = cmd; 
	}
	
	public void setWest(Command cmd)
	{
		westCommand = cmd; 
	}

	public void setEast(Command cmd)
	{
		eastCommand = cmd; 
	}
	
	public void setDrop(Command cmd)
	{
		dropCommand = cmd; 
	}
	
	public void setGet1(Command cmd)
	{
		get1Command = cmd; 
	}
	
	public void setGet2(Command cmd)
	{
		get2Command = cmd; 
	}
	
	public void setAttack(Command cmd)
	{
		attackCommand = cmd; 
	}
	public void setMove(Command cmd)
	{
		moveCommand = cmd; 
	}
	
	public void setReload(Command cmd)
	{
		reloadCommand = cmd; 
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == north)
	    {
	        northCommand.execute(e.focusRow, e.focusCol);
	    }
		else if (event.getSource() == south)
	    {
	        southCommand.execute(e.focusRow, e.focusCol);
	    }
		else if (event.getSource() == east)
	    {
	        eastCommand.execute(e.focusRow, e.focusCol);
	    }
		else if (event.getSource() == west)
	    {
	        westCommand.execute(e.focusRow, e.focusCol);
	    }
		else if (event.getSource() == move)
	    {
	        moveCommand.execute(e.focusRow, e.focusCol);
	    }
		else if (event.getSource() == drop)
	    {
	        dropCommand.execute(e.focusRow, e.focusCol);
	    }
		else if (event.getSource() == get1)
	    {
	        get1Command.execute(e.focusRow, e.focusCol);
	    }
		else if (event.getSource() == get2)
	    {
	        get2Command.execute(e.focusRow, e.focusCol);
	    }
		else if (event.getSource() == attack)
	    {
	        attackCommand.execute(e.focusRow, e.focusCol);
	    }
		else if (event.getSource() == reload)
	    {
	        reloadCommand.execute(e.focusRow, e.focusCol);
	    }
		
	}

}

/**
 * 
 */
package command;

/**
 * @author Dr. Alice Armstrong
 * Creates a separate GUI window that contains the user controls for the game
 * this GUI is separate from the map GUI to facilitate group work. 
 */
public class InvokerBuilder {
	
	public Invoker loadCommands()
	{
		//create the Invoker GUI
		Invoker inv = new Invoker(); 
		
		//set the Invoker's Commands
		inv.setAttack(new AttackCmd());
		inv.setNorth(new TurnNorthCmd());
		inv.setSouth(new TurnSouthCmd());
		inv.setEast(new TurnEastCmd());
		inv.setWest(new TurnWestCmd());
		inv.setMove(new MoveCmd());
		inv.setGet1(new Acquire1Cmd());
		inv.setGet2(new Acquire2Cmd());
		inv.setDrop(new DropCmd());
		inv.setReload(new ReloadCmd());
		
		//done!
		return inv; 
		
	}

}

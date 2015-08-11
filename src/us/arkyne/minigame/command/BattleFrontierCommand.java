package us.arkyne.minigame.command;

import us.arkyne.server.ArkyneMain;
import us.arkyne.server.command.Command;
import us.arkyne.server.command.CommandExecutor;

public class BattleFrontierCommand implements CommandExecutor
{
	public static String[] commandNames = new String[] { "battlefrontier", "bf" };
	
	private ArkyneMain main;
	
	public BattleFrontierCommand()
	{
		main = ArkyneMain.getInstance();
	}
	
	public boolean battleFrontierCommand(Command command)
	{
		
		
		return false;
	}
}
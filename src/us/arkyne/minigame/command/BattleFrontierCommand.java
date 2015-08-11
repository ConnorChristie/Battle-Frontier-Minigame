package us.arkyne.minigame.command;

import us.arkyne.server.ArkyneMain;
import us.arkyne.server.command.Command;
import us.arkyne.server.command.CommandExecutor;

public class BattleFrontierCommand implements CommandExecutor
{
	public static String[] commandNames = new String[] { "battleFrontier", "bf" };
	
	private ArkyneMain main;
	
	public BattleFrontierCommand()
	{
		main = ArkyneMain.getInstance();
	}
	
	public boolean battleFrontierCommand(Command command)
	{
		if (command.isSubCommandMessageIfError("createlobby", 1, false, "First select the boundry's with worldedit", "Then stand where you you want the spawn point to be", "Then execute: /{cmd} createlobby <id>"))
		{
			
			
			return true;
		}
		
		return false;
	}
}
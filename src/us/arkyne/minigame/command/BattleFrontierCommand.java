package us.arkyne.minigame.command;

import org.bukkit.ChatColor;
import org.bukkit.World;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

import us.arkyne.minigame.BattleFrontier;
import us.arkyne.minigame.MinigameMain;
import us.arkyne.minigame.inventory.InventoryPreset;
import us.arkyne.minigame.message.SignMessagePreset;
import us.arkyne.server.ArkyneMain;
import us.arkyne.server.command.Command;
import us.arkyne.server.command.CommandExecutor;
import us.arkyne.server.game.Game;
import us.arkyne.server.util.Cuboid;

public class BattleFrontierCommand implements CommandExecutor
{
	public static String[] commandNames = new String[] { "battlefrontier", "bf" };
	
	private BattleFrontier bf;
	
	public BattleFrontierCommand()
	{
		bf = MinigameMain.getInstance().getBattleFrontier();
	}
	
	public boolean battlefrontierCommand(Command command)
	{
		if (command.isSubCommandMessageIfError("creategame", 0, false, "Usage: /{cmd} creategame"))
		{
			int id = bf.createGame();
			
			if (id != -1)
			{
				command.sendSenderMessage("Successfully created a new game, ID: " + ChatColor.AQUA + id, ChatColor.GREEN);
				command.sendSenderMessage("Next create a lobby with: " + ChatColor.AQUA + "/{cmd} createlobby " + id, ChatColor.GREEN);
			} else
			{
				command.sendSenderMessage("Could not create a new game", ChatColor.RED);
			}
			
			return true;
		} else if (command.isSubCommandMessageIfError("createlobby", 0, false, "First select the boundry's with worldedit", "Then stand where you you want the spawn point to be", "Then execute: /{cmd} createlobby"))
		{
			try
			{
				Selection selection = ArkyneMain.getInstance().getWorldEdit().getSelection(command.getPlayer().getOnlinePlayer());
				
				if (selection != null && selection instanceof CuboidSelection)
				{
					Cuboid cuboid = new Cuboid((World) selection.getWorld(), selection.getNativeMinimumPoint(), selection.getNativeMaximumPoint());
					
					boolean created = bf.setLobby(command.getPlayer().getLocation(), cuboid, InventoryPreset.BF_LOBBY, SignMessagePreset.BF_LOBBY);
					
					if (created)
					{
						command.sendSenderMessage("Successfully created the minigame lobby!", ChatColor.GREEN);
					} else
					{
						command.sendSenderMessage("There is already a lobby setup for this minigame!", ChatColor.RED);
					}
				} else
				{
					command.sendSenderMessage("First select the boundry's with worldedit", ChatColor.RED);
					command.sendSenderMessage("Then stand where you want the spawn point to be", ChatColor.RED);
					command.sendSenderMessage("Then execute: /{cmd} createlobby <id>", ChatColor.RED);
				}
			} catch (NumberFormatException e)
			{
				command.sendSenderMessage("Invalid ID entered, Usage: /{cmd} createlobby", ChatColor.RED);
			}
			
			return true;
		} else if (command.isSubCommandMessageIfError("creategamelobby", 1, false, "First select the boundry's with worldedit", "Then stand where you you want the spawn point to be", "Then execute: /{cmd} creategamelobby <gameid>"))
		{
			try
			{
				int id = Integer.parseInt(command.getArg(0));
				
				Game game = bf.getGameHandler().getGame(id);
				
				if (game != null)
				{
					Selection selection = ArkyneMain.getInstance().getWorldEdit().getSelection(command.getPlayer().getOnlinePlayer());
					
					if (selection != null && selection instanceof CuboidSelection)
					{
						Cuboid cuboid = new Cuboid((World) selection.getWorld(), selection.getNativeMinimumPoint(), selection.getNativeMaximumPoint());
						
						boolean created = game.createPregameLobby(command.getPlayer().getLocation(), cuboid, InventoryPreset.BF_PREGAME_LOBBY, SignMessagePreset.BF_PREGAME_LOBBY);
						
						if (created)
						{
							command.sendSenderMessage("Successfully created the pregame lobby!", ChatColor.GREEN);
						} else
						{
							command.sendSenderMessage("There is already a pregame lobby setup for this game!", ChatColor.RED);
						}
					} else
					{
						command.sendSenderMessage("First select the boundry's with worldedit", ChatColor.RED);
						command.sendSenderMessage("Then stand where you want the spawn point to be", ChatColor.RED);
						command.sendSenderMessage("Then execute: /{cmd} createlobby <id>", ChatColor.RED);
					}
				} else
				{
					command.sendSenderMessage("No game found with that ID", ChatColor.RED);
				}
			} catch (NumberFormatException e)
			{
				command.sendSenderMessage("Invalid ID entered, Usage: /{cmd} creategamelobby <gameid>", ChatColor.RED);
			}
			
			return true;
		}
		
		return command.wasArgLengthError();
	}
}
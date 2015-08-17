package us.arkyne.minigame.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.World;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

import us.arkyne.minigame.BattleFrontier;
import us.arkyne.minigame.MinigameMain;
import us.arkyne.minigame.game.BFArena;
import us.arkyne.minigame.game.ArenaMap;
import us.arkyne.minigame.inventory.InventoryPreset;
import us.arkyne.minigame.message.SignMessagePreset;
import us.arkyne.server.ArkyneMain;
import us.arkyne.server.command.Command;
import us.arkyne.server.command.CommandExecutor;
import us.arkyne.server.game.arena.Arena;
import us.arkyne.server.game.Game;
import us.arkyne.server.util.Cuboid;

public class BattleFrontierCommand implements CommandExecutor
{
	public static String[] commandNames = new String[] { "battlefrontier", "bf" };
	
	private BattleFrontier bf;
	
	private Map<Integer, ArenaMap> gameTypes = new HashMap<Integer, ArenaMap>();
	
	public BattleFrontierCommand()
	{
		bf = MinigameMain.getInstance().getBattleFrontier();
	}
	
	public boolean battlefrontierCommand(Command command)
	{
		if (command.isSubCommandMessageIfError("creategame", 1, false, "Usage: /{cmd} creategame <map>"))
		{
			//TODO: Create game with game type
			
			ArenaMap gameType = ArenaMap.valueOf(command.getArg(0).toUpperCase());
			
			int id = bf.createGame(gameType.getMapName());
			
			if (id != -1)
			{
				command.sendSenderMessage("Successfully created a new game, ID: " + ChatColor.AQUA + id, ChatColor.GREEN);
				command.sendSenderMessage("Next create a lobby with: " + ChatColor.AQUA + "/{cmd} creategamelobby " + id, ChatColor.GREEN);
				
				gameTypes.put(id, gameType);
			} else
			{
				command.sendSenderMessage("Could not create a new game", ChatColor.RED);
			}
			
			return true;
		} else if (command.isSubCommandMessageIfError("createlobby", 0, false, "First select the boundry's with worldedit", "Then stand where you you want the spawn point to be", "Then execute: /{cmd} createlobby"))
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
				command.sendSenderMessage("Then execute: /{cmd} createlobby", ChatColor.RED);
			}
			
			return true;
		} else if (command.isSubCommandMessageIfError("game", 1, true, "/{cmd} game <id> set <lobby|arena|spawn> [args]"))
		{
			try
			{
				int id = Integer.parseInt(command.getArg(0));
				
				Game game = bf.getGameHandler().getGame(id);
				
				if (game != null)
				{
					String subCommand = command.getArg(1);
					
					if (subCommand.equalsIgnoreCase("set"))
					{
						if (gameTypes.containsKey(id))
						{
							String toSet = command.getArg(2);
							
							if (toSet.equalsIgnoreCase("lobby") || toSet.equalsIgnoreCase("arena"))
							{
								Selection selection = ArkyneMain.getInstance().getWorldEdit().getSelection(command.getPlayer().getOnlinePlayer());
								
								if (selection != null && selection instanceof CuboidSelection)
								{
									Cuboid cuboid = new Cuboid((World) selection.getWorld(), selection.getNativeMinimumPoint(), selection.getNativeMaximumPoint());
									
									ArenaMap gameType = gameTypes.get(id);
									boolean created = false;
									
									if (toSet.equalsIgnoreCase("lobby"))
									{
										created = game.createPregameLobby(command.getPlayer().getLocation(), cuboid, gameType.getPregameInventory(), SignMessagePreset.BF_GAME);
									} else
									{
										created = game.createArena(new BFArena(game, cuboid, gameType.getGameInventory()));
									}
									
									if (created)
									{
										command.sendSenderMessage("Successfully created the " + toSet + "!", ChatColor.GREEN);
									} else
									{
										command.sendSenderMessage("There is already a " + toSet + " setup for this game!", ChatColor.RED);
									}
								} else
								{
									command.sendSenderMessage("Please select a region with worldedit before setting the " + toSet, ChatColor.RED);
								}
							} else if (toSet.equalsIgnoreCase("spawn"))
							{
								Arena arena = game.getArena();
								
								if (arena != null)
								{
									arena.addSpawn(command.getArg(3), command.getPlayer().getLocation());
									
									command.sendSenderMessage("Successfully set the spawn for the " + command.getArg(3) + " team!", ChatColor.GREEN);
								} else
								{
									command.sendSenderMessage("Please create an arena before setting the spawns", ChatColor.RED);
								}
							} else
							{
								command.sendSenderMessage("Usage: /{cmd} game " + id + " set <lobby|arena|spawn> [args]", ChatColor.RED);
							}
						} else
						{
							command.sendSenderMessage("Could not find a game with that ID", ChatColor.RED);
							command.sendSenderMessage("Are you sure you just created a game?", ChatColor.RED);
						}
					}
					
					game.save();
				} else
				{
					command.sendSenderMessage("Could not find a game with that ID", ChatColor.RED);
				}
				
				command.sendSenderOptionalMessage("Usage: /{cmd} game <id> <command> [args]", ChatColor.RED);
			} catch (NumberFormatException e)
			{
				command.sendSenderMessage("Could not find a game with that ID", ChatColor.RED);
			}
			
			return true;
		}
		
		return command.wasArgLengthError();
	}
}
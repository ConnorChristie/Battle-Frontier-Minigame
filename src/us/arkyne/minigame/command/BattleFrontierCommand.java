package us.arkyne.minigame.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

import us.arkyne.minigame.BattleFrontier;
import us.arkyne.minigame.MinigameMain;
import us.arkyne.minigame.game.BFGame;
import us.arkyne.minigame.game.arena.BFMap;
import us.arkyne.minigame.game.arena.BFArena;
import us.arkyne.minigame.game.team.BFArenaTeam;
import us.arkyne.minigame.inventory.InventoryPreset;
import us.arkyne.minigame.message.SignMessagePreset;
import us.arkyne.server.ArkyneMain;
import us.arkyne.server.command.Command;
import us.arkyne.server.command.CommandExecutor;
import us.arkyne.server.game.arena.Arena;
import us.arkyne.server.game.team.ArenaTeam;
import us.arkyne.server.game.Game;
import us.arkyne.server.util.Cuboid;

public class BattleFrontierCommand implements CommandExecutor
{
	public static String[] commandNames = new String[] { "battlefrontier", "bf" };
	
	private BattleFrontier bf;
	
	private Map<Integer, BFMap> arenaMaps = new HashMap<Integer, BFMap>();
	
	public BattleFrontierCommand()
	{
		bf = MinigameMain.getInstance().getBattleFrontier();
	}
	
	public boolean battlefrontierCommand(Command command)
	{
		if (command.isSenderPlayer(true))
		{
			if (command.isSubCommandMessageIfError("creategame", 1, false, "Usage: /{cmd} creategame <arenaId>"))
			{
				try
				{
					int id = Integer.parseInt(command.getArg(1));
					
					Arena arena = bf.getArenaHandler().getArena(id);
					
					if (arena != null)
					{
						BFGame game = bf.createGame(arena);
						
						if (game != null)
						{
							command.sendSenderMessage("Successfully created a new game, ID: " + game.getId(), ChatColor.GREEN);
							command.sendSenderMessage("Now set the pre-game lobby's bounds by using world edit", ChatColor.AQUA);
							command.sendSenderMessage("Then execute: /{cmd} game " + game.getId() + " set lobby", ChatColor.AQUA);
							
							World gameWorld = game.getArena().getWorld(game);
							Location tpLoc = new Location(gameWorld, 0, gameWorld.getHighestBlockYAt(0, 0), 0);
							
							command.getPlayer().teleport(tpLoc);
							
							//TODO: Create a pre-game lobby maybe in the arena class?
							//TODO: Teleport to game arena, tell them to set a pre-game lobby
						}
					}
				} catch (NumberFormatException e)
				{
					command.sendSenderMessage("Could not find an arena with that ID", ChatColor.RED);
				}
				
				/*
				ArenaMap arenaMap = ArenaMap.valueOf(command.getArg(1).toUpperCase());
				
				Game game = bf.createGame(arenaMap.getMapName(), arenaMap.getWorldName());
				
				if (game != null)
				{
					command.sendSenderMessage("Successfully created a new game, ID: " + ChatColor.AQUA + game.getId(), ChatColor.GREEN);
					command.sendSenderMessage("Next create a lobby with: " + ChatColor.AQUA + "/{cmd} game " + game.getId() + " set lobby", ChatColor.GREEN);
					
					
					
					arenaMaps.put(game.getId(), arenaMap);
				} else
				{
					command.sendSenderMessage("Could not create a new game", ChatColor.RED);
				}
				*/
				
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
			} else if (command.isSubCommandMessageIfError("createarena", 1, false, "Usage: /{cmd} createarena <map>"))
			{
				BFMap map = BFMap.valueOf(command.getArg(1).toUpperCase());
				
				if (map != null)
				{
					BFArena arena = bf.createArena(map.getMapName(), map.getWorldName());
					Location tpLoc = new Location(arena.getWorld(), 0, arena.getWorld().getHighestBlockYAt(0, 0), 0);
					
					command.getPlayer().teleport(tpLoc);
					
					command.sendSenderMessage("Successfully created a new arena, ID: " + arena.getId(), ChatColor.GREEN);
					command.sendSenderMessage("Now set the arena's bounds by using world edit", ChatColor.AQUA);
					command.sendSenderMessage("Then execute: /{cmd} arena " + arena.getId() + " set bounds", ChatColor.AQUA);
				} else
				{
					command.sendSenderMessage("Could not find a map with that name", ChatColor.RED);
					command.sendSenderMessage("Maps: " + Arrays.toString(BFMap.values()).replace("[", "").replace("]", ""), ChatColor.RED);
				}
				
				return true;
			} else if (command.isSubCommandMessageIfError("arena", 1, true, "/{cmd} arena <id> set <bounds> [args]"))
			{
				try
				{
					int id = Integer.parseInt(command.getArg(1));
					
					Arena arena = bf.getArenaHandler().getArena(id);
					
					if (arena != null)
					{
						String subCommand = command.getArg(2);
						
						if (subCommand.equalsIgnoreCase("set"))
						{
							String toSet = command.getArg(3);
							
							if (toSet.equalsIgnoreCase("bounds"))
							{
								Selection selection = ArkyneMain.getInstance().getWorldEdit().getSelection(command.getPlayer().getOnlinePlayer());
								
								if (selection != null && selection instanceof CuboidSelection)
								{
									Cuboid cuboid = new Cuboid((World) selection.getWorld(), selection.getNativeMinimumPoint(), selection.getNativeMaximumPoint());
									
									arena.setBounds(cuboid);
									arena.save();
									
									command.sendSenderMessage("Successfully set the arena's bounds!", ChatColor.GREEN);
									command.sendSenderMessage("Now stand where one of the spawns will be", ChatColor.AQUA);
									command.sendSenderMessage("Then execute: /{cmd} arena " + arena.getId() + " set spawn <teamName>", ChatColor.AQUA);
								} else
								{
									command.sendSenderMessage("Please select a region with worldedit before setting the " + toSet, ChatColor.RED);
								}
							} else if (toSet.equalsIgnoreCase("spawn"))
							{
								if (command.getArgs().length == 5)
								{
									String teamName = command.getArg(4);
									
									arena.addTeam(teamName, command.getPlayer().getLocation());
									arena.save();
									
									ItemStack coreCreator = new ItemStack(Material.BLAZE_ROD, 1);
									
									ItemMeta meta = coreCreator.getItemMeta();
									List<String> lore = new ArrayList<String>();
									
									lore.add(ChatColor.GOLD + "Arena: " + ChatColor.AQUA + arena.getId());
									lore.add(ChatColor.GOLD + "Team: " + ChatColor.AQUA + teamName);
									
									meta.setLore(lore);
									meta.setDisplayName(ChatColor.GOLD + "Core for " + ChatColor.AQUA + teamName + ChatColor.GOLD + " team");
									
									coreCreator.setItemMeta(meta);
									
									command.getPlayer().getOnlinePlayer().getInventory().addItem(coreCreator);
									
									command.sendSenderMessage("Successfully set the spawn for: " + teamName, ChatColor.GREEN);
									command.sendSenderMessage("Next, set the core for this team, to do so:", ChatColor.AQUA);
									command.sendSenderMessage("Right click the ground, with this rod, where you want the core to be!", ChatColor.AQUA);
								}
							}
						}
					}
				} catch (NumberFormatException e)
				{
					command.sendSenderMessage("Could not find an arena with that ID", ChatColor.RED);
				}
				
				return true;
			} else if (command.isSubCommandMessageIfError("game", 1, true, "/{cmd} game <id> set <lobby|arena|spawn> [args]"))
			{
				try
				{
					int id = Integer.parseInt(command.getArg(1));
					
					Game game = bf.getGameHandler().getGame(id);
					
					if (game != null)
					{
						String subCommand = command.getArg(2);
						
						if (subCommand.equalsIgnoreCase("set"))
						{
							if (arenaMaps.containsKey(id))
							{
								String toSet = command.getArg(3);
								
								if (toSet.equalsIgnoreCase("lobby") || toSet.equalsIgnoreCase("arena"))
								{
									Selection selection = ArkyneMain.getInstance().getWorldEdit().getSelection(command.getPlayer().getOnlinePlayer());
									
									if (selection != null && selection instanceof CuboidSelection)
									{
										Cuboid cuboid = new Cuboid((World) selection.getWorld(), selection.getNativeMinimumPoint(), selection.getNativeMaximumPoint());
										
										BFMap arenaMap = arenaMaps.get(id);
										boolean created = false;
										
										if (toSet.equalsIgnoreCase("lobby"))
										{
											created = game.createPregameLobby(command.getPlayer().getLocation(), cuboid, arenaMap.getPregameInventory(), SignMessagePreset.BF_GAME);
										} else if (toSet.equalsIgnoreCase("arena"))
										{
											//TODO: Pass an arena ID to the game
											
											if (command.getArgs().length == 5)
											{
												//created = game.createArena(new BFArena(bf, game, cuboid));
											} else
											{
												command.sendSenderMessage("Usage: /{cmd} game " + game.getId() + " set map <map>", ChatColor.RED);
											}
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
								} else if (toSet.equalsIgnoreCase("spawn") && command.getArgs().length == 5)
								{
									Arena arena = game.getArena();
									
									if (arena != null)
									{
										arena.addTeam(command.getArg(4), command.getPlayer().getLocation());
										
										command.sendSenderMessage("Successfully set the spawn for the " + command.getArg(4) + " team!", ChatColor.GREEN);
										command.sendSenderMessage("Right click with this rod to set the core", ChatColor.GREEN);
										command.sendSenderMessage("Then execute: /{cmd} game " + id + " set core " + command.getArg(4), ChatColor.GREEN);
										
										command.getPlayer().getOnlinePlayer().getInventory().addItem(new ItemStack(Material.BLAZE_ROD, 1));
									} else
									{
										command.sendSenderMessage("Please create an arena before setting the spawns", ChatColor.RED);
									}
								} else if (toSet.equalsIgnoreCase("core") && command.getArgs().length == 5)
								{
									Arena arena = game.getArena();
									
									if (arena != null)
									{
										ArenaTeam team = arena.getTeam(command.getArg(4));
										
										if (team != null)
										{
											((BFArenaTeam) team).setCore(bf.getCoreLocation(command.getPlayer()));
											
											command.sendSenderMessage("Successfully set the core for the " + command.getArg(4) + " team!", ChatColor.GREEN);
										} else
										{
											command.sendSenderMessage("Please create a team before setting the core's", ChatColor.RED);
										}
									} else
									{
										command.sendSenderMessage("Please create an arena before setting the core's", ChatColor.RED);
									}
								} else
								{
									command.sendSenderMessage("Usage: /{cmd} game " + id + " set <lobby|arena|spawn|core> [args]", ChatColor.RED);
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
		
		return true;
	}
}
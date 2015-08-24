package us.arkyne.minigame;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import us.arkyne.minigame.command.BattleFrontierCommand;
import us.arkyne.minigame.event.EventListener;
import us.arkyne.minigame.game.BFGame;
import us.arkyne.minigame.game.arena.BFArena;
import us.arkyne.server.game.arena.Arena;
import us.arkyne.server.minigame.Minigame;
import us.arkyne.server.player.ArkynePlayer;

public class BattleFrontier extends Minigame
{
	private Map<ArkynePlayer, Location> coreLocations = new HashMap<ArkynePlayer, Location>();
	
	public BattleFrontier()
	{
		super(MinigameMain.getInstance(), "BattleFrontier", "BF");
		
		//Try not to use this method
	}
	
	@Override
	public void onLoad()
	{
		super.onLoad();
		
		new EventListener();
		
		getMain().getCommandHandler().registerCommand(MinigameMain.getInstance(), BattleFrontierCommand.class);
	}

	@Override
	public void onUnload()
	{
		getMain().getCommandHandler().unregisterCommand(BattleFrontierCommand.class);
		
		super.onUnload();
	}

	@SuppressWarnings("unchecked")
	public BFGame createGame(Arena arena)
	{
		int id = getGameHandler().getNextId();
		
		if (!getGameHandler().containsGame(id))
		{
			BFGame game = new BFGame(this, arena, id);
			
			getGameHandler().addGame(game);
			getGameHandler().save(game);
			
			return game;
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public BFArena createArena(String mapName, String worldName)
	{
		int id = getArenaHandler().getNextId();
		
		if (!getArenaHandler().containsArena(id))
		{
			BFArena arena = new BFArena(this, id, mapName, worldName);
			
			getArenaHandler().addArena(arena);
			getArenaHandler().save(arena);
			
			return arena;
		}
		
		return null;
	}
	
	public void addCoreLocation(ArkynePlayer player, Location coreLocation)
	{
		coreLocations.put(player, coreLocation);
	}
	
	public Location getCoreLocation(ArkynePlayer player)
	{
		Location core = coreLocations.get(player);
		coreLocations.remove(player);
		
		return core;
	}
}
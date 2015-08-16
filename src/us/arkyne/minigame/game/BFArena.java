package us.arkyne.minigame.game;

import java.util.Map;

import org.bukkit.Location;

import us.arkyne.server.game.arena.Arena;
import us.arkyne.server.game.Game;
import us.arkyne.server.inventory.Inventory;
import us.arkyne.server.player.ArkynePlayer;
import us.arkyne.server.util.Cuboid;

public class BFArena extends Arena
{
	//TODO: This just contains the bounds, spawn, inventory and regen method
	
	public BFArena(Game game, Cuboid cuboid, Inventory inventory)
	{
		super(game, cuboid, inventory);
	}
	
	public void onLoad()
	{
		super.onLoad();
	}
	
	public void onUnload()
	{
		super.onUnload();
	}
	
	public Location getSpawn(ArkynePlayer player)
	{
		return spawns.get(player.getExtra("team").toString());
	}
	
	public BFArena(Map<String, Object> map)
	{
		super(map);
	}
	
	public Map<String, Object> serialize()
	{
		Map<String, Object> map = super.serialize();
		
		return map;
	}
}
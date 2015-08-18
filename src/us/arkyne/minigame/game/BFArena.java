package us.arkyne.minigame.game;

import java.util.Map;

import org.bukkit.Location;

import us.arkyne.server.game.arena.Arena;
import us.arkyne.server.game.Game;
import us.arkyne.server.player.ArkynePlayer;
import us.arkyne.server.util.Cuboid;

public class BFArena extends Arena
{
	public BFArena(Game game, Cuboid cuboid)
	{
		super(game, cuboid);
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
		return getTeam(player.getExtra("team").toString()).getSpawn();
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
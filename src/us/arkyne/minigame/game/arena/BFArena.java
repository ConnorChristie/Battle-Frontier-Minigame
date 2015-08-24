package us.arkyne.minigame.game.arena;

import java.util.Map;

import org.bukkit.Location;

import us.arkyne.minigame.game.team.BFArenaTeam;
import us.arkyne.server.game.arena.Arena;
import us.arkyne.server.minigame.Minigame;

public class BFArena extends Arena
{
	public BFArena(Minigame minigame, int id, String mapName, String worldName)
	{
		super(minigame, id, mapName, worldName);
	}
	
	public void onLoad()
	{
		super.onLoad();
	}
	
	public void onUnload()
	{
		super.onUnload();
	}
	
	public void addTeam(String team, Location spawn)
	{
		teams.add(new BFArenaTeam(this, team, spawn));
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
package us.arkyne.minigame.game;

import java.util.Map;

import org.bukkit.Location;

import us.arkyne.server.game.arena.Arena;
import us.arkyne.server.game.team.ArkyneTeam;
import us.arkyne.server.minigame.Minigame;
import us.arkyne.server.player.ArkynePlayer;

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
		teams.add(new BFTeam(this, team, spawn));
	}
	
	public Location getSpawn(ArkynePlayer player)
	{
		return ((ArkyneTeam) player.getExtra("team")).getSpawn();
	}
	
	public BFTeam getTeamFromCore(Location core)
	{
		for (ArkyneTeam t : teams)
		{
			BFTeam team = (BFTeam) t;
			
			if (core.distance(team.getCore()) < 1)
			{
				return team;
			}
		}
		
		return null;
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
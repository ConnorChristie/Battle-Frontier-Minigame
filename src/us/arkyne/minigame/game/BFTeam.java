package us.arkyne.minigame.game;

import java.util.Map;

import org.bukkit.Location;

import us.arkyne.server.game.arena.Arena;
import us.arkyne.server.game.team.ArkyneTeam;

public class BFTeam extends ArkyneTeam
{
	private Location core;
	
	public BFTeam(Arena arena, String teamName, Location spawn)
	{
		super(arena, teamName, spawn);
	}
	
	public void setCore(Location core)
	{
		this.core = core;
	}
	
	public Location getCore()
	{
		return core;
	}
	
	public BFTeam(Map<String, Object> map)
	{
		super(map);
		
		core = (Location) map.get("core");
	}
	
	public Map<String, Object> serialize()
	{
		Map<String, Object> map = super.serialize();
		
		map.put("core", core);
		
		return map;
	}
}
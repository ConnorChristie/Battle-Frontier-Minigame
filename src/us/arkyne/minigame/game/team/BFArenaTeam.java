package us.arkyne.minigame.game.team;

import java.util.Map;

import org.bukkit.Location;

import us.arkyne.server.game.arena.Arena;
import us.arkyne.server.game.team.ArenaTeam;

public class BFArenaTeam extends ArenaTeam
{
	private Location core;
	
	public BFArenaTeam(Arena arena, String teamName, Location spawn)
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
	
	public BFArenaTeam(Map<String, Object> map)
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
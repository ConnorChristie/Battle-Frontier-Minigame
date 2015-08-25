package us.arkyne.minigame.game;

import org.bukkit.Location;

import us.arkyne.minigame.game.team.BFArenaTeam;
import us.arkyne.minigame.game.team.BFGameTeam;
import us.arkyne.server.game.Game;
import us.arkyne.server.game.GameArena;
import us.arkyne.server.game.arena.Arena;
import us.arkyne.server.game.team.ArenaTeam;
import us.arkyne.server.game.team.GameTeam;

public class BFGameArena extends GameArena
{
	public BFGameArena(Game game, Arena arena)
	{
		super(game, arena);
	}
	
	public BFGameTeam getTeamFromCore(Location core)
	{
		for (GameTeam t : getTeams())
		{
			BFGameTeam team = (BFGameTeam) t;
			
			if (core.distance(((BFArenaTeam) team.getTeam()).getCore()) < 1)
			{
				return team;
			}
		}
		
		return null;
	}
	
	protected GameTeam getTeam(ArenaTeam team)
	{
		return new BFGameTeam(game, team);
	}
}
package us.arkyne.minigame.game;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import us.arkyne.minigame.MinigameMain;
import us.arkyne.minigame.message.SignMessagePreset;
import us.arkyne.server.game.Game;
import us.arkyne.server.game.status.GameSubStatus;
import us.arkyne.server.game.status.IGameSubStatus;
import us.arkyne.server.minigame.Minigame;
import us.arkyne.server.player.ArkynePlayer;

public class BFGame extends Game
{
	private Random random = new Random();
	
	private int spawnRadius = 2;
	
	public BFGame(Minigame minigame, int id, String mapName)
	{
		super(minigame, id, 20, 2, mapName, SignMessagePreset.BF_GAME);
	}
	
	public void onLoad()
	{
		super.onLoad();
		
		minigame = MinigameMain.getInstance().getBattleFrontier();
	}
	
	public void onUnload()
	{
		super.onUnload();
	}
	
	protected void onGameStart()
	{
		spawnPlayers();
	}
	
	protected void onGameEnd()
	{
		
	}
	
	public void onPlayerDeath(ArkynePlayer player, DamageCause cause)
	{
		sendPlayersMessage(ChatColor.GOLD + player.getOnlinePlayer().getName() + ChatColor.RED + " has died from " + cause.toString(), ChatColor.RED);
		
		player.teleport(getArena().getSpawn(player.getExtra("team").toString()));
	}
	
	protected IGameSubStatus getGameSubStatus(GameSubStatus status)
	{
		return GameSubStatusPreset.valueOf(status.name());
	}

	protected void onStatusChange(GameSubStatus status)
	{
		
	}
	
	private void spawnPlayers()
	{
		List<String> teams = getArena().getTeams();
		
		int teamIndex = 0;
		
		for (ArkynePlayer player : players)
		{
			String team = teams.get(teamIndex);
			
			Location loc = getArena().getSpawn(team).clone();
			loc.add(random.nextInt(spawnRadius * 2) - spawnRadius, 0, random.nextInt(spawnRadius * 2) - spawnRadius);
			
			player.setExtra("team", team);
			player.teleport(loc);
			
			//Just go back and forth between all the teams, evenly distribute players
			if (teamIndex < teams.size() - 1) teamIndex++;
			else teamIndex = 0;
		}
	}
	
	public BFGame(Map<String, Object> map)
	{
		super(map);
	}
	
	public Map<String, Object> serialize()
	{
		Map<String, Object> map = super.serialize();
		
		return map;
	}
}
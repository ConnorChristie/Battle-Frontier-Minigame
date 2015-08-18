package us.arkyne.minigame.game;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;

import us.arkyne.minigame.MinigameMain;
import us.arkyne.minigame.inventory.InventoryPreset;
import us.arkyne.minigame.inventory.item.Kit.Tier;
import us.arkyne.minigame.inventory.item.WarriorKit;
import us.arkyne.minigame.message.SignMessagePreset;
import us.arkyne.server.game.Game;
import us.arkyne.server.game.status.GameSubStatus;
import us.arkyne.server.game.status.IGameSubStatus;
import us.arkyne.server.game.team.ArkyneTeam;
import us.arkyne.server.inventory.Inventory;
import us.arkyne.server.minigame.Minigame;
import us.arkyne.server.player.ArkynePlayer;

public class BFGame extends Game
{
	private Random random = new Random();
	
	private int spawnRadius = 2;
	
	public BFGame(Minigame minigame, int id, String mapName, String worldName)
	{
		super(minigame, id, mapName, worldName, SignMessagePreset.BF_GAME);
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
		//Display message and what not
	}
	
	public int getMinPlayers()
	{
		return 2;
	}
	
	public int getMaxPlayers()
	{
		return 20;
	}
	
	public void onPlayerDamage(ArkynePlayer player, EntityDamageEvent event)
	{
		if (event instanceof EntityDamageByEntityEvent)
		{
			EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;
			
			if (damageEvent.getDamager() instanceof HumanEntity)
			{
				ItemStack item = ((HumanEntity) damageEvent.getDamager()).getItemInHand();
				
				if (item.getType() == Material.NETHER_STAR)
				{
					event.setDamage(10);
				}
			}
		}
	}
	
	public void onPlayerDeath(ArkynePlayer player, ArkynePlayer killer)
	{
		boolean coreDestroyed = true;
		
		if (coreDestroyed)
		{
			player.getOnlinePlayer().setGameMode(GameMode.SPECTATOR);
		} else
		{
			//player.teleport(getArena().getSpawn(player.getExtra("team").toString()));
		}
	}
	
	public void onCloneTargetChange(ArkynePlayer player, EntityTargetLivingEntityEvent event)
	{
		if (gameSubStatus == GameSubStatus.GAME_PLAYING)
		{
			//First check original target
			
			if (event.getTarget() instanceof Player)
			{
				ArkynePlayer target = getMain().getArkynePlayerHandler().getPlayer((Player) event.getTarget());
				
				if (target != null && target.getJoinable() != null && target.getJoinable().equals(this))
				{
					if (!player.getExtra("team").toString().equalsIgnoreCase(target.getExtra("team").toString()))
					{
						//Target is good and not on the same team
						
						return;
					}
				}
			}
			
			double shortestDistance = 10;
			ArkynePlayer closestPlayer = null;
			
			for (ArkynePlayer p : players)
			{
				if (p.getOnlinePlayer().getGameMode() == GameMode.SURVIVAL && !player.getExtra("team").toString().equalsIgnoreCase(p.getExtra("team").toString()))
				{
					if (((LivingEntity) event.getEntity()).hasLineOfSight(p.getOnlinePlayer()))
					{
						double distance = event.getEntity().getLocation().distance(p.getLocation());
						
						if (distance < shortestDistance)
						{
							shortestDistance = distance;
							closestPlayer = p;
						}
					}
				}
			}
			
			if (closestPlayer != null && closestPlayer.isOnline())
			{
				event.setTarget(closestPlayer.getOnlinePlayer());
			} else
			{
				event.setCancelled(true);
			}
		}
	}
	
	protected IGameSubStatus getGameSubStatus(GameSubStatus status)
	{
		return GameSubStatusPreset.valueOf(status.name());
	}

	protected void onStatusChange(GameSubStatus status)
	{
		
	}
	
	protected boolean canPvP()
	{
		return true;
	}
	
	protected Inventory getPlayerInventory(ArkynePlayer player)
	{
		if (player.hasExtra("inventory"))
		{
			return (Inventory) player.getExtra("inventory");
		}
		
		return InventoryPreset.BF_LOBBY;
	}
	
	private void spawnPlayers()
	{
		List<ArkyneTeam> teams = getArena().getTeams();
		
		int teamIndex = 0;
		
		for (ArkynePlayer player : players)
		{
			player.setExtra("inventory", new WarriorKit(Tier.PRO));
			player.updateInventory();
			
			String team = teams.get(teamIndex).getTeamName();
			
			Location loc = getArena().getTeam(team).getSpawn().clone();
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
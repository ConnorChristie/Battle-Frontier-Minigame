package us.arkyne.minigame.game;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

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
import us.arkyne.server.scoreboard.ArkyneScoreboard;

public class BFGame extends Game
{
	private Runnable fireworkRunnable;
	
	private int fireworks = 0;
	
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
		// Display message and what not
		
		for (ArkynePlayer p : players)
		{
			p.sendMessage("You got " + (int) p.getExtra("kills") + " kills that game!", ChatColor.GREEN);
			
			if (p.isOnline() && (int) p.getExtra("kills") > 0)
			{
				fireworkRunnable = () ->
				{
					Firework fw = (Firework) p.getOnlinePlayer().getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
					FireworkMeta fwm = fw.getFireworkMeta();
					
					Random r = new Random();
					
					// Get the type
					int rt = r.nextInt(3) + 1;
					FireworkEffect.Type type = FireworkEffect.Type.BALL;
					
					if (rt == 1)
						type = FireworkEffect.Type.BALL;
					if (rt == 2)
						type = FireworkEffect.Type.BALL_LARGE;
					if (rt == 3)
						type = FireworkEffect.Type.BURST;
					if (rt == 4)
						type = FireworkEffect.Type.STAR;
						
					// Get our random colours
					int r1i = r.nextInt(17) + 1;
					int r2i = r.nextInt(17) + 1;
					
					Color c1 = getColor(r1i);
					Color c2 = getColor(r2i);
					
					// Create our effect with this
					FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(c1).withFade(c2).with(type).trail(true).build();
					
					// Then apply the effect to the meta
					fwm.addEffect(effect);
					
					// Generate some random power and set it
					int rp = r.nextInt(2) + 1;
					fwm.setPower(rp);
					
					// Then apply this to our rocket
					fw.setFireworkMeta(fwm);
					
					fireworks++;
					
					if (fireworks <= 10) Bukkit.getScheduler().runTaskLater(getMain(), fireworkRunnable, 8);
				};
				
				Bukkit.getScheduler().runTask(getMain(), fireworkRunnable);
			}
		}
	}
	
	private Color getColor(int i)
	{
		Color c = null;
		
		if (i == 1)
		{
			c = Color.AQUA;
		}
		if (i == 2)
		{
			c = Color.BLACK;
		}
		if (i == 3)
		{
			c = Color.BLUE;
		}
		if (i == 4)
		{
			c = Color.FUCHSIA;
		}
		if (i == 5)
		{
			c = Color.GRAY;
		}
		if (i == 6)
		{
			c = Color.GREEN;
		}
		if (i == 7)
		{
			c = Color.LIME;
		}
		if (i == 8)
		{
			c = Color.MAROON;
		}
		if (i == 9)
		{
			c = Color.NAVY;
		}
		if (i == 10)
		{
			c = Color.OLIVE;
		}
		if (i == 11)
		{
			c = Color.ORANGE;
		}
		if (i == 12)
		{
			c = Color.PURPLE;
		}
		if (i == 13)
		{
			c = Color.RED;
		}
		if (i == 14)
		{
			c = Color.SILVER;
		}
		if (i == 15)
		{
			c = Color.TEAL;
		}
		if (i == 16)
		{
			c = Color.WHITE;
		}
		if (i == 17)
		{
			c = Color.YELLOW;
		}
		
		return c;
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
			// player.teleport(getArena().getSpawn(player.getExtra("team").toString()));
		}
	}
	
	public void onCloneTargetChange(ArkynePlayer player, EntityTargetLivingEntityEvent event)
	{
		if (gameSubStatus == GameSubStatus.GAME_PLAYING)
		{
			// First check original target
			
			if (event.getTarget() instanceof Player)
			{
				ArkynePlayer target = getMain().getArkynePlayerHandler().getPlayer((Player) event.getTarget());
				
				if (target != null && target.getJoinable() != null && target.getJoinable().equals(this))
				{
					if (!player.getExtra("team").toString().equalsIgnoreCase(target.getExtra("team").toString()))
					{
						// Target is good and not on the same team
						
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
			
			teams.get(teamIndex).joinTeam(player);
			
			// Just go back and forth between all the teams, evenly distribute
			// players
			if (teamIndex < teams.size() - 1)
				teamIndex++;
			else
				teamIndex = 0;
		}
		
		for (ArkynePlayer p : players)
		{
			p.getOnlinePlayer().setPlayerListName(p.getOnlinePlayer().getName());
			
			Scoreboard sb = ((ArkyneScoreboard) p.getExtra("scoreboard")).getScoreboard();
			
			for (ArkyneTeam t : getArena().getTeams())
			{
				Team team = sb.registerNewTeam(t.getTeamName());
				
				for (ArkynePlayer tp : t.getPlayers())
				{
					team.addEntry(tp.getOnlinePlayer().getName());
				}
				
				team.setPrefix(t.getColor());
				team.setNameTagVisibility(NameTagVisibility.ALWAYS);
			}
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
package us.arkyne.minigame.event;

import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

import us.arkyne.minigame.BattleFrontier;
import us.arkyne.minigame.MinigameMain;
import us.arkyne.minigame.game.BFArena;
import us.arkyne.minigame.game.BFGame;
import us.arkyne.minigame.game.BFTeam;
import us.arkyne.server.ArkyneMain;
import us.arkyne.server.player.ArkynePlayer;

public class EventListener implements Listener
{
	private BattleFrontier bf;
	
	public EventListener()
	{
		bf = MinigameMain.getInstance().getBattleFrontier();
		
		bf.getPlugin().getServer().getPluginManager().registerEvents(this, bf.getPlugin());
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event)
	{
		ArkynePlayer player = ArkyneMain.getInstance().getArkynePlayerHandler().getPlayer(event.getPlayer());
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Block block = event.getClickedBlock();
			
			if (event.getItem() != null && event.getItem().getType() == Material.BLAZE_ROD && player.isManager())
			{
				List<String> lore = event.getItem().getItemMeta().getLore();
				
				if (lore != null)
				{
					BFArena arena = (BFArena) bf.getArenaHandler().getArena(Integer.parseInt(ChatColor.stripColor(lore.get(0)).replace("Arena: ", "")));
					
					if (arena != null)
					{
						BFTeam team = (BFTeam) arena.getTeam(ChatColor.stripColor(lore.get(1)).replace("Team: ", ""));
						
						if (team != null)
						{
							Location coreLocation = block.getLocation().add(0.5, 1, 0.5);
							
							block.getLocation().getWorld().spawnEntity(coreLocation, EntityType.ENDER_CRYSTAL);
							block.getLocation().getWorld().save();
							
							team.setCore(coreLocation);
						} else
						{
							player.sendMessage("The team this rod is attached to, no longer exists", ChatColor.RED);
						}
					} else
					{
						player.sendMessage("The arena this rod is attached to, no longer exists", ChatColor.RED);
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamage(EntityDamageEvent event)
	{
		if (event.getEntityType() == EntityType.ENDER_CRYSTAL)
		{
			event.setCancelled(true);
			
			if (event instanceof EntityDamageByEntityEvent)
			{
				EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) event;
				
				if (damageEvent.getDamager() instanceof Player)
				{
					ArkynePlayer player = ArkyneMain.getInstance().getArkynePlayerHandler().getPlayer((Player) damageEvent.getDamager());
					
					if (player.getJoinable() != null && player.getJoinable() instanceof BFGame)
					{
						BFGame game = (BFGame) player.getJoinable();
						BFTeam team = game.getArena().getTeamFromCore(event.getEntity().getLocation());
						
						if (team != null)
						{
							System.out.println("That is their core: " + team.getTeamName());
						}
					} else if (player.isManager() && player.getOnlinePlayer().getGameMode() == GameMode.CREATIVE)
					{
						event.getEntity().remove();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityTargetLivingEntity(EntityTargetLivingEntityEvent event)
	{
		if (event.getEntityType() == EntityType.ZOMBIE)
		{
			Zombie zombie = (Zombie) event.getEntity();
			
			List<MetadataValue> meta = zombie.getMetadata("ZombieClone");
			List<MetadataValue> meta1 = zombie.getMetadata("CloneOwner");
			
			if (meta != null && meta1 != null && meta.size() > 0 && meta1.size() > 0)
			{
				MetadataValue val = meta.get(0);
				MetadataValue val1 = meta1.get(0);
				
				if (val.value() instanceof Boolean && val1.value() instanceof String)
				{
					ArkynePlayer cloneOwner = ArkyneMain.getInstance().getArkynePlayerHandler().getPlayer(UUID.fromString(val1.asString()));
					
					if (val.asBoolean() && cloneOwner != null)
					{
						if (cloneOwner.getJoinable() != null && cloneOwner.getJoinable() instanceof BFGame)
						{
							((BFGame) cloneOwner.getJoinable()).onCloneTargetChange(cloneOwner, event);
						} else
						{
							zombie.setHealth(0);
						}
					}
				}
			}
		}
	}
}
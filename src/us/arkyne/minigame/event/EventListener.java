package us.arkyne.minigame.event;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.metadata.MetadataValue;

import us.arkyne.minigame.BattleFrontier;
import us.arkyne.minigame.MinigameMain;
import us.arkyne.minigame.game.BFGame;
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
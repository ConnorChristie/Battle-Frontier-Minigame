package us.arkyne.minigame.event;

import org.bukkit.event.Listener;

import us.arkyne.minigame.BattleFrontier;
import us.arkyne.minigame.MinigameMain;

public class EventListener implements Listener
{
	private BattleFrontier bf;
	
	public EventListener()
	{
		bf = MinigameMain.getInstance().getBattleFrontier();
		
		bf.getPlugin().getServer().getPluginManager().registerEvents(this, bf.getPlugin());
	}
	
	
}
package us.arkyne.minigame;

import org.bukkit.plugin.Plugin;

import us.arkyne.server.ArkyneMain;
import us.arkyne.server.plugin.MinigamePlugin;

public class MinigameMain extends MinigamePlugin
{
	private static MinigameMain main;
	
	public void onEnable()
	{
		main = this;
		
		ArkyneMain.getInstance().getMinigameHandler().registerMinigame(new BattleFrontier());
	}
	
	public ArkyneMain getArkyneMain()
	{
		Plugin plugin = getServer().getPluginManager().getPlugin("ArkyneServerPlugin");
		
		if (plugin == null || !(plugin instanceof ArkyneMain)) return null;
		
		return (ArkyneMain) plugin;
	}
	
	public static MinigameMain getInstance()
	{
		return main;
	}
}
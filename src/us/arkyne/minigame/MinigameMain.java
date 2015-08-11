package us.arkyne.minigame;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;

import us.arkyne.minigame.game.BFGame;
import us.arkyne.server.ArkyneMain;
import us.arkyne.server.plugin.MinigamePlugin;

public class MinigameMain extends MinigamePlugin
{
	private static MinigameMain main;
	
	private BattleFrontier bf;
	
	public void onEnable()
	{
		main = this;
		
		ConfigurationSerialization.registerClass(BFGame.class);
		
		bf = new BattleFrontier();
		
		ArkyneMain.getInstance().getMinigameHandler().registerMinigame(bf);
	}
	
	public ArkyneMain getArkyneMain()
	{
		Plugin plugin = getServer().getPluginManager().getPlugin("ArkyneServerPlugin");
		
		if (plugin == null || !(plugin instanceof ArkyneMain)) return null;
		
		return (ArkyneMain) plugin;
	}
	
	public BattleFrontier getBattleFrontier()
	{
		return bf;
	}
	
	public static MinigameMain getInstance()
	{
		return main;
	}
}
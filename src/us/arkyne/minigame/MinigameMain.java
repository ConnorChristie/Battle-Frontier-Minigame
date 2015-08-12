package us.arkyne.minigame;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;

import us.arkyne.minigame.game.BFGame;
import us.arkyne.minigame.inventory.InventoryPreset;
import us.arkyne.server.ArkyneMain;
import us.arkyne.server.inventory.InventoryHandler;
import us.arkyne.server.plugin.MinigamePlugin;

public class MinigameMain extends MinigamePlugin
{
	private static MinigameMain main;
	
	private BattleFrontier bf;
	
	public void onEnable()
	{
		main = this;
		bf = new BattleFrontier();
		
		ConfigurationSerialization.registerClass(BFGame.class);
		InventoryHandler.registerInventoryPresets(InventoryPreset.values());
		
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
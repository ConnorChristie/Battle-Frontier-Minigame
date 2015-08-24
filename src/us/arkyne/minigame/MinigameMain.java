package us.arkyne.minigame;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.Plugin;

import us.arkyne.minigame.game.BFGame;
import us.arkyne.minigame.game.team.BFArenaTeam;
import us.arkyne.minigame.inventory.InventoryItemPreset;
import us.arkyne.minigame.inventory.InventoryPreset;
import us.arkyne.minigame.message.SignMessagePreset;
import us.arkyne.server.ArkyneMain;
import us.arkyne.server.inventory.handler.InventoryHandler;
import us.arkyne.server.inventory.handler.InventoryItemHandler;
import us.arkyne.server.message.SignMessageHandler;
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
		ConfigurationSerialization.registerClass(BFArenaTeam.class);
		
		InventoryHandler.registerInventoryPresets(InventoryPreset.values());
		InventoryItemHandler.registerInventoryItemPresets(InventoryItemPreset.values());
		
		SignMessageHandler.registerSignMessagePresets(SignMessagePreset.values());
		
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
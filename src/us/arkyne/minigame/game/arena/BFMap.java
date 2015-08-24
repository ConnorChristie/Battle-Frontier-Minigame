package us.arkyne.minigame.game.arena;

import us.arkyne.minigame.inventory.InventoryPreset;
import us.arkyne.server.inventory.Inventory;

public enum BFMap
{
	HEAVEN_HELL("Heaven & Hell", "Battle_Frontier_Heaven_Hell", InventoryPreset.BF_PREGAME_LOBBY);
	
	private String mapName;
	private String worldName;
	
	private Inventory pregame;
	
	private BFMap(String mapName, String worldName, Inventory pregame)
	{
		this.mapName = mapName;
		this.worldName = worldName;
		
		this.pregame = pregame;
	}
	
	public String getMapName()
	{
		return mapName;
	}
	
	public String getWorldName()
	{
		return worldName;
	}

	public Inventory getPregameInventory()
	{
		return pregame;
	}
}
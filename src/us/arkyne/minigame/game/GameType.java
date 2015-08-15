package us.arkyne.minigame.game;

import us.arkyne.minigame.inventory.InventoryPreset;
import us.arkyne.server.inventory.Inventory;

public enum GameType
{
	HEAVEN_HELL("Heaven & Hell", InventoryPreset.BF_PREGAME_LOBBY, InventoryPreset.BF_GAME);
	
	private String mapName;
	
	private Inventory pregame;
	private Inventory game;
	
	private GameType(String mapName, Inventory pregame, Inventory game)
	{
		this.mapName = mapName;
		
		this.pregame = pregame;
		this.game = game;
	}
	
	public String getMapName()
	{
		return mapName;
	}

	public Inventory getPregameInventory()
	{
		return pregame;
	}

	public Inventory getGameInventory()
	{
		return game;
	}
}
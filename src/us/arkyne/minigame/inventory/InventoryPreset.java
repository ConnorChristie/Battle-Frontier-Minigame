package us.arkyne.minigame.inventory;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.Material.NETHER_STAR;
import static org.bukkit.Material.REDSTONE_BLOCK;

import us.arkyne.server.ArkyneMain;
import us.arkyne.server.inventory.Inventory;
import us.arkyne.server.inventory.Item;
import us.arkyne.server.player.ArkynePlayer;

public enum InventoryPreset implements Inventory
{
	//Arrays have to be exactly 36 deep
	
	//TODO: Exit item has to exit to minigame lobby, not main lobby
	
	PREGAME_LOBBY(new Item[] {
			/* Hotbar */ Item.i(NETHER_STAR, AQUA + "Pick a Class"), null, null, null, null, null, null, null, Item.i(REDSTONE_BLOCK, RED + "Exit to Main Lobby", (player) -> player.changeLobby(ArkyneMain.getInstance().getLobbyHandler().getMainLobby())),
			/* Row 1 */ null, null, null, null, null, null, null, null, null,
			/* Row 2 */ null, null, null, null, null, null, null, null, null,
			/* Row 3 */ null, null, null, null, null, null, null, null, null,
	});
	
	private Item[] items;
	
	private InventoryPreset(Item[] items)
	{
		this.items = items;
	}
	
	public void updateInventory(ArkynePlayer player)
	{
		player.getOnlinePlayer().getInventory().setContents(items);
	}
	
	public Item getItem(int index)
	{
		return items[index];
	}
	
	public Item[] getItems()
	{
		return items;
	}
}
package us.arkyne.minigame.inventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.arkyne.server.ArkyneMain;
import us.arkyne.server.inventory.InventoryClick;
import us.arkyne.server.inventory.item.InventoryItem;

public enum InventoryItemPreset implements InventoryItem
{
	EXIT_TO_MAINLOBBY(ChatColor.RED + "Exit to Main Lobby", Material.REDSTONE_BLOCK, (player) -> ArkyneMain.getInstance().getMainLobbyHandler().getMainLobby().join(player));
	
	private ItemStack item;
	private InventoryClick inventoryClick;
	
	private InventoryItemPreset(String displayName, Material material, InventoryClick inventoryClick)
	{
		this.item = new ItemStack(material, 1);
		this.inventoryClick = inventoryClick;
		
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(displayName);
		item.setItemMeta(meta);
	}

	public ItemStack getItem()
	{
		return item;
	}

	public InventoryClick getInventoryClick()
	{
		return inventoryClick;
	}
}
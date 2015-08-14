package us.arkyne.minigame.inventory;

import org.bukkit.inventory.ItemStack;

import us.arkyne.server.inventory.Inventory;
import us.arkyne.server.inventory.item.InventoryItem;
import us.arkyne.server.player.ArkynePlayer;

public enum InventoryPreset implements Inventory
{
	//Arrays have to be exactly 36 deep
	
	//TODO: Change item[] to InventoryItem[], have to create an interface for the enum as well
	
	BF_LOBBY(new InventoryItem[] {
			/* Hotbar */ us.arkyne.server.inventory.item.InventoryItemPreset.DUMMY_ITEM, null, null, null, null, null, null, null, InventoryItemPreset.EXIT_TO_MAINLOBBY,
			/* Row 1 */ null, null, null, null, null, null, null, null, null,
			/* Row 2 */ null, null, null, null, null, null, null, null, null,
			/* Row 3 */ null, null, null, null, null, null, null, null, null
	}),
	BF_PREGAME_LOBBY(new InventoryItem[] {
			/* Hotbar */ us.arkyne.server.inventory.item.InventoryItemPreset.DUMMY_ITEM, null, null, null, null, null, null, null, InventoryItemPreset.EXIT_TO_MAINLOBBY,
			/* Row 1 */ null, null, null, null, null, null, null, null, null,
			/* Row 2 */ null, null, null, null, null, null, null, null, null,
			/* Row 3 */ null, null, null, null, null, null, null, null, null,
	});
	
	private InventoryItem[] items;
	
	private InventoryPreset(InventoryItem[] items)
	{
		this.items = items;
	}
	
	public void updateInventory(ArkynePlayer player)
	{
		ItemStack[] itemStacks = new ItemStack[items.length];
		
		for (int i = 0; i < items.length; i++)
		{
			itemStacks[i] = items[i] != null ? items[i].getItem() : null;
		}
		
		player.getOnlinePlayer().getInventory().setContents(itemStacks);
	}
	
	public InventoryItem getItem(int index)
	{
		return items[index];
	}

	@Override
	public InventoryItem[] getItems()
	{
		return items;
	}
}
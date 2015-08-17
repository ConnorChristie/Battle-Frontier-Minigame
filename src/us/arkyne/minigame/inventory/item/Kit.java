package us.arkyne.minigame.inventory.item;

import org.bukkit.ChatColor;

import us.arkyne.minigame.inventory.item.KitItem.Type;
import us.arkyne.server.inventory.Inventory;
import us.arkyne.server.inventory.item.InventoryItem;

public abstract class Kit implements Inventory
{
	private KitItem[] items = new KitItem[Type.values().length];
	
	private InventoryItem[] invItems = new InventoryItem[36];
	private InventoryItem[] armorItems = new InventoryItem[4];
	
	protected KitItem k(Type type, Tier tier)
	{
		items[type.ordinal()] = KitItem.k(type, this, tier);
		
		return items[type.ordinal()];
	}
	
	protected KitItem t(Type type)
	{
		return items[type.ordinal()];
	}
	
	protected void updateInventory()
	{
		invItems = new InventoryItem[] {
				t(Type.WEAPON), null, null, null, t(Type.ABILITY), null, null, null, null,
				
				null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null,
		};
		
		armorItems = new InventoryItem[] {
				t(Type.HELMET), t(Type.CHESTPLATE), t(Type.LEGGINGS), t(Type.BOOTS)
		};
	}
	
	public InventoryItem[] getItems()
	{
		return invItems;
	}
	
	public InventoryItem[] getArmor()
	{
		return armorItems;
	}
	
	public InventoryItem getItem(int index)
	{
		return invItems[index];
	}
	
	public InventoryItem getArmor(int index)
	{
		return armorItems[index];
	}
	
	public static enum Tier
	{
		STARTER(ChatColor.GRAY + "Starter"),
		NORMAL(ChatColor.GRAY + "Normal"),
		PRO(ChatColor.GRAY + "Pro"),
		LEGEND(ChatColor.GRAY + "Legend");
		
		private String name;
		
		private Tier(String name)
		{
			this.name = name;
		}
		
		public String getName()
		{
			return name;
		}
	}
}
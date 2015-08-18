package us.arkyne.minigame.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import us.arkyne.server.ArkyneMain;
import us.arkyne.server.inventory.InventoryClick;
import us.arkyne.server.inventory.item.InventoryItem;

public enum InventoryItemPreset implements InventoryItem
{
	EXIT_MINIGAME(ChatColor.RED + "Exit to Main Lobby", Material.REDSTONE_BLOCK, (player) -> ArkyneMain.getInstance().getMainLobbyHandler().getMainLobby().join(player)),
	EXIT_GAME(ChatColor.RED + "Exit to Minigame Lobby", Material.REDSTONE_BLOCK, (player) -> { if (player.getJoinable() != null) player.getJoinable().leave(player); });
	
	//TODO: Set a timer, you can only use this item every so often, also enchantment sparkle
	
	private ItemStack item;
	private InventoryClick inventoryClick;
	
	private double attack;
	private double defense;
	
	private InventoryItemPreset(String displayName, Material material, InventoryClick inventoryClick)
	{
		this(displayName, material, new String[0], 0, 0, inventoryClick);
	}
	
	private InventoryItemPreset(String displayName, Material material, String[] lore, InventoryClick inventoryClick)
	{
		this(displayName, material, lore, 0, 0, inventoryClick);
	}
	
	private InventoryItemPreset(String displayName, Material material, String[] lore, double attack, double defense)
	{
		this(displayName, material, lore, attack, defense, null);
	}
	
	private InventoryItemPreset(String displayName, Material material, String[] lore, double attack, double defense, InventoryClick inventoryClick)
	{
		this.item = new ItemStack(material, 1);
		this.inventoryClick = inventoryClick;
		
		this.attack = attack;
		this.defense = defense;
		
		ItemMeta meta = item.getItemMeta();
		List<String> loreList = new ArrayList<String>(Arrays.asList(lore));
		
		if (attack != 0)
			loreList.add(ChatColor.GOLD + "Attack: " + attack);
		if (defense != 0)
			loreList.add(ChatColor.GOLD + "Defense: " + defense);
			
		meta.setDisplayName(displayName);
		meta.setLore(loreList);
		
		//meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		
		net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = nmsStack.getTag();
		
		if (compound == null)
		{
			compound = new NBTTagCompound();
			nmsStack.setTag(compound);
		}
		
		NBTTagList modifiers = new NBTTagList();
		NBTTagCompound healthboost = new NBTTagCompound();
		
		healthboost.set("AttributeName", new NBTTagString("generic.attackDamage"));
		healthboost.set("Name", new NBTTagString("generic.attackDamage"));
		healthboost.set("Amount", new NBTTagInt(20));
		healthboost.set("Operation", new NBTTagInt(0));
		healthboost.set("UUIDLeast", new NBTTagInt(894654));
		healthboost.set("UUIDMost", new NBTTagInt(2872));
		
		modifiers.add(healthboost);
		
		compound.set("AttributeModifiers", modifiers);
		nmsStack.setTag(compound);
		
		item = CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public ItemStack getItem()
	{
		return item;
	}
	
	public InventoryClick getInventoryClick()
	{
		return inventoryClick;
	}
	
	@Override
	public double getAttack()
	{
		return attack;
	}
	
	@Override
	public double getDefense()
	{
		return defense;
	}
}
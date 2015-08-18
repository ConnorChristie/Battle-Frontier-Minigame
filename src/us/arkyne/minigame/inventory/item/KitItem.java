package us.arkyne.minigame.inventory.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import us.arkyne.minigame.inventory.item.Kit.Tier;
import us.arkyne.nms.item.GlowingItem;
import us.arkyne.server.inventory.InventoryClick;
import us.arkyne.server.inventory.item.InventoryItem;

public class KitItem implements InventoryItem
{
	private Kit kit;
	
	private Type type;
	private Tier tier;
	
	private ItemStack item;
	private InventoryClick inventoryClick;
	
	private String[] lore;
	
	private double attack;
	private double defense;
	
	public static KitItem k(Type type, Kit kit, Tier tier)
	{
		return new KitItem(type, kit, tier);
	}
	
	public KitItem name(String name)
	{
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(kit.name() + " " + name);
		item.setItemMeta(meta);
		
		return this;
	}
	
	public KitItem material(Material material)
	{
		item.setType(material);
		
		return this;
	}
	
	public KitItem attackDefense(double attack, double defense)
	{
		this.attack = attack;
		this.defense = defense;
		
		lore(lore);
		
		return this;
	}
	
	public KitItem click(InventoryClick click)
	{
		this.inventoryClick = click;
		
		return this;
	}
	
	public KitItem glow(boolean addGlow)
	{
		item = addGlow ? GlowingItem.addGlow(item) : GlowingItem.removeGlow(item);
		
		return this;
	}
	
	public KitItem lore(String... lore)
	{
		this.lore = lore;
		
		ItemMeta meta = item.getItemMeta();
		List<String> loreList = new ArrayList<String>();
		
		loreList.add(tier.getName());
		
		if (attack != 0)
			loreList.add(ChatColor.GOLD + "Attack: " + attack);
		if (defense != 0)
			loreList.add(ChatColor.GOLD + "Defense: " + defense);
		
		if (lore != null) loreList.addAll(Arrays.asList(lore));
		
		meta.setLore(loreList);
		item.setItemMeta(meta);
		
		return this;
	}
	
	public KitItem(Type type, Kit kit, Tier tier)
	{
		this.kit = kit;
		this.type = type;
		this.tier = tier;
		
		this.item = new ItemStack(type.getMaterial(), 1);
		
		//this.attack = attack;
		//this.defense = defense;
		
		ItemMeta meta = item.getItemMeta();
		List<String> loreList = new ArrayList<String>();
		
		loreList.add(tier.getName());
		
		/*
		List<String> loreList = new ArrayList<String>(Arrays.asList(lore));
		
		if (attack != 0)
			loreList.add(ChatColor.GOLD + "Attack: " + attack);
		if (defense != 0)
			loreList.add(ChatColor.GOLD + "Defense: " + defense);
			*/
		
		meta.setDisplayName(kit.name() + " " + WordUtils.capitalize(type.name().toLowerCase()));
		//meta.setLore(loreList);
		
		//meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		meta.setLore(loreList);
		item.setItemMeta(meta);
		
		/*
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
		*/
	}
	
	public ItemStack getItem()
	{
		return item;
	}
	
	public InventoryClick getInventoryClick()
	{
		return inventoryClick;
	}
	
	public double getAttack()
	{
		return attack;
	}
	
	public double getDefense()
	{
		return defense;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public static enum Type
	{
		WEAPON( Material.IRON_SWORD),
		ABILITY(Material.BOOK),
		
		HELMET(    Material.DIAMOND_HELMET),
		CHESTPLATE(Material.IRON_CHESTPLATE),
		LEGGINGS(  Material.DIAMOND_LEGGINGS),
		BOOTS(     Material.DIAMOND_BOOTS);
		
		private Material material;
		
		private Type(Material material)
		{
			this.material = material;
		}
		
		public Material getMaterial()
		{
			return material;
		}
	}

	@Override
	public String name()
	{
		return null;
	}
}
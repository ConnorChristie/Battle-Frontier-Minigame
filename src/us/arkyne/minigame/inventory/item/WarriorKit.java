package us.arkyne.minigame.inventory.item;

import org.bukkit.ChatColor;

import us.arkyne.minigame.inventory.item.KitItem.Type;

public class WarriorKit extends Kit
{
	public WarriorKit(Tier tier)
	{
		k(Type.WEAPON,     tier).attackDefense(6, 0);
		k(Type.ABILITY,    tier).name("Clone");
		k(Type.HELMET,     tier).attackDefense(0, 10);
		k(Type.CHESTPLATE, tier).attackDefense(0, 9);
		k(Type.LEGGINGS,   tier).attackDefense(0, 12);
		k(Type.BOOTS,      tier).attackDefense(0, 10);
		
		switch (tier)
		{
			case NORMAL:
				t(Type.WEAPON).attackDefense(12, 0);
				
				t(Type.HELMET).attackDefense(0, 12);
				t(Type.CHESTPLATE).attackDefense(0, 22);
				t(Type.LEGGINGS).attackDefense(0, 17);
				t(Type.BOOTS).attackDefense(0, 7);
				
				break;
			case PRO:
				t(Type.WEAPON).attackDefense(15, 0);
				
				t(Type.HELMET).attackDefense(0, 12);
				t(Type.CHESTPLATE).attackDefense(0, 22);
				t(Type.LEGGINGS).attackDefense(0, 17);
				t(Type.BOOTS).attackDefense(0, 7);
				
				break;
			case LEGEND:
				t(Type.WEAPON).attackDefense(20, 0);
				
				t(Type.HELMET).attackDefense(0, 12);
				t(Type.CHESTPLATE).attackDefense(0, 22);
				t(Type.LEGGINGS).attackDefense(0, 17);
				t(Type.BOOTS).attackDefense(0, 7);
				
				break;
			default: break;
		}
		
		updateInventory();
	}
	
	public String name()
	{
		return ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Warrior";
	}
}
package us.arkyne.minigame.inventory.item;

import org.bukkit.ChatColor;

import us.arkyne.minigame.inventory.item.KitItem.Type;

public class WarriorKit extends Kit
{
	public WarriorKit(Tier tier)
	{
		k(Type.WEAPON,     tier).attackDefense(6, 0).name("Sword");
		k(Type.ABILITY,    tier).name("Clone").click((player) -> player.spawnClone()).lore(ChatColor.GRAY + "Right click to spawn clones").glow(true);
		k(Type.HELMET,     tier).attackDefense(0, 10);
		k(Type.CHESTPLATE, tier).attackDefense(0, 9);
		k(Type.LEGGINGS,   tier).attackDefense(0, 12);
		k(Type.BOOTS,      tier).attackDefense(0, 10);
		
		switch (tier)
		{
			case NORMAL:
				k(Type.WEAPON).attackDefense(8, 0);
				
				k(Type.HELMET).attackDefense(0, 11);
				k(Type.CHESTPLATE).attackDefense(0, 9.5);
				k(Type.LEGGINGS).attackDefense(0, 13);
				k(Type.BOOTS).attackDefense(0, 11);
				
				break;
			case PRO:
				k(Type.WEAPON).attackDefense(9, 0);
				k(Type.ABILITY).name("Clones").click((player) -> player.spawnClone());
				k(Type.HELMET).attackDefense(0, 13);
				k(Type.CHESTPLATE).attackDefense(0, 10);
				k(Type.LEGGINGS).attackDefense(0, 15);
				k(Type.BOOTS).attackDefense(0, 13);
				
				break;
			case LEGEND:
				k(Type.WEAPON).attackDefense(10, 0);
				k(Type.ABILITY).name("Clones").click((player) -> player.spawnClone());
				k(Type.HELMET).attackDefense(0, 15);
				k(Type.CHESTPLATE).attackDefense(0, 11);
				k(Type.LEGGINGS).attackDefense(0, 15);
				k(Type.BOOTS).attackDefense(0, 15);
				
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
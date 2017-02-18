package com.camlacademy.plugin.luckyblocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.camlacademy.spigot.utils.CamlListenerBase;
import com.camlacademy.spigot.utils.CamlPluginBase;

public class LuckyBlockBreakListener extends CamlListenerBase {
	
	static Map<Material, List<ItemStack>> blockMap = new HashMap<>();
	
	public static int REDSTONE_ORE_DIAMOND_SWORD_COUNT = 5;
	public static int REDSTONE_ORE_DIAMOND_SPADE_COUNT = 5;
	public static int REDSTONE_ORE_DIAMOND_HELMET_COUNT = 5;

	public LuckyBlockBreakListener(CamlPluginBase plugin) {
		super(plugin);
		
		//For REDSTONE_ORE, we will put 15 items into the blockMap.
		//we will later randomly take things out of this blockMap
		//and give it to the player as a lucky block reward.
		//We can later add different items for other blocks besides REDSTONE_ORE
		
		//REDSTONE_ORE
		blockMap.put(Material.REDSTONE_ORE, new ArrayList<>());
		for(int itemNumber = 0; itemNumber < REDSTONE_ORE_DIAMOND_SWORD_COUNT; itemNumber++){
			ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
			item.addUnsafeEnchantment(Enchantment.KNOCKBACK, itemNumber + 1);
			item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, itemNumber + 1);
			blockMap.get(Material.REDSTONE_ORE).add(item);
		}
		
		for(int itemNumber = 0; itemNumber < REDSTONE_ORE_DIAMOND_SPADE_COUNT; itemNumber++){
			ItemStack sword = new ItemStack(Material.DIAMOND_HELMET);
			sword.addUnsafeEnchantment(Enchantment.DIG_SPEED, itemNumber + 1);
			sword.addUnsafeEnchantment(Enchantment.DURABILITY, itemNumber + 1);
			blockMap.get(Material.REDSTONE_ORE).add(sword);
		}
		
		for(int itemNumber = 0; itemNumber < REDSTONE_ORE_DIAMOND_HELMET_COUNT; itemNumber++){
			ItemStack sword = new ItemStack(Material.DIAMOND_HELMET);
			sword.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, itemNumber + 1);
			sword.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, itemNumber + 1);
			blockMap.get(Material.REDSTONE_ORE).add(sword);
		}
		
		
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onBlockBreakEvent(BlockBreakEvent event) {
		
		if(event.getBlock() == null){
			return;
		}
		
		ItemStack itemInHand = plugin.getPlayerHelper().getItemInHand(event.getPlayer());
		
		if(itemInHand != null && itemInHand.containsEnchantment(Enchantment.SILK_TOUCH)){
			//we do not want to trigger lucky blocks if Silk touch is used.
			//That would lead to infinite rewards.
			return;
		}
		
		List<ItemStack> possibleItems = blockMap.get(event.getBlock().getType());
		
		if(possibleItems == null || possibleItems.isEmpty()){
			//if this happens, it means the player did not break REDSTONE_ORE
			return;
		}
		
		//if we got this far, it means the player did break REDSTONE_ORE
		
		int size = possibleItems.size();
		
		//get a random index in the list of 15 items
		int randomIndex = new Random().nextInt(size);
		
		//get a handle on the item at that random index
		ItemStack reward = possibleItems.get(randomIndex);
		
		//drop a clone of that item in the place where the block was broken.
		event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), reward.clone());
		
	}

}

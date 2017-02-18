package com.camlacademy.plugin.itemstats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.camlacademy.spigot.utils.CamlListenerBase;
import com.camlacademy.spigot.utils.CamlPluginBase;

public class ItemStatsDamageListener extends CamlListenerBase {

	public static String LORE_KEY_KILLING_BLOWS = "Killing blows: ";

	public ItemStatsDamageListener(CamlPluginBase plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerKillMob(EntityDamageByEntityEvent event) {

		if (!(event.getDamager() instanceof Player)) {
			//this must be a mob damaging something
			return;
		}

		if (!(event.getEntity() instanceof Damageable)) {
			//this feature will only work if we can detect the damage delivered
			return;
		}

		//We are hardcoded to work with these mob types
		List<EntityType> acceptedTypes = Arrays.asList(EntityType.CREEPER, EntityType.ZOMBIE, EntityType.WITCH,
				EntityType.SKELETON, EntityType.ENDERMAN);
		if (acceptedTypes.contains(event.getEntity().getType())) {
			return;
		}

		Player player = (Player) event.getDamager();
		ItemStack itemInHand = plugin.getPlayerHelper().getItemInHand(player);

		if (itemInHand == null) {
			//the player is punching a mob
			//we need an item in hand for this feature to work.
			return;
		}

		//We are hardcoded to work with these item types
		if (itemInHand.getType() != Material.WOOD_SWORD && itemInHand.getType() != Material.STONE_SWORD
				&& itemInHand.getType() != Material.IRON_SWORD && itemInHand.getType() != Material.GOLD_SWORD
				&& itemInHand.getType() != Material.DIAMOND_SWORD) {
			return;
		}
		
		Damageable enemy = (Damageable) event.getEntity();
		if (event.getFinalDamage() < enemy.getHealth()) {
			//The mob was not killed by this attack
			return;
		}
		
		//if we got this far, then the mob was killed.
		
		addKillingBlowsToStats(itemInHand);
	}

	private void addKillingBlowsToStats(ItemStack itemInHand) {
		ItemMeta itemMeta = itemInHand.getItemMeta();
		List<String> lore = itemMeta.getLore();
		List<String> newLore = new ArrayList<>();
		boolean killingBlowLoreAdded = false;
		if (lore == null) {
			//this is the first killing blow added to this sword
			newLore.add(LORE_KEY_KILLING_BLOWS + 1);
			killingBlowLoreAdded = true;
		} else {
			for (String oldLore : lore) {
				if (oldLore == null) {
					continue;
				}
				if (oldLore.startsWith(LORE_KEY_KILLING_BLOWS)) {
					//this is an pre-existing killing blow lore line
					String numberPart = oldLore
							.substring(oldLore.indexOf(LORE_KEY_KILLING_BLOWS) + LORE_KEY_KILLING_BLOWS.length());
					int killingBlows = Integer.parseInt(numberPart);
					killingBlows++;
					newLore.add(LORE_KEY_KILLING_BLOWS + killingBlows);
					killingBlowLoreAdded = true;
				} else {
					//this is not a killing blow lore line
					newLore.add(oldLore);
				}
			}
		}
		
		if(!killingBlowLoreAdded){
			//this is the first killing blow added to this sword, but thre must have been some existing lore
			newLore.add(LORE_KEY_KILLING_BLOWS + 1);
		}
		
		itemMeta.setLore(newLore);
	}
}

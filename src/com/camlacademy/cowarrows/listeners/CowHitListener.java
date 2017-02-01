package com.camlacademy.cowarrows.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import com.camlacademy.spigot.utils.CamlListenerBase;
import com.camlacademy.spigot.utils.CamlPluginBase;

public class CowHitListener extends CamlListenerBase{

	public CowHitListener(CamlPluginBase plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void playerHitsAnimalWithArrow(EntityDamageByEntityEvent event) {
		if (event.getCause() != null && event.getCause().equals(DamageCause.PROJECTILE)) {
			// the mob was hit by a projectile. Don't do anything
			return;
		}

		Player player = null;

		if (!(event.getDamager() instanceof Player)) {
			// The damager was not a player. Don't do anything
			return;
		} else {
			// cast the Damager to a Player
			player = (Player) event.getDamager();
		}
		ItemStack itemInHand = plugin.getPlayerHelper().getItemInHand(player);

		if (itemInHand == null) {
			// no item in hand. Don't do anything
			return;
		}

		if (!itemInHand.getType().equals(Material.ARROW)) {
			// The item in hand does is not an arrow. Don't do anything.
			return;
		}

		if (!event.getEntity().getType().equals(EntityType.COW)) {
			// the mob that was hit is not a cow. Don't do anything.
			return;
		}

		// If we got this far, replace the cow with an arrow named "Cow arrow"
		plugin.getPlayerHelper().replaceEntityWithNamedArrow(event.getEntity(), itemInHand, player, "Cow arrow");
	}
}

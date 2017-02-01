package com.camlacademy.cowarrows.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import com.camlacademy.spigot.utils.CamlListenerBase;
import com.camlacademy.spigot.utils.CamlPluginBase;

public class ArrowFiredListener extends CamlListenerBase{

	public ArrowFiredListener(CamlPluginBase plugin) {
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerShootEntityArrow(EntityShootBowEvent event) {

		if (event.getEntity() == null || !(event.getEntity() instanceof Player)) {
			// this was probably a skeleton. Don't do anything (unless you want
			// them to spawn cows too.
			return;
		}

		Player player = (Player) event.getEntity();

		ItemStack firstArrowStack = plugin.getPlayerHelper().getFirstArrowStack(player);

		if (firstArrowStack == null || firstArrowStack.getItemMeta() == null
				|| firstArrowStack.getItemMeta().getDisplayName() == null
				|| !firstArrowStack.getItemMeta().getDisplayName().equals("Cow arrow")) {
			// There was either no arrow found (i.e. the player fired an empty
			// bow),
			// or the arrow was not named "Cow arrow". Don't do anything
			return;
		}

		// rename the projectile entity "Cow arrow" so that when it lands we
		// know we should spawn a cow
		event.getProjectile().setCustomName("Cow arrow");
	}

}

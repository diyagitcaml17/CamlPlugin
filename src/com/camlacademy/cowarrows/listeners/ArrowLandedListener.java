package com.camlacademy.cowarrows.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.camlacademy.cowarrows.CowArrowsPluginV4;
import com.camlacademy.spigot.utils.CamlListenerBase;

public class ArrowLandedListener extends CamlListenerBase {

	private CowArrowsPluginV4 cowArrowsPlugin;

	public ArrowLandedListener(CowArrowsPluginV4 cowArrowsPlugin) {
		super(cowArrowsPlugin);
		this.cowArrowsPlugin = cowArrowsPlugin;
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onEntityArrowHit(ProjectileHitEvent event) {

		if (!(event.getEntity().getShooter() instanceof Player)) {
			// this was probably a skeleton. Don't do anything (unless you want
			// them to spawn cows too.
			return;
		}

		if (event.getEntity() == null || event.getEntity().getCustomName() == null) {
			// this arrow entity was did not have a custom name
			// Don't do anything
			return;
		}

		if (!event.getEntity().getCustomName().equals("Cow arrow")) {
			// this arrow entity was not named "Cow arrow"
			// Don't do anything
			return;
		}

		// spawn the cow
		if (cowArrowsPlugin.isSpawnMushroomCows()) {
			event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.MUSHROOM_COW);
		} else {
			event.getEntity().getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.COW);
		}

		// remove the arrow so players cant pick it up and spawn more cows for
		// free
		event.getEntity().remove();
	}
}

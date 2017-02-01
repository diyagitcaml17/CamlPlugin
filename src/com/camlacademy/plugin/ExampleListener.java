package com.camlacademy.plugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

import com.camlacademy.spigot.utils.CamlListenerBase;
import com.camlacademy.spigot.utils.CamlPluginBase;

public class ExampleListener extends CamlListenerBase {

	public ExampleListener(CamlPluginBase plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerPlaceBlock(BlockPlaceEvent event) {
		event.getPlayer().sendMessage("You placed a " + event.getBlock().getType());
	}

}

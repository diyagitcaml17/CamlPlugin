package com.camlacademy.plugin.kits;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.camlacademy.plugin.CamlPlugin;
import com.camlacademy.spigot.utils.CamlCommandExecuterBase;

public class KitCommand extends CamlCommandExecuterBase {

	private static final int COOL_DOWN_SEC = 60;

	private Map<String, Date> cooldownMap = new HashMap<>();

	public KitCommand(CamlPlugin camlPlugin, String commandName) {
		super(camlPlugin, commandName);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String title, String[] parameters) {
		// do something cool
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;

		// check when the player last ran the command
		Date lastTimeRan = null;
		if (cooldownMap.containsKey(player.getName())) {
			lastTimeRan = cooldownMap.get(player.getName());
		}

		// if lastTimeRan is not null, it mans the player has ran the command
		// before
		Date now = new Date();
		if (lastTimeRan != null) {
			long secondsSinceLastRan = (now.getTime() - lastTimeRan.getTime()) / 1000;

			if (lastTimeRan != null && secondsSinceLastRan < COOL_DOWN_SEC) {
				long secondsToWait = COOL_DOWN_SEC - secondsSinceLastRan;
				player.sendMessage("Too soon.  Wait " + secondsToWait + " second(s).");
				return true;
			}
		}

		// if we got this far, the player has either never run the command, or
		// it has been long enough.

		cooldownMap.put(player.getName(), now);

		// load up the inventory
		Inventory kitInventory = Bukkit.createInventory(null, 9, "Simple Kit");

		kitInventory.addItem(new ItemStack(Material.IRON_SWORD));
		kitInventory.addItem(new ItemStack(Material.IRON_SPADE));
		kitInventory.addItem(new ItemStack(Material.IRON_AXE));
		kitInventory.addItem(new ItemStack(Material.IRON_PICKAXE));
		kitInventory.addItem(new ItemStack(Material.IRON_HELMET));
		kitInventory.addItem(new ItemStack(Material.IRON_BOOTS));
		kitInventory.addItem(new ItemStack(Material.IRON_LEGGINGS));
		kitInventory.addItem(new ItemStack(Material.IRON_CHESTPLATE));
		kitInventory.addItem(new ItemStack(Material.TORCH));

		// open the inventory
		player.openInventory(kitInventory);

		return true;
	}

}

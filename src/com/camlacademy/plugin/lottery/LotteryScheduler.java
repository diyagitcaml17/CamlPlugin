package com.camlacademy.plugin.lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.camlacademy.spigot.utils.CamlPluginBase;


public class LotteryScheduler extends BukkitRunnable {

	private CamlPluginBase plugin;

	public LotteryScheduler(CamlPluginBase plugin){
		this.plugin = plugin;
	}

	@Override
	public void run() {
		if (Bukkit.getOnlinePlayers() == null || Bukkit.getOnlinePlayers().isEmpty()) {
			plugin.getLogger().info("No players online for lottery :(");
			return;
		}

		List<Player> players = new ArrayList<Player>(Bukkit.getOnlinePlayers());

		
		
		int size = players.size();

		int randomIndex = new Random().nextInt(size);

		Player winner = players.get(randomIndex);

		Bukkit.broadcastMessage("Lottery Winner: " + winner.getName());

		Location dropLocation = winner.getLocation().clone();

		dropLocation.setY(dropLocation.getY() + 5);

		new BukkitRunnable() {
			@Override
			public void run() {
				winner.getWorld().dropItemNaturally(dropLocation, new ItemStack(Material.DIRT));
			}
		}.runTaskLater(plugin, 20L * 3);
	}

	
}

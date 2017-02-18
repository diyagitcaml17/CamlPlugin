package com.camlacademy.plugin.teleporter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.camlacademy.plugin.CamlPlugin;
import com.camlacademy.spigot.utils.CamlListenerBase;
import com.camlacademy.spigot.utils.CamlPluginBase;

public class TeleporterListener extends CamlListenerBase {
	
	private static final int TELEPORT_COOL_DOWN_TIME_SEC = 90;
	
	private Map<String,Date> playerLastTeleportedDate = new HashMap<>();
	

	public TeleporterListener(CamlPluginBase plugin) {
		super(plugin);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onCraftItem(PrepareItemCraftEvent event) {
		
		//there must be a result
		if(event.getInventory().getResult() == null){
			return;
		}
		
		//there must match the type referred to by CamlPlugin.ITEM_TYPE_TELEPORTER
		if(event.getInventory().getResult().getType() != CamlPlugin.ITEM_TYPE_TELEPORTER){
			return;
		}
		
		//there must be items which were used to craft this
		if(event.getInventory().getMatrix() == null || event.getInventory().getMatrix().length < 0){
			return;
		}
		
		boolean wasSameItemTypeFound = false;
		for(ItemStack item : event.getInventory().getMatrix()){
			if(item != null && item.getType() == CamlPlugin.ITEM_TYPE_TELEPORTER){
				wasSameItemTypeFound = true;
				break;
			}
		}
		
		if(!wasSameItemTypeFound){
			return;
		}
		
		ItemStack result = event.getInventory().getResult();
		ItemMeta itemMeta = result.getItemMeta();
		itemMeta.setDisplayName(CamlPlugin.ITEM_NAME_TELEPORTER);
		
		List<String> lore = new ArrayList<>();
		HumanEntity player = event.getViewers().get(0);
		Location location = player.getLocation();
		String coodinates = location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
		lore.add(coodinates);
		itemMeta.setLore(lore);
		result.setItemMeta(itemMeta);
		event.getInventory().setResult(result);
	}
	
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerClickWithTeleporter(PlayerInteractEvent event) {
		ItemStack itemInHand = plugin.getPlayerHelper().getItemInHand(event.getPlayer());
		
		if(itemInHand == null || itemInHand.getItemMeta() == null){
			return;
		}
		
		if(!CamlPlugin.ITEM_NAME_TELEPORTER.equals(itemInHand.getItemMeta().getDisplayName())){
			return;
		}
		
		Date now = new Date();
		String playerName = event.getPlayer().getName();
		if(!playerCanTeleport(playerName, now)){
			event.getPlayer().sendMessage("Not yet!");
			return;
		}
		playerLastTeleportedDate.put(playerName, now);
		
		String coordinate = itemInHand.getItemMeta().getLore().get(0);
		
		String xString = coordinate.substring(0, coordinate.indexOf(","));
		String yString = coordinate.substring(coordinate.indexOf(",") + 1, coordinate.lastIndexOf(","));
		String zString = coordinate.substring(coordinate.lastIndexOf(",") + 1);
		
		int x = Integer.parseInt(xString);
		int y = Integer.parseInt(yString);
		int z = Integer.parseInt(zString);
		
		Location location = new Location(event.getPlayer().getWorld(), x, y, z);
		event.getPlayer().teleport(location);
		
	}
	
	private boolean playerCanTeleport(String playerName, Date now){
		if(!playerLastTeleportedDate.containsKey(playerName) || playerLastTeleportedDate.get(playerName) == null){
			return true;
		}
		long lastTeleportTimeMs = playerLastTeleportedDate.get(playerName).getTime();
		long nowMs = now.getTime();
		
		boolean playerCanTelport = (nowMs - lastTeleportTimeMs)/1000 > TELEPORT_COOL_DOWN_TIME_SEC;
		
		return playerCanTelport;
	}
	
	
	public static ShapedRecipe getTeleporterRecipe(){
		ItemStack teleporterItem = new ItemStack(CamlPlugin.ITEM_TYPE_TELEPORTER);
		ItemMeta teleporterMeta = teleporterItem.getItemMeta();
		teleporterMeta.setDisplayName(CamlPlugin.ITEM_NAME_TELEPORTER);
		teleporterItem.setItemMeta(teleporterMeta);

		ShapedRecipe teleporterRecipe = new ShapedRecipe(teleporterItem);
		teleporterRecipe.shape("DDD", "DCD", "DDD");

		teleporterRecipe.setIngredient('B', Material.DIRT);
		teleporterRecipe.setIngredient('C', CamlPlugin.ITEM_TYPE_TELEPORTER);
		return teleporterRecipe;
	}
}

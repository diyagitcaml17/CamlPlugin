package com.camlacademy.plugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.camlacademy.plugin.lottery.LotteryScheduler;
import com.camlacademy.plugin.teleporter.TeleporterListener;
import com.camlacademy.spigot.utils.CamlPluginBase;

public class CamlPlugin extends CamlPluginBase {

	private String exampleDataManagedByPlugin = "EXAMPLE";

	public static final String CONFIG_KEY_EXAMPLE_DATA = "example-data";
	
	public static final String ITEM_NAME_TELEPORTER = "Teleporter";
	public static final String ITEM_NAME_BOUNCY_SHOES = "Bouncy Shoes";
	public static final String ITEM_NAME_THOR = "Thor";
	
	public static final Material ITEM_TYPE_TELEPORTER = Material.COMPASS;
	public static final Material ITEM_TYPE_BOUNCY_SHOES = Material.IRON_BOOTS;
	public static final Material ITEM_TYPE_THOR = Material.BOW;
	

	@Override
	public void onEnable() {
		super.onEnable();
		registerListeners();
		registerCommands();
		registerRecipes();
		
		int delay = 5;//sec
		int period = 120;//sec
		
		new LotteryScheduler(this).runTaskTimer(this, 20L * delay, 20L * period);
	}

	private void registerListeners() {
		new ExampleListener(this);
		
		new TeleporterListener(this);
	}

	private void registerCommands() {
		new ExampleCommand(this, "example");
	}

	private void registerRecipes() {

		ItemStack itemStack = new ItemStack(Material.DIRT);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName("Example Item");
		itemStack.setItemMeta(itemMeta);

		ShapedRecipe recipe = new ShapedRecipe(itemStack);
		recipe.shape("DDD", "CCC", "DDD");

		recipe.setIngredient('B', Material.DIRT);
		recipe.setIngredient('C', Material.COBBLESTONE);

		getServer().addRecipe(recipe);
	}

	public String getExampleDataManagedByPlugin() {
		return exampleDataManagedByPlugin;
	}

	public void setExampleDataManagedByPlugin(String exampleDataManagedByPlugin) {
		this.exampleDataManagedByPlugin = exampleDataManagedByPlugin;
	}

}

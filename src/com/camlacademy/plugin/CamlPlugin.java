package com.camlacademy.plugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.camlacademy.spigot.utils.CamlPluginBase;

public class CamlPlugin extends CamlPluginBase {

	private String exampleDataManagedByPlugin = "EXAMPLE";

	public static final String CONFIG_KEY_EXAMPLE_DATA = "example-data";

	@Override
	public void onEnable() {
		super.onEnable();
		registerListeners();
		registerCommands();
		registerRecipes();
	}

	private void registerListeners() {
		new ExampleListener(this);
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
		recipe.shape("DDD", "C", "DDD");

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

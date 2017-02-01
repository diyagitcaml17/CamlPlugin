package com.camlacademy.cowarrows;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import com.camlacademy.cowarrows.commands.MushroomCowCommand;
import com.camlacademy.cowarrows.listeners.ArrowFiredListener;
import com.camlacademy.cowarrows.listeners.ArrowLandedListener;
import com.camlacademy.cowarrows.listeners.CowHitListener;
import com.camlacademy.spigot.utils.CamlPluginBase;

public class CowArrowsPluginV4 extends CamlPluginBase {

	public static final String CONFIG_KEY_ALLOW_COW_ARROW_RECIPE = "allowCowArrowRecipe";

	private boolean spawnMushroomCows = false;

	@Override
	public void onEnable() {
		super.onEnable();

		new CowHitListener(this);
		new ArrowFiredListener(this);
		new ArrowLandedListener(this);

		registerRecipes();

		new MushroomCowCommand(this, "toggleMushroomCows");
	}

	private void registerRecipes() {
		boolean allowCowArrowRecipe = getConfig().getBoolean(CONFIG_KEY_ALLOW_COW_ARROW_RECIPE);

		if (allowCowArrowRecipe) {
			getLogger().info(CONFIG_KEY_ALLOW_COW_ARROW_RECIPE + ": true");

			ItemStack itemStack = new ItemStack(Material.ARROW);
			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName("Cow arrow");
			itemStack.setItemMeta(itemMeta);

			ShapedRecipe recipe = new ShapedRecipe(itemStack);
			recipe.shape(" B ", " A ", " L ");

			recipe.setIngredient('L', Material.LEATHER);
			recipe.setIngredient('A', Material.ARROW);
			recipe.setIngredient('B', Material.RAW_BEEF);

			getServer().addRecipe(recipe);

		} else {
			getLogger().info(CONFIG_KEY_ALLOW_COW_ARROW_RECIPE + ": false");
		}
	}

	public boolean isSpawnMushroomCows() {
		return spawnMushroomCows;
	}

	public void setSpawnMushroomCows(boolean spawnMushroomCows) {
		this.spawnMushroomCows = spawnMushroomCows;
	}
}

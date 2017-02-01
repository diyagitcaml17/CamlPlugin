package com.camlacademy.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.camlacademy.spigot.utils.CamlCommandExecuterBase;

public class ExampleCommand extends CamlCommandExecuterBase {

	CamlPlugin camlPlugin;

	public ExampleCommand(CamlPlugin camlPlugin, String commandName) {
		super(camlPlugin, commandName);
		this.camlPlugin = camlPlugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String title, String[] parameters) {
		// do something cool
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		player.sendMessage("You ran the command: " + title);
		player.sendMessage("Example Data managed by plugin: " + camlPlugin.getExampleDataManagedByPlugin());
		player.sendMessage("Example Data managed in config: " + plugin.getConfig().getString(CamlPlugin.CONFIG_KEY_EXAMPLE_DATA, "[NULL]"));
		return true;
	}

}

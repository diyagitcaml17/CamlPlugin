package com.camlacademy.cowarrows.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.camlacademy.cowarrows.CowArrowsPluginV4;
import com.camlacademy.spigot.utils.CamlCommandExecuterBase;
import com.camlacademy.spigot.utils.CamlPluginBase;

public class MushroomCowCommand extends CamlCommandExecuterBase{
	
	CowArrowsPluginV4 cowArrowsPlugin;

	public MushroomCowCommand(CowArrowsPluginV4 cowArrowsPlugin, String commandName) {
		super(cowArrowsPlugin, commandName);
		this.cowArrowsPlugin = cowArrowsPlugin;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		cowArrowsPlugin.setSpawnMushroomCows(!cowArrowsPlugin.isSpawnMushroomCows());
		return true;
	}

}

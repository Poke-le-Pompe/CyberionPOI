package com.poke.cyberion.poi.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.poke.cyberion.poi.CyberionPlugin;
import com.poke.cyberion.poi.objects.ListPOI;
import com.poke.cyberion.poi.objects.POI;

public class PoiTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] arguments) {

		String[] commands = { "set", "list", "book", "setDesc", "setActivation", "setCat", "remove", "reload", "toggleBook", "info", "toggleHolo", "tp", "order", "debugHolo"};
		String[] empty = {};
		List<String> listCommands = Arrays.asList(empty);

		if (arguments.length == 1) {
			listCommands = Arrays.asList(commands);
			return listCommands;
		}

		if (arguments.length == 2) {

			switch (arguments[0].toLowerCase()) {

			case "remove":
				return listNomsPOI();
			case "setdesc":
				return listNomsPOI();
			case "setactivation":
				return listNomsPOI();
			case "setcat":
				return listNomsPOI();
			case "info":
				return listNomsPOI();
			case "toggleholo":
				return listNomsPOI();
			case "tp":
				return listNomsPOI();

			default:
				break;
			}
		}
		
		if (arguments.length == 3) {

			switch (arguments[0].toLowerCase()) {

			case "order":
				return listNomsPOI();
			default:
				break;
			}
		}

		return Arrays.asList(empty);
		
	}
	
	private List<String> listNomsPOI() {
		List<String> listName = new ArrayList<String>();
		ListPOI listPoi = CyberionPlugin.getInstance().getListPOI();
		for (POI poi : listPoi) {
			listName.add(poi.getName());
		}

		return listName;

	}
}
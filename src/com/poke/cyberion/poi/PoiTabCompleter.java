package com.poke.cyberion.poi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class PoiTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] arguments) {

		String[] commands = { "set", "list", "book", "desc", "remove", "reload", "toggleBook" };
		String[] empty = {};
		List<String> listCommands = Arrays.asList(empty);

		if (arguments.length == 1) {
			listCommands = Arrays.asList(commands);
			return listCommands;
		}

		if (arguments.length == 2) {

			switch (arguments[0]) {

			case "remove":
				return listNomsPOI();
			case "desc":
				return listNomsPOI();
			case "infos":
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
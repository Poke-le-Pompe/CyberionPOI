package com.poke.cyberion.poi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.poke.cyberion.poi.CyberionPlugin;
import com.poke.cyberion.poi.CyberionUtil;
import com.poke.cyberion.poi.ListPOI;
import com.poke.cyberion.poi.POI;

public class CPoi implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {



		if (arg.length == 0) {
			return false;
		} else {

			String[] args = new String[arg.length - 1];

			for (int i = 1, j = 0; i < arg.length; i++) {
				args[j] = arg[i];
				j++;
			}

			switch (arg[0]) {
			case "set":
				return setCommand(sender, args);
			case "remove":
				return removeCommand(sender, args);
			case "list":
				return listCommand(sender, args);
			case "reload":
				return reloadCommand(sender, args);
			case "book":
				return bookCommand(sender, args);
			case "toggleBook":
				return toggleItemBookCommand(sender, args);

			default:
				return false;
			}

		}


	}

	private boolean setCommand(CommandSender sender, String[] arg) {

		if (arg.length > 0) {

			CyberionPlugin plugin = CyberionPlugin.getInstance();

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < arg.length; i++) {
				sb.append(arg[i]).append(" ");
			}
			String allArgs = sb.toString().trim();

			// Check if the player actually has the permission required
			if (sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {
				if (sender instanceof Player) {
					Player player = (Player) sender;

					if (plugin.getListPOI().nameExist(allArgs)) {
						player.sendMessage(CyberionUtil.getMessageHeader('c') + "Ce nom de POI est déjà utilisé");
						return true;
					}

					plugin.addSetter(player, allArgs);
					player.sendMessage(CyberionUtil.getMessageHeader()
							+ "Cliquez sur le bloc que vous voulez définir en tant que POI");

					return true;
				} else {
					// Send the command sender a message telling them that only players can use this
					// command
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							CyberionPlugin.getInternalConfig().getOnlyPlayersMessage()));

					// Return false since the command was not run successfully
					return false;
				}
			} else {
				// Send the command sender a message telling them that they don't have
				// permission to use this command
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						CyberionPlugin.getInternalConfig().getNoPermMessage()));

				// Return false since the command was not run successfully
				return false;
			}
		}

		sender.sendMessage(CyberionUtil.getMessageHeader('c') + "Ajoutez un nom pour le POI");
		return false;

	}

	private boolean listCommand(CommandSender sender, String[] arg) {
		CyberionPlugin plugin = CyberionPlugin.getInstance();
		ListPOI listPoi = plugin.getListPOI();

		if (sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				player.sendMessage(CyberionUtil.getMessageHeader() + "Liste des points d'intérêts: ");

				for (POI poi : listPoi) {
					player.sendMessage("#" + listPoi.indexOf(poi) + " : " + poi.getName() + " | " + poi.getLoc().getX()
							+ " " + poi.getLoc().getY() + " " + poi.getLoc().getZ() + " ("
							+ poi.getLoc().getWorld().getName() + ")");
				}

				player.sendMessage("--------------------------------------");

				if (plugin.getVisitedList().getPlayerList(player) == null) {
					player.sendMessage("Aucun POI Visité");
				} else {

					for (String id : plugin.getVisitedList().getPlayerList(player)) {
						POI poi = listPoi.getPOIbyId(id);
						player.sendMessage("#" + listPoi.indexOf(poi) + " : " + poi.getName() + " | "
								+ poi.getLoc().getX() + " " + poi.getLoc().getY() + " " + poi.getLoc().getZ() + " ("
								+ poi.getLoc().getWorld().getName() + ")");
					}
				}


				return true;
			} else {
				// Send the command sender a message telling them that only players can use this
				// command
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						CyberionPlugin.getInternalConfig().getOnlyPlayersMessage()));

				// Return false since the command was not run successfully
				return false;
			}
		}
		return false;
	}

	private boolean bookCommand(CommandSender sender, String[] arg) {

		if (sender.hasPermission(CyberionUtil.CYBERION_USER_PERM)) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				CyberionUtil.openListBookForPlayer(player);
				return true;
			}
		}

		return false;


	}

	private boolean reloadCommand(CommandSender sender, String[] arg) {
		CyberionPlugin plugin = CyberionPlugin.getInstance();

		if (sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				plugin.getListPOI().loadConfig();

				player.sendMessage("Reload OK");

				return true;
			} else {

				plugin.getListPOI().loadConfig();

				sender.sendMessage(CyberionUtil.getMessageHeader() + "Reload OK");

				return true;
			}
		}
		return false;

	}

	private boolean removeCommand(CommandSender sender, String[] arg) {
		CyberionPlugin plugin = CyberionPlugin.getInstance();

		if (arg.length >= 1) {

			//get all args as a name
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < arg.length; i++) {
				sb.append(arg[i]).append(" ");
			}
			String allArgs = sb.toString().trim();

			// Check if the player actually has the permission required
			if (sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {

				if (sender instanceof Player) {
					Player player = (Player) sender;

					String nameDel = allArgs;

					POI success = plugin.getListPOI().remove(nameDel);

					if (success != null) {
						player.sendMessage(CyberionUtil.getMessageHeader('a') + "POI Supprimé !");
						return true;
					} else {
						player.sendMessage(
								CyberionUtil.getMessageHeader('c') + "Impossible de supprimer (POI inexistant ?)");
						return false;
					}

				} else {
					// Send the command sender a message telling them that only players can use this
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							CyberionPlugin.getInternalConfig().getOnlyPlayersMessage()));

					// Return false since the command was not run successfully
					return false;
				}
			} else {
				// Send the command sender a message telling them that they don't have
				// permission to use this command
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						CyberionPlugin.getInternalConfig().getNoPermMessage()));

				// Return false since the command was not run successfully
				return false;
			}
		}

		sender.sendMessage(CyberionUtil.getMessageHeader() + "Indiquez l'ID du POI à supprimer");
		return false;
	}

	private boolean toggleItemBookCommand(CommandSender sender, String[] arg) {
		CyberionPlugin plugin = CyberionPlugin.getInstance();

		if (sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {

			CyberionPlugin.getInternalConfig().toggleActiveBookItem();
			CyberionPlugin.getInternalConfig().saveConfig();

			if (CyberionPlugin.getInternalConfig().isActiveBookItem()) {
				sender.sendMessage(CyberionUtil.getMessageHeader('a')+"Auto Give du Livre liste activé");
				for(Player p : plugin.getServer().getOnlinePlayers()) {
					p.getInventory().setItem(7, CyberionUtil.getBookItem());
				}
				
			} else {
				sender.sendMessage(CyberionUtil.getMessageHeader('c') + "Auto Give du Livre liste désactivé");
			}

			return true;

		}
		return false;

	}

}

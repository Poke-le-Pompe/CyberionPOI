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

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class CommandPoi implements CommandExecutor {

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
			case "info":
				return infoCommand(sender, args);
			case "list":
				return listCommand(sender, args);
			case "reload":
				return reloadCommand(sender, args);
			case "book":
				return bookCommand(sender, args);
			case "toggleBook":
				return toggleItemBookCommand(sender, args);
			case "setDesc":
				return setDescCommand(sender, args);
			case "setActivation":
				return setActivationCommand(sender, args);
			case "toggleHolo":
				return toggleHoloCommand(sender, args);
			case "tp":
				return tpCommand(sender, args);

			default:
				return false;
			}

		}


	}

	private boolean tpCommand(CommandSender sender, String[] args) {
		if (args.length > 0) {

			CyberionPlugin plugin = CyberionPlugin.getInstance();

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < args.length; i++) {
				sb.append(args[i]).append(" ");
			}
			String allArgs = sb.toString().trim();

			// Check if the player actually has the permission required
			if (sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {
				if (sender instanceof Player) {
					Player player = (Player) sender;

					if (!plugin.getListPOI().nameExist(allArgs)) {
						player.sendMessage(CyberionUtil.getMessageHeader('c') + "Ce nom de POI n'existe pas");
						return true;
					}

					POI poi = plugin.getListPOI().getPOIbyName(allArgs);
					player.teleport(poi.getLoc());

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

	private boolean toggleHoloCommand(CommandSender sender, String[] args) {
		if (args.length > 0) {

			CyberionPlugin plugin = CyberionPlugin.getInstance();

			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < args.length; i++) {
				sb.append(args[i]).append(" ");
			}
			String allArgs = sb.toString().trim();

			// Check if the player actually has the permission required
			if (sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {
				if (sender instanceof Player) {
					Player player = (Player) sender;

					if (!plugin.getListPOI().nameExist(allArgs)) {
						player.sendMessage(CyberionUtil.getMessageHeader('c') + "Ce nom de POI n'existe pas");
						return true;
					}

					POI poi = plugin.getListPOI().getPOIbyName(allArgs);
					boolean active = poi.toggleHolo();
					if (active) {
						sender.sendMessage(CyberionUtil.getMessageHeader('a')+"Hologramme activé pour le POI " + ChatColor.GOLD + poi.getName());
					} else {
						sender.sendMessage(CyberionUtil.getMessageHeader('c')+"Hologramme désativé pour le POI " + ChatColor.GOLD + poi.getName());
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

	private boolean infoCommand(CommandSender sender, String[] args) {
		CyberionPlugin plugin = CyberionPlugin.getInstance();
		ListPOI listPoi = plugin.getListPOI();
		if (args.length >= 1) {

			//get all args as a name
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				sb.append(args[i]).append(" ");
			}
			String allArgs = sb.toString().trim();

			// Check if the player actually has the permission required
			if (sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {

				if (sender instanceof Player) {
					Player player = (Player) sender;

					String name = allArgs;

					//check if poi exist
					if (!listPoi.nameExist(name)) {
						player.sendMessage(CyberionUtil.getMessageHeader('c') + "POI inexistant !");
						return false;
					}
					POI poi = listPoi.getPOIbyName(name);

					player.sendMessage(ChatColor.GOLD + "--------------------------------");
					player.sendMessage(ChatColor.YELLOW +"Nom: " + ChatColor.LIGHT_PURPLE + poi.getName());
					player.sendMessage(ChatColor.YELLOW +"Description: " + ChatColor.GREEN  + poi.getDesc());
					player.sendMessage(ChatColor.YELLOW +"Message d'Activation: " + ChatColor.GREEN  + poi.getActivationMessage());
					if (poi.getHoloActive()) {
						player.sendMessage(ChatColor.YELLOW +"Hologramme: " + ChatColor.GREEN  + "Activé");
					} else {
						player.sendMessage(ChatColor.YELLOW +"Hologramme: " + ChatColor.RED  + "Désactivé");
					}

					TextComponent message = new TextComponent( ChatColor.YELLOW +"Position: " + ChatColor.AQUA  + poi.getLoc().getBlockX() + " " + poi.getLoc().getBlockY() +" " + poi.getLoc().getBlockZ() + " (" + poi.getLoc().getWorld().getName() + ") " + ChatColor.DARK_AQUA + "[TP]"  );
					message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Se téléporter au POI")));
					message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/poi tp " + poi.getName()));
					player.spigot().sendMessage(message);
					player.sendMessage(ChatColor.GOLD +"---------------------------------");

					return true;

				} else {
					// Send the command sender a message telling them that only players can use this
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',CyberionPlugin.getInternalConfig().getOnlyPlayersMessage()));

					return false;
				}
			} else {

				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',CyberionPlugin.getInternalConfig().getNoPermMessage()));

				return false;
			}
		}

		sender.sendMessage(CyberionUtil.getMessageHeader() + "Indiquez l'ID du POI");
		return false;
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
					player.sendMessage(CyberionUtil.getMessageHeader() + "Cliquez sur le bloc que vous voulez définir en tant que POI");

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

	private boolean setActivationCommand(CommandSender sender, String[] args) {
		CyberionPlugin plugin = CyberionPlugin.getInstance();
		ListPOI listPoi = plugin.getListPOI();

		if (sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					sb.append(args[i]).append(" ");
				}
				String allArgs = sb.toString().trim();

				if (allArgs.matches(".+ ; .+")) {
					String name = allArgs.substring(0, allArgs.indexOf(';')-1);
					String act = allArgs.substring(allArgs.indexOf(';')+2);

					if (!listPoi.nameExist(name)) {
						player.sendMessage(CyberionUtil.getMessageHeader('c') + "POI inexistant !");
						return false;
					}

					listPoi.setActivation(name, act);
					player.sendMessage(CyberionUtil.getMessageHeader() + "Message d'activation du POI " + ChatColor.AQUA + name + ChatColor.YELLOW + " défini en " + ChatColor.AQUA + act);
					return true;

				} else {
					return false;
				}

			} else {
				// Send the command sender a message telling them that only players can use this
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',CyberionPlugin.getInternalConfig().getOnlyPlayersMessage()));

				return false;
			}
		}
		return false;
	}

	private boolean setDescCommand(CommandSender sender, String[] args) {
		CyberionPlugin plugin = CyberionPlugin.getInstance();
		ListPOI listPoi = plugin.getListPOI();

		if (sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {
			if (sender instanceof Player) {
				Player player = (Player) sender;


				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					sb.append(args[i]).append(" ");
				}
				String allArgs = sb.toString().trim();


				if (allArgs.matches(".+ ; .+")) {
					String name = allArgs.substring(0, allArgs.indexOf(';')-1);
					String desc = allArgs.substring(allArgs.indexOf(';')+2);


					//POI Inexistant
					if (!listPoi.nameExist(name)) {
						player.sendMessage(CyberionUtil.getMessageHeader('c') + "POI inexistant !");
						return false;
					}


					listPoi.setDesc(name, desc);
					player.sendMessage(CyberionUtil.getMessageHeader() + "Description du POI " + ChatColor.AQUA + name + ChatColor.YELLOW + " définie en " + ChatColor.AQUA + desc);
					return true;

				} else {
					return false;
				}

			} else {
				// Send the command sender a message telling them that only players can use this
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CyberionPlugin.getInternalConfig().getOnlyPlayersMessage()));

				return false;
			}
		}


		return false;
	}

	private boolean listCommand(CommandSender sender, String[] arg) {
		CyberionPlugin plugin = CyberionPlugin.getInstance();
		ListPOI listPoi = plugin.getListPOI();

		if (sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {
			if (sender instanceof Player) {
				Player player = (Player) sender;

				player.sendMessage(ChatColor.GOLD + "--------------------------------");

				for (POI poi : listPoi) {

					TextComponent message = new TextComponent(
					ChatColor.GOLD + "#" + listPoi.indexOf(poi) + " "
					+ ChatColor.YELLOW + poi.getName() 
					+ ChatColor.DARK_GRAY + " | " 
					+ ChatColor.AQUA + poi.getLoc().getX() + " " + poi.getLoc().getY() + " " + poi.getLoc().getZ() + " ("+ poi.getLoc().getWorld().getName() + ") "  
					);

					
					TextComponent tp = new TextComponent(ChatColor.DARK_AQUA + "[TP] " );
					tp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Se téléporter au POI")));
					tp.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/poi tp " + poi.getName()));
					
					TextComponent info = new TextComponent(ChatColor.DARK_RED + "[INFO]" );
					info.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Afficher les détails")));
					info.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/poi info " + poi.getName()));
					
				    message.addExtra(tp);
				    message.addExtra(info);
					player.spigot().sendMessage(message);
				}


				player.sendMessage(ChatColor.GOLD + "--------------------------------");

				/*
				 * if (plugin.getVisitedList().getPlayerList(player) == null) {
				 * player.sendMessage("Aucun POI Visité"); } else {
				 * 
				 * for (String id : plugin.getVisitedList().getPlayerList(player)) { POI poi =
				 * listPoi.getPOIbyId(id); player.sendMessage("#" + listPoi.indexOf(poi) + " : "
				 * + poi.getName() + " | " + poi.getLoc().getX() + " " + poi.getLoc().getY() +
				 * " " + poi.getLoc().getZ() + " (" + poi.getLoc().getWorld().getName() + ")");
				 * } }
				 */


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

		if (sender.hasPermission(CyberionUtil.CYBERION_USER_PERM) || sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				CyberionUtil.openListBookForPlayer(player);
				return true;
			}
		}
		sender.sendMessage(CyberionUtil.getMessageHeader('c') + "no perm");
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

	private boolean reloadCommand(CommandSender sender, String[] arg) {
		CyberionPlugin plugin = CyberionPlugin.getInstance();

		if (sender.hasPermission(CyberionUtil.CYBERION_ADMIN_PERM)) {

			plugin.reloadAllConfigs();

			if (sender instanceof Player) {
				Player player = (Player) sender;

				player.sendMessage(CyberionUtil.getMessageHeader() + "Reload terminé !");

				return true;


			} else {

				sender.sendMessage(CyberionUtil.getMessageHeader() + "Reload terminé !");

				return true;
			}
		}
		return false;

	}

}

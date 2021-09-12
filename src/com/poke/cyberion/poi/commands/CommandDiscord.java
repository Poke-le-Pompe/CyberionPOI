package com.poke.cyberion.poi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.poke.cyberion.poi.CyberionPlugin;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class CommandDiscord implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {


		if (sender instanceof Player) {
			Player player = (Player) sender;

			player.sendMessage(ChatColor.GOLD + "--------------------------------");


			TextComponent message = new TextComponent(
						ChatColor.YELLOW + "Rejoins-nous sur " + ChatColor.DARK_AQUA + ChatColor.BOLD + "Discord" + ChatColor.RESET + ChatColor.YELLOW + ": ");


				TextComponent link = new TextComponent(ChatColor.DARK_PURPLE + "[" + ChatColor.LIGHT_PURPLE + "Clique ici" + ChatColor.DARK_PURPLE + "]" );
				link.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Rejoindre !")));
				link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/zSxmgNfMk9"));


				message.addExtra(link);
				player.spigot().sendMessage(message);
			
				player.sendMessage(ChatColor.GOLD + "--------------------------------");
				
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

}
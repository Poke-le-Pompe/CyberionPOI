package com.poke.cyberion.poi.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import com.poke.cyberion.poi.CyberionPlugin;
import com.poke.cyberion.poi.objects.ListPOI;
import com.poke.cyberion.poi.objects.ListVisited;
import com.poke.cyberion.poi.objects.POI;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

/**
 * A utility class with various static methods to provide a clean easy API
 * @author Poke
 */
public final class CyberionUtil {

	// Permission string
	public static final String CYBERION_USER_PERM = "Cyberion.poi.user";
	public static final String CYBERION_ADMIN_PERM = "Cyberion.poi.admin";

	// List of all registered permissions
	private static final ArrayList<Permission> perms = new ArrayList<>();

	/**
	 * Prevent anyone from initializing this class as it is solely to be used for
	 * static utility
	 */
	private CyberionUtil() {
	}

	public static String getMessageHeader() {
		return ChatColor.translateAlternateColorCodes('&', "&3<&6Cyberion&3> &e");
	}

	public static String getMessageHeader(char c) {
		return ChatColor.translateAlternateColorCodes('&', "&3<&6Cyberion&3> &" + c);
	}

	/**
	 * Register all Cyberion permissions
	 */
	public static void registerPermissions() {
		// Create the permissions and store them in a list
		// The list is mainly used internally but a getter could be used to grant other
		// developers access to the list
		// For a permission we only need to have a string representing the key, however
		// it's best to include the description and who has the permission by default
		
		// perms.add(new Permission(Cyberion_USE_PERM, "Allows player to use Cyberion",
		// PermissionDefault.TRUE));
		perms.add(new Permission(CYBERION_ADMIN_PERM, "Allows players to access to all the commands", PermissionDefault.OP));
		perms.add(new Permission(CYBERION_USER_PERM, "Allows players to use basic commands", PermissionDefault.NOT_OP));

		// Loop through the list and add all the permissions we created
		for (Permission perm : perms) {
			Bukkit.getPluginManager().addPermission(perm);

			// Log a message that we added the permission
			CyberionPlugin.getInstance().getLogger().fine("Registered Permission: " + perm.getName());
		}
	}

	/**
	 * Unregister all Cyberion permissions
	 */
	public static void unregisterPermissions() {
		for (Permission perm : perms) {
			Bukkit.getPluginManager().removePermission(perm);

			// Log a message that we removed the permission
			CyberionPlugin.getInstance().getLogger().fine("Unregistered Permission: " + perm.getName());
		}

		// Clear the list of permissions incase this method was called but the plugin
		// wasn't disabled
		// If we don't do this then calling registerPermissions() would result in trying
		// to register each permission twice
		perms.clear();
	}

	public static void openListBookForPlayer(Player p) {
		CyberionPlugin plugin = CyberionPlugin.getInstance();
		ListVisited listVisited = plugin.getVisitedList();
		ListPOI listPoi = plugin.getListPOI();

		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();

		int c_visited = 0;
		int c_total = 0;
		String text = "";

		HashMap<String, ArrayList<POI>> hashCat = new HashMap<String, ArrayList<POI>>();
		ArrayList<POI> listAutre = new ArrayList<POI>();

		for (POI poi : listPoi) {
			String cat = poi.getCategory();
			if (cat == null) {
				listAutre.add(poi);
			} else {
				if (hashCat.containsKey(cat)) {
					hashCat.get(cat).add(poi);
				} else {
					ArrayList<POI> newlist = new ArrayList<POI>();
					newlist.add(poi);
					hashCat.put(cat, newlist);
				}
			}
		}



		List<String> keys = new ArrayList<>(hashCat.keySet());
		Collections.sort(keys);

		if (listAutre.size() > 0) {
			hashCat.put("Autre", listAutre);
			keys.add("Autre");
		}

		for (String cat : keys) {

			text = text  +"\n\n" + "�" + cat + ":";

			for (POI poi : hashCat.get(cat)) {


				String checkMark = "";

				// check if visited
				if (listVisited.playerAlreadyVisited(p, poi)) {
					c_visited++;
					checkMark = "\u2714";
					// checkMark = ChatColor.translateAlternateColorCodes('&', "\n&2");
				} else {
					checkMark = "\u2716";
					// checkMark = ChatColor.translateAlternateColorCodes('&', "\n&4");
				}

				String desc;
				if (poi.getDesc().equals("")) {
					desc = "No Description (" + poi.getName() + ")";
				} else {
					desc = poi.getDesc();
				}

				c_total++;
				text = text + "\n" + checkMark + " " + desc;
			}
		}

		text =  c_visited + "/" + c_total + " points trouv�s" + text;
		
		
		String textPage = "";
		int linecount = 0;
		
		
		for (String line : getLines(text)) {

			String rline = line;

			if (!(linecount == 0 && rline.equals(" "))) {


				rline = rline.replace("\u2714", ChatColor.GREEN + "\u2714" + ChatColor.BLACK);

				rline = rline.replace("\u2716", ChatColor.RED + "\u2716" + ChatColor.BLACK);

				rline = rline.replace("�", ChatColor.DARK_AQUA + "" + ChatColor.BOLD);

				textPage = textPage + rline + "\n";

				linecount++;

				if (linecount == 14 || getLines(text).indexOf(line) == getLines(text).size() - 1) {
					BaseComponent[] page = new ComponentBuilder(textPage).create();
					bookMeta.spigot().addPage(page);
					// add the page to the meta

					linecount = 0;
					textPage = "";
				}
			}

		}

		// set the title and author of this book
		bookMeta.setTitle("Liste des Points d'Int�r�t");
		bookMeta.setAuthor("Club Minecraft");

		book.setItemMeta(bookMeta);

		p.openBook(book);

	}

	public static ItemStack getBookItem() {
		ItemStack book = new ItemStack(Material.BOOK);
		ItemMeta bookMeta = book.getItemMeta();

		bookMeta.setDisplayName(ChatColor.AQUA + "" + ChatColor.BOLD + "Liste des Points d'Int�r�t");
		bookMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		bookMeta.addEnchant(Enchantment.SILK_TOUCH, 1, true);

		book.setItemMeta(bookMeta);

		return book;
	}


	private static ArrayList<String> getLines(String rawText) {

		
		// final MinecraftFont font = new MinecraftFont();
		// final int maxLineWidth = font.getWidth("LLLLLLLLLLLLLLLLLLL"); //113

		// Get all of our lines
		ArrayList<String> lines = new ArrayList<>();
		try {
			// Each 'section' is separated by a line break (\n)
			for (String section : rawText.split("\n")) {
				// If the section is blank, that means we had a double line break there
				if (section.equals(""))
					lines.add(" ");
				// We have an actual section with some content
				else {
					// Iterate through all the words of the section
					String[] words = ChatColor.stripColor(section).split(" ");
					String line = "";
					for (int index = 0; index < words.length; index++) {
						String word = words[index];
						// Make sure we can actually use this next word in our current line
						String test = (line + " " + word);
						if (test.startsWith(" "))
							test = test.substring(1);

						// Current line + word is too long to be one line

						if (test.length() > 19) {
							// Add our current line
							lines.add(line);
							// Set our next line to start off with this word
							line = word;
							continue;
						}
						// Add the current word to our current line
						line = test;
					}
					// Make sure we add the line if it was the last word and wasn't too long for the
					// line to start a new one
					if (!line.equals(""))
						lines.add(line);
				}
			}
		} catch (IllegalArgumentException ex) {
			lines.clear();
		}

		return lines;
	}

}
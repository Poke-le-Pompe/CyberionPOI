package com.poke.cyberion.poi.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.poke.cyberion.poi.CyberionPlugin;
import com.poke.cyberion.poi.CyberionUtil;

public class BookItemListener implements Listener {

	@EventHandler
	public void onPlayerClickBookInInventory(InventoryClickEvent event) {

		ItemStack clicked = event.getCurrentItem();

		if (CyberionPlugin.getInternalConfig().isActiveBookItem() && clicked.equals(CyberionUtil.getBookItem())) {
			event.setCancelled(true);
		}

	}
	

	@EventHandler
	public void onPlayerClickBook(PlayerInteractEvent event) {

		Player player = event.getPlayer();

		ItemStack clicked = event.getItem();
		Action action = event.getAction();

		if ((action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) && clicked.equals(CyberionUtil.getBookItem())) {
			CyberionUtil.openListBookForPlayer(player);
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerJoinBook(PlayerJoinEvent event) {

		Player player = event.getPlayer();
		Inventory inv = player.getInventory();
		
		if (CyberionPlugin.getInternalConfig().isActiveBookItem() && !inv.contains(CyberionUtil.getBookItem())) {
			player.getInventory().setItem(7, CyberionUtil.getBookItem());
		}



	}

}

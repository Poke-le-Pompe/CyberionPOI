package com.poke.cyberion.poi.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.poke.cyberion.poi.CyberionPlugin;
import com.poke.cyberion.poi.ListPOI;
import com.poke.cyberion.poi.ListVisited;
import com.poke.cyberion.poi.POI;

public class ClickPOIListener implements Listener {

	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {

		CyberionPlugin plugin = CyberionPlugin.getInstance();
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		ListPOI listPOI = plugin.getListPOI();
		ListVisited listVisited = plugin.getVisitedList();

		boolean isPOI = false;

		POI clickedPoi = null;

		for (POI poi : listPOI) {
			if (poi.getLoc().getBlock().equals(block)) {
				clickedPoi = poi;
				isPOI = true;
				break;
			}
		}

		if (isPOI) {
			event.setCancelled(true);

			if (event.getHand() != EquipmentSlot.HAND) {
				return;
			}

			// si player entrain de set un poi alors stop
			if (plugin.getSetters().containsKey(player)) {
				return;
			}

			if (listVisited.playerAlreadyVisited(player, clickedPoi)) {
				player.sendMessage(ChatColor.YELLOW + "Vous avez déjà trouvé ce point d'intérêt !");
			} else {
				player.sendMessage(ChatColor.YELLOW + "Nouveau Point d'Intérêt découvert !");
				player.sendMessage(ChatColor.GREEN + clickedPoi.getActivationMessage());
				plugin.getVisitedList().addToPlayerList(player, clickedPoi.getUuid());

			}

		}

	}

}

package com.poke.cyberion.poi.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.poke.cyberion.poi.CyberionPlugin;
import com.poke.cyberion.poi.CyberionUtil;
import com.poke.cyberion.poi.POI;

public class SetPOIListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerClick(PlayerInteractEvent event) {

		if (event.getHand() != EquipmentSlot.HAND) {
			return;
		}

		CyberionPlugin plugin = CyberionPlugin.getInstance();
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		if (plugin.getSetters().containsKey(player)) {

			event.setCancelled(true);

			if (event.getHand() != EquipmentSlot.HAND) {
				return;
			}

			if (plugin.getListPOI().locExist(block.getLocation())) {
				player.sendMessage(
						CyberionUtil.getMessageHeader('c') + "Ce block est d�j� un POI ! Choississez un autre bloc.");
				return;
			}

			plugin.getListPOI().add(new POI(plugin.getSetters().get(player), block.getLocation()));

			player.sendMessage(CyberionUtil.getMessageHeader('a') + "Block d�fini en tant que POI");
			plugin.removeSetter(player);

		}

	}

}

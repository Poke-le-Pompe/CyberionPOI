package com.poke.cyberion.poi;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.poke.cyberion.poi.objects.POI;

public class ParticlesRunnable extends BukkitRunnable {


	@Override
	public void run() {
		CyberionPlugin plugin = CyberionPlugin.getInstance();
		for (POI poi : plugin.getListPOI()) { 
			for (Player player : plugin.getServer().getOnlinePlayers()) { 
				if(!plugin.getVisitedList().playerAlreadyVisited(player, poi)) {
					Location loc = new Location(poi.getLoc().getWorld(), poi.getLoc().getX()+0.5,
							poi.getLoc().getY()+0.5, poi.getLoc().getZ()+0.5);
					player.spawnParticle(Particle.VILLAGER_HAPPY, loc, 3, 0.5, 0.5, 0.5); 
				} 
			} 
		}
		
	}


}

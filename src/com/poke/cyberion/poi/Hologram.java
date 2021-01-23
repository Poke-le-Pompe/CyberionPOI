package com.poke.cyberion.poi;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class Hologram {

	ArmorStand asPOI;
	ArmorStand asName;
	//ArmorStand asClick;

	POI poi;

	public Hologram(POI poi) {
		this.poi = poi;

	}

	public void spawn() {

		if (asName == null) {
			
			Location locAsPoi = new Location(poi.getLoc().getWorld(), poi.getLoc().getX()+0.5, poi.getLoc().getY()+1.1, poi.getLoc().getZ()+0.5);
			asPOI = (ArmorStand) poi.getLoc().getWorld().spawnEntity(locAsPoi, EntityType.ARMOR_STAND);
			asPOI.setGravity(false); //Make sure it doesn't fall
			asPOI.setSmall(true);
			asPOI.setMarker(true);
			asPOI.setCanPickupItems(false); //I'm not sure what happens if you leave this as it is, but you might as well disable it
			asPOI.setCustomName(ChatColor.AQUA + "Point d'Intérêt:"); //Set this to the text you want
			asPOI.setCustomNameVisible(true); //This makes the text appear no matter if your looking at the entity or not
			asPOI.setVisible(false); //Makes the ArmorStand invisible
			
			Location locAsName = new Location(poi.getLoc().getWorld(), poi.getLoc().getX()+0.5, poi.getLoc().getY()+0.85, poi.getLoc().getZ()+0.5);
			asName = (ArmorStand) poi.getLoc().getWorld().spawnEntity(locAsName, EntityType.ARMOR_STAND);
			asName.setGravity(false); //Make sure it doesn't fall
			asName.setSmall(true);
			asName.setMarker(true);
			asName.setCanPickupItems(false); //I'm not sure what happens if you leave this as it is, but you might as well disable it
			asName.setCustomName(ChatColor.GOLD + poi.getName()); //Set this to the text you want
			asName.setCustomNameVisible(true); //This makes the text appear no matter if your looking at the entity or not
			asName.setVisible(false); //Makes the ArmorStand invisible
			
		}
	}

	public void despawn() {
		if (asName != null) {
			asName.remove();
			asName = null;	
			asPOI.remove();
			asPOI = null;
			
		}

	}



}

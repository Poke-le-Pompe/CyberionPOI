package com.poke.cyberion.poi;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class ListPOI implements Iterable<POI> {

	private CyberionPlugin plugin;
	private ArrayList<POI> list;

	public ListPOI(CyberionPlugin p) {
		list = new ArrayList<POI>();
		this.plugin = p;
		loadConfig();
	}

	public void loadConfig() {
		
		list = new ArrayList<POI>();
		FileConfiguration config = plugin.getPoiConfig();

		if (!config.contains("poi.0")) {
			return;
		}

		for (String key : config.getConfigurationSection("poi").getKeys(false)) {

			String uuid = config.getString("poi." + key + ".uuid");
			String name = config.getString("poi." + key + ".name");
			String desc = config.getString("poi." + key + ".description");
			String activation = config.getString("poi." + key + ".activationMessage");

			Location loc = config.getLocation("poi." + key + ".location");

			list.add(new POI(uuid, name, desc, loc, activation));
		}

	}

	public void saveConfig() {

		FileConfiguration config = plugin.getPoiConfig();

		for (POI poi : list) {
			int id = list.indexOf(poi);

			config.set("poi." + id + ".uuid", poi.getUuid());
			config.set("poi." + id + ".name", poi.getName());
			config.set("poi." + id + ".description", poi.getDesc());
			config.set("poi." + id + ".location", poi.getLoc());
			config.set("poi." + id + ".activationMessage", poi.getActivationMessage());

		}
		;

		plugin.savePoiConfig();
	}

	public boolean add(POI poi) {
		boolean ret = list.add(poi);
		saveConfig();
		return ret;
	}

	public POI remove(int iddel) {
		POI ret;
		if (list.size() >= iddel + 1) {
			ret = list.remove(iddel);
			saveConfig();
		} else {
			ret = null;
		}

		return ret;
	}

	public POI remove(String name) {
		POI poi = getPOIbyName(name);
		
		if (poi == null) {
			return null;
		} else {
			POI ret = list.remove(list.indexOf(poi));
			saveConfig();
			return ret;
		}
		
	}
	
	public void setDesc(String name, String desc) {
		POI poi = getPOIbyName(name);
		poi.setDesc(desc);
		saveConfig();
	}
	
	public void setActivation(String name, String act) {
		POI poi = getPOIbyName(name);
		poi.setActivationMessage(act);
		saveConfig();
	}
	

	@Override
	public Iterator<POI> iterator() {
		return list.iterator();
	}

	public int indexOf(POI poi) {
		return list.indexOf(poi);
	}

	public boolean locExist(Location loc) {
		for (POI poi : list) {
			if (poi.getLoc().equals(loc)) {
				return true;
			}
		}

		return false;
	}

	public boolean nameExist(String name) {
		for (POI poi : list) {
			if (poi.getName().equals(name)) {
				return true;
			}
		}

		return false;
	}

	public POI getPOIbyId(String id) {
		for (POI poi : list) {
			if (poi.getUuid().equals(id)) {
				return poi;
			}
		}
		return null;
	}

	public POI getPOIbyName(String name) {
		for (POI poi : list) {
			if (poi.getName().equals(name)) {
				return poi;
			}
		}
		return null;
	}

}

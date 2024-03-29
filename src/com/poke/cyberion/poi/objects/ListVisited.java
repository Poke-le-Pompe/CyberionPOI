package com.poke.cyberion.poi.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.poke.cyberion.poi.CyberionPlugin;

public class ListVisited {

	private CyberionPlugin plugin;
	HashMap<String, ArrayList<String>> visitedMap;

	public ListVisited(CyberionPlugin p) {
		this.visitedMap = new HashMap<String, ArrayList<String>>();
		this.plugin = p;
		loadConfig();
	}

	@SuppressWarnings("unchecked")
	public void loadConfig() {

		this.visitedMap = new HashMap<String, ArrayList<String>>();
		FileConfiguration config = plugin.getVisitedConfig();

		if (!config.contains("players")) {
			return;
		}

		for (String id : config.getConfigurationSection("players").getKeys(false)) {

			ArrayList<String> al = new ArrayList<String>();
			al.addAll((Collection<? extends String>) config.getList("players." + id + ".poiVisited"));
			//System.out.println(config.getList("players." + id + ".poiVisited"));

			visitedMap.put(id, al);

			//System.out.println(visitedMap);

		}

	}

	public void saveConfig() {

		FileConfiguration config = plugin.getVisitedConfig();
		
		
		config.set("players", null);

		for (Map.Entry<String, ArrayList<String>> entry : visitedMap.entrySet()) {
			

			//config.set("players." + entry.getKey().toString() + ".username", Bukkit.getPlayer(UUID.fromString(entry.getKey())).getName());
			config.set("players." + entry.getKey().toString() + ".poiVisited", entry.getValue());

		}

		plugin.saveVisitedConfig();
	}

	public void addToPlayerList(Player p, String poiUuid) {
		String id = p.getUniqueId().toString();
		if (visitedMap.containsKey(id)) {
			visitedMap.get(id).add(poiUuid);
		} else {
			ArrayList<String> list = new ArrayList<String>();
			list.add(poiUuid);
			visitedMap.put(id, list);
		}
		saveConfig();
	}

	public void RemoveFromPlayerList(Player p, String poiUuid) {
		String id = p.getUniqueId().toString();
		if (visitedMap.containsKey(id)) {
			ArrayList<String> list = visitedMap.get(id);
			if (list.contains(poiUuid)){
				list.remove(poiUuid);
				saveConfig();
			}

		}

	}

	public void RemoveFromAll(String poiUuid) {
		for (Map.Entry<String, ArrayList<String>> entry : visitedMap.entrySet()) {
			ArrayList<String> list = entry.getValue();
			if (list.contains(poiUuid)){
				list.remove(poiUuid);
			}
			
			saveConfig();
			
		}
		
	}

	public ArrayList<String> getPlayerList(Player p) {
		String id = p.getUniqueId().toString();

		if (visitedMap.containsKey(id)) {
			return visitedMap.get(id);
		} else {
			return null;
		}
	}

	public boolean playerAlreadyVisited(Player p, POI poi) {
		ArrayList<String> list = visitedMap.get(p.getUniqueId().toString());
		if (list == null)
			return false;
		return list.contains(poi.getUuid());
	}

}

package com.poke.cyberion.poi;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.poke.cyberion.poi.commands.CommandPoi;
import com.poke.cyberion.poi.listeners.BookItemListener;
import com.poke.cyberion.poi.listeners.ClickPOIListener;
import com.poke.cyberion.poi.listeners.SetPOIListener;

public class CyberionPlugin extends JavaPlugin {

	// Logger instance for logging debug messages and information about what the
	// plugin is doing
	private final Logger logger = Logger.getLogger("CyberionPOI");

	// Our internal config object for storing the configuration options that server
	// owners can set
	private static Config config;

	// An instance of this plugin for easy access
	private static CyberionPlugin plugin;

	private HashMap<Player, String> poiSetters;
	private ListVisited visitedList;

	private ListPOI listPOI;

	private File poiConfigFile;
	private FileConfiguration poiConfig;

	private File visitedConfigFile;
	private FileConfiguration visitedConfig;

	// Lancé à l'activation
	@Override
	public void onEnable() {
		plugin = this;
		config = new Config();
		createPoiConfig();
		createVisitedConfig();

		poiSetters = new HashMap<Player, String>();
		setVisitedList(new ListVisited(this));
		listPOI = new ListPOI(this);

		CyberionUtil.registerPermissions();

		/*
		 * this.getCommand("setPOI").setExecutor(new CsetPOI());
		 * this.getCommand("listPOI").setExecutor(new ClistPOI());
		 * this.getCommand("removePOI").setExecutor(new CremovePOI());
		 * this.getCommand("reloadPOI").setExecutor(new CreloadPOI());
		 */

		this.getCommand("POI").setTabCompleter(new PoiTabCompleter());
		this.getCommand("POI").setExecutor(new CommandPoi());

		getServer().getPluginManager().registerEvents(new SetPOIListener(), this);
		getServer().getPluginManager().registerEvents(new ClickPOIListener(), this);
		getServer().getPluginManager().registerEvents(new BookItemListener(), this);

	}

	// Lancé à la désactivation
	@Override
	public void onDisable() {
		CyberionUtil.unregisterPermissions();

	}

	public Logger getLogger() {
		return logger;
	}

	public HashMap<Player, String> getSetters() {
		return poiSetters;
	}

	public void addSetter(Player p, String d) {
		poiSetters.put(p, d);
	}

	public void removeSetter(Player p) {
		poiSetters.remove(p);
	}

	/**
	 * Gets an instance of this plugin
	 * 
	 * @return The static instance of this plugin
	 */
	public static CyberionPlugin getInstance() {
		return plugin;
	}

	public static Config getInternalConfig() {
		return config;
	}

	public ListPOI getListPOI() {
		return listPOI;
	}

	public FileConfiguration getPoiConfig() {
		return this.poiConfig;
	}

	public FileConfiguration getVisitedConfig() {
		return this.visitedConfig;
	}
	

	private void createPoiConfig() {
		poiConfigFile = new File(getDataFolder(), "poi.yml");
		if (!poiConfigFile.exists()) {
			poiConfigFile.getParentFile().mkdirs();
			saveResource("poi.yml", false);
		}

		poiConfig = new YamlConfiguration();
		try {
			poiConfig.load(poiConfigFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	private void createVisitedConfig() {
		visitedConfigFile = new File(getDataFolder(), "players.yml");
		if (!visitedConfigFile.exists()) {
			visitedConfigFile.getParentFile().mkdirs();
			saveResource("players.yml", false);
		}

		visitedConfig = new YamlConfiguration();
		try {
			visitedConfig.load(visitedConfigFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void savePoiConfig() {
		try {
			poiConfig.save(poiConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveVisitedConfig() {
		try {
			visitedConfig.save(visitedConfigFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ListVisited getVisitedList() {
		return visitedList;
	}

	public void setVisitedList(ListVisited visitedList) {
		this.visitedList = visitedList;
	}

}

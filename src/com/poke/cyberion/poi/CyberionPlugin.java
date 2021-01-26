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
import org.bukkit.scheduler.BukkitRunnable;
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


		new BukkitRunnable() {

			@Override
			public void run() {
				CyberionPlugin plugin = CyberionPlugin.getInstance();
				config = new Config();
				plugin.createPoiConfig();
				plugin.createVisitedConfig();

				poiSetters = new HashMap<Player, String>();
				setVisitedList(new ListVisited(plugin));
				listPOI = new ListPOI(plugin);

				CyberionUtil.registerPermissions();

				plugin.getCommand("POI").setTabCompleter(new PoiTabCompleter());
				plugin.getCommand("POI").setExecutor(new CommandPoi());

				plugin.getServer().getPluginManager().registerEvents(new SetPOIListener(), plugin);
				plugin.getServer().getPluginManager().registerEvents(new ClickPOIListener(), plugin);
				plugin.getServer().getPluginManager().registerEvents(new BookItemListener(), plugin);
				
				ParticlesRunnable br = new ParticlesRunnable();
				br.runTaskTimer(plugin, 30, 30);

			}

		}.runTaskLater(this, 1);


	}

	// Lancé à la désactivation
	@Override
	public void onDisable() {
		CyberionUtil.unregisterPermissions();
		for (POI poi : listPOI) {
			poi.getHolo().despawn();
		}

	}

	public void reloadAllConfigs() {
		for (POI poi : listPOI) {
			poi.getHolo().despawn();
		}
		createPoiConfig();
		createVisitedConfig();
		listPOI.loadConfig();
		visitedList.loadConfig();
		reloadConfig();
		config.reloadConfig();
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

	public HashMap<Player, String> getSetters() {
		return poiSetters;
	}

	public void addSetter(Player p, String d) {
		poiSetters.put(p, d);
	}

	public void removeSetter(Player p) {
		poiSetters.remove(p);
	}

	public ListPOI getListPOI() {
		return listPOI;
	}

	public void setVisitedList(ListVisited visitedList) {
		this.visitedList = visitedList;
	}

	public ListVisited getVisitedList() {
		return visitedList;
	}

	public static Config getInternalConfig() {
		return config;
	}

	public FileConfiguration getPoiConfig() {
		return this.poiConfig;
	}

	public FileConfiguration getVisitedConfig() {
		return this.visitedConfig;
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

	public Logger getLogger() {
		return logger;
	}

	/**
	 * Gets an instance of this plugin
	 * 
	 * @return The static instance of this plugin
	 */
	public static CyberionPlugin getInstance() {
		return plugin;
	}

}

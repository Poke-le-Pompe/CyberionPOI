package com.poke.cyberion.poi;

import org.bukkit.configuration.file.FileConfiguration;


  //An internal config class used to store all variables from the plugin
 
public final class Config {

	// Keys for accessing information in this plugin's FileConfiguration
	// We are saving these to avoid "Magic Values"
	public static final String NO_PERM_KEY = "locale.noperm";
	public static final String ONLY_PLAYERS_KEY = "locale.onlyplayers";
	public static final String LIST_BOOK_ITEM_ACTIVE = "general.list_book_item";

	// Message strings
	private String noPermMessage, invalidPlayerMessage, onlyPlayersMessage, configReloadedMessage;
	private boolean activeBookItem;

	/**
	 * Default config constructor Once we create the Config object we want to ensure
	 * that the defaults are set and that we load whatever data is in the file
	 */
	public Config() {
		setDefaults();
		loadConfig();
	}

	/**
	 * Load data from plugin FileConfiguration Here we load all information from the
	 * FileConfiguration to our internal variables
	 */
	public void loadConfig() {
		FileConfiguration config = CyberionPlugin.getInstance().getConfig();

		// Load all the values from the FileConfiguration into our internal config
		noPermMessage = config.getString(NO_PERM_KEY, "&4You do not have permission for that!");
		onlyPlayersMessage = config.getString(ONLY_PLAYERS_KEY, "&4Only players can enter that command!");
		
		activeBookItem = config.getBoolean(LIST_BOOK_ITEM_ACTIVE, false);
	}

	/**
	 * Set default FileConfiguration This will create the file if it doesn't exist
	 * This will also setup any default values and write them to the config.yml file
	 * if they are missing
	 **/
	public void setDefaults() {
		// Get this plugin's FileConfiguration object
		FileConfiguration config = CyberionPlugin.getInstance().getConfig();

		// Set the default values using a string key plus the value we want to set
		config.addDefault(LIST_BOOK_ITEM_ACTIVE, false);
		
		config.addDefault(NO_PERM_KEY, "&4You do not have permission for that!");
		config.addDefault(ONLY_PLAYERS_KEY, "&4Only players can enter that command!");

		// Copy the defaults that we set back to the FileConfiguration object
		config.options().copyDefaults(true);

		// Last step is to actually write the defaults to the config.yml file
		CyberionPlugin.getInstance().saveConfig();
	}

	
	public void saveConfig() {
		FileConfiguration config = CyberionPlugin.getInstance().getConfig();

		// Save any updated values from our internal config to the FileConfiguration
		config.set(LIST_BOOK_ITEM_ACTIVE, activeBookItem);
		
		config.set(NO_PERM_KEY, noPermMessage);
		config.set(ONLY_PLAYERS_KEY, onlyPlayersMessage);

		CyberionPlugin.getInstance().saveConfig();
	}

	/**
	 * Reloads the config 
	 */
	public void reloadConfig() {
		loadConfig();
	}

	/**
	 * Gets the No Permission message
	 * 
	 * @return The No Permission message
	 */
	public String getNoPermMessage() {
		return noPermMessage;
	}

	/**
	 * Sets the No Permission message
	 * 
	 * @param The No Permission message
	 */
	public void setNoPermMessage(String message) {
		this.noPermMessage = message;
	}

	/**
	 * Gets the Invalid Player message
	 * 
	 * @return The Invalid Player message
	 */
	public String getInvalidPlayerMessage() {
		return invalidPlayerMessage;
	}

	/**
	 * Sets the Invalid Player message
	 * 
	 * @param The Invalid Player message
	 */
	public void setInvalidPlayerMessage(String message) {
		this.invalidPlayerMessage = message;
	}

	/**
	 * Gets the Only Players message
	 * 
	 * @return The Only Players message
	 */
	public String getOnlyPlayersMessage() {
		return onlyPlayersMessage;
	}

	/**
	 * Sets the Only Players message
	 * 
	 * @param The Only Players message
	 */
	public void setOnlyPlayersMessage(String message) {
		this.onlyPlayersMessage = message;
	}

	/**
	 * Gets the Config Reloaded message
	 * 
	 * @return The Config Reloaded message
	 */
	public String getConfigReloadedMessage() {
		return configReloadedMessage;
	}

	/**
	 * Sets the Config Reloaded message
	 * 
	 * @param The Config Reloaded message
	 */
	public void setConfigReloadedMessage(String message) {
		this.configReloadedMessage = message;
	}
	
	public boolean isActiveBookItem() {
		return activeBookItem;
	}

	public void setActiveBookItem(boolean listBookItem) {
		this.activeBookItem = listBookItem;
	}
	
	public void toggleActiveBookItem() {
		this.activeBookItem = !this.activeBookItem;
	}
}
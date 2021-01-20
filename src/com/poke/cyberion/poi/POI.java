package com.poke.cyberion.poi;

import java.util.UUID;

import org.bukkit.Location;

public class POI {

	private String name;
	private String uuid;
	private String desc;
	private String activationMessage;
	private Location loc;

	public POI(String name, Location loc) {
		super();
		this.name = name;
		this.loc = loc;
		this.desc = "";
		this.activationMessage = "";
		this.setUuid(UUID.randomUUID().toString().replace("-", "").substring(5, 15));
	}

	public POI(String name, String desc, Location loc) {
		super();
		this.name = name;
		this.desc = desc;
		this.loc = loc;
		this.activationMessage = "";
		this.setUuid(UUID.randomUUID().toString().replace("-", "").substring(5, 15));
	}

	public POI(String id, String name, String desc, Location loc) {
		super();
		this.name = name;
		this.desc = desc;
		this.loc = loc;
		this.uuid = id;
		this.activationMessage = "";
	}

	public POI(String id, String name, String desc, Location loc, String actMess) {
		super();
		this.name = name;
		this.desc = desc;
		this.loc = loc;
		this.uuid = id;
		this.activationMessage = actMess;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getActivationMessage() {
		return activationMessage;
	}

	public void setActivationMessage(String activationMessage) {
		this.activationMessage = activationMessage;
	}

}

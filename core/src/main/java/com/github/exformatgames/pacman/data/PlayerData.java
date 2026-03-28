package com.github.exformatgames.pacman.data;

public class PlayerData {
	
	public int ID;
	public String username;
	public Status status;
	
	public enum Status {
		ONLINE,
		OFFLINE
	}
}

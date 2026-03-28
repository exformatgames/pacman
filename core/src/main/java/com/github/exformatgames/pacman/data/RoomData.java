package com.github.exformatgames.pacman.data;

public class RoomData {
	
	public int ID;
	public String name;
	
	public int creatorID;
	
	public int mapID;
	public int[]playerIDList;
	
	public enum Status {
		WAIT,
		IN_GAME
	}
}

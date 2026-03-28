package com.github.exformatgames.pacman.server;

import com.github.exformatgames.pacman.server.ecs.GameWorld;
import com.badlogic.gdx.utils.Array;
import com.github.exformatgames.pacman.data.PlayerData;
import com.badlogic.gdx.utils.IntMap;

public class Room {
	
	private final int ID;
	private final String name;
	private final int authorID;
	
	private GameWorld gameWorld;
	
	private final IntMap<PlayerData> playerDataMap = new IntMap<>();

	public Room (int iD, String name, int authorID) {
		ID = iD;
		this.name = name;
		this.authorID = authorID;
	}

	

	

	public void update (float dT) {}
	
	public void reset() {}
	
	public int getID () {
		return ID;
	}
	
	public String getName () {
		return name;
	}

	public int getAuthorID () {
		return authorID;
	}
	
	public boolean containsPlayer(int playerID) {
		return playerDataMap.containsKey(playerID);
	}
	
	public boolean addPlayer(PlayerData player) {
		if (playerDataMap.containsKey(player.ID)) {
			return false;
		}
		
		playerDataMap.put(player.ID, player);
		
		//create entity
		return true;
	}
	
	public boolean deletePlayer(int playerID) {
		if (playerDataMap.containsKey(playerID)) {
			playerDataMap.remove(playerID);
			
			//delete entity
			return true;
		}

		return false;
	}
}

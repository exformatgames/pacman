package com.github.exformatgames.pacman.server;

import com.github.exformatgames.pacman.data.PlayerData;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;

public class Server {
	
	private final IntMap<Room> roomMap = new IntMap<>();
	private final Array<Room> activeRoomList = new Array<>();
	//private final Array<PlayerData> playerList = new Array<>();
	private final IntMap<PlayerData> playerMap = new IntMap<>();
	private final IntMap<PlayerData> onlinePlayerMap = new IntMap<>();
	private final IntMap<PlayerData> offlinePlayerMap = new IntMap<>();
	
	private final RoomActions roomActions = new RoomActions(roomMap, activeRoomList);

	//private final PlayerActions playerActions = new PlayerActions();
	
	public RoomActions getRoomActions () {
		return roomActions;
	}
	
	public void updateActiveRooms(float dT) {
		for (Room room : activeRoomList) {
			room.update(dT);
		}
	}
}

package com.github.exformatgames.pacman.server;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.github.exformatgames.pacman.data.PlayerData;

public class RoomActions {
	private final IntMap<Room> roomMap;
	private final Array<Room> activeRoomList;
	//private final Array<PlayerData> playerList;

	private int lastRoomID = 0;

	public RoomActions (IntMap<Room> roomMap, Array<Room> activeRoomList) {
		this.roomMap = roomMap;
		this.activeRoomList = activeRoomList;
		//this.playerList = playerList;
	}

	public int createRoom(PlayerData player, String name) {
		int roomID = lastRoomID += 1;
		Room room = new Room(roomID, name, player.ID);
		roomMap.put(roomID, room);

		return roomID;
	}

	public boolean deleteRoom(PlayerData player, int roomID) {
		if (roomMap.containsKey(roomID)) {
			Room room = roomMap.get(roomID);
			if (room.getAuthorID() == player.ID) {
				activeRoomList.removeValue(room, true);
				roomMap.remove(roomID);
				return true;
			}
		}

		return false;
	}


	public boolean startRoom(PlayerData player, int roomID) {
		if (roomMap.containsKey(roomID)) {
			Room room = roomMap.get(roomID);
			if (room.getAuthorID() == player.ID) {
				if ( ! activeRoomList.contains(room, true)) {
					activeRoomList.add(room);
					return true;
				}
			}
		}

		return false;
	}

	public boolean stopRoom(PlayerData player, int roomID) {
		if (roomMap.containsKey(roomID)) {
			Room room = roomMap.get(roomID);
			if (room.getAuthorID() == player.ID) {
				activeRoomList.removeValue(room, true);
				return true;
			}
		}

		return false;
	}


	public boolean resetRoom(PlayerData player, int roomID) {
		if (roomMap.containsKey(roomID)) {
			Room room = roomMap.get(roomID);
			if (room.getAuthorID() == player.ID) {
				room.reset();
				return true;
			}
		}

		return false;
	}

	public boolean addPlayer(PlayerData player, int roomID) {
		if (roomMap.containsKey(roomID)) {
			Room room = roomMap.get(roomID);
			
			return room.addPlayer(player);
		}

		return false;
	}
	
	public boolean deletePlayer(PlayerData player, int roomID) {
		if (roomMap.containsKey(roomID)) {
			Room room = roomMap.get(roomID);

			return room.deletePlayer(player.ID);
		}

		return false;
	}
}

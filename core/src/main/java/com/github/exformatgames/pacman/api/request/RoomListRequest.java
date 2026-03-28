package com.github.exformatgames.pacman.api.request;

import com.github.exformatgames.pacman.data.RoomData;

public interface RoomListRequest {
	public void request(RoomData[] roomList);
}

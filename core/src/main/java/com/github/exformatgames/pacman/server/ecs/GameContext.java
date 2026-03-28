package com.github.exformatgames.pacman.server.ecs;

import com.github.exformatgames.pacman.data.RoomData;
import com.github.exformatgames.pacman.data.MapData;
import com.badlogic.gdx.utils.Array;
import com.github.exformatgames.pacman.data.PlayerData;

public class GameContext {
	
	public RoomData room;
	
	public MapData map;
	
	public Array<PlayerData> playerList = new Array<>();
	
	
}

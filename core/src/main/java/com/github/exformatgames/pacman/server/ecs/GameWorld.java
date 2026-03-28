package com.github.exformatgames.pacman.server.ecs;

import com.artemis.World;
import com.github.exformatgames.pacman.data.InputData;
import com.github.exformatgames.pacman.data.PlayerData;

public class GameWorld {
	
	private World world;
	
	public GameWorld() {}
	
	public void update(float dT) {
		world.setDelta(dT);
		world.process();
	}
	
	public void stopGame() {}
	
	public void addPlayer(PlayerData player) {}
	public void deletePlayer(PlayerData player) {}
	public void playerInput(PlayerData player, InputData input) {}
	
	public void getDeltaMapData() {}
}

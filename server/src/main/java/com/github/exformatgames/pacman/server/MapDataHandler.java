package com.github.exformatgames.pacman.server;

import data.MapData;

public class MapDataHandler {

	private final GameWorld gameWorld;

	public MapDataHandler (GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public void requestGameMap(final int clientID) {
		gameWorld.addCommand(new Runnable() {
				@Override
				public void run() {
					MapData mapData = gameWorld.buildMapData();
					//хм.. идем из нетсервиса чтоб обратно в него вернуться.. пусть и через очередь...
					gameWorld.getNetService().getSendService().sendMapData(clientID, mapData);
				}
			});
	}
}

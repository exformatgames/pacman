package com.github.exformatgames.pacman.server.data;

public class GameField {
	
	private final EntityData[][] map;

	public GameField (MapData mapData) {
		map = new EntityData[mapData.width][mapData.height];
		for (EntityData entityData : mapData.entityList) {
			if (entityData != null) {
				map[entityData.position.x][entityData.position.y] = entityData;
			}
		}
	}

	public EntityData[][] getMap () {
		return map;
	}
}

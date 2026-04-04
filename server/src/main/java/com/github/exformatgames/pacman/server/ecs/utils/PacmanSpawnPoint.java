package com.github.exformatgames.pacman.server.ecs.utils;

import data.MapData;
import data.PositionData;

public class PacmanSpawnPoint {
	private static PositionData position = new PositionData();

	public static PositionData get (MapData data) {
		double random = Math.random();
        random *= 4;
        int point = Math.toIntExact(Math.round(random));
        switch (point) {
			case 0 : {
					position.x = 1;
					position.y = 1;
					break;
				}
			case 1 : {
					position.x = 1;
					position.y = data.height - 2;
					break;
				}
			case 2 : {
					position.x = data.width - 2;
					position.y = data.height - 2;
					break;
				}
			case 3 : {
					position.x = data.width - 2;
					position.y = 1;
					break;
				}
		}

		return position;
	}
}

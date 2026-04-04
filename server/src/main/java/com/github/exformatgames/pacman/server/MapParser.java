package com.github.exformatgames.pacman.server;

/**
 * @author Exformat, ChatGPT
 */

import data.EntityData;
import data.EntityType;
import data.MapData;
import data.PositionData;

import java.io.BufferedReader;
import java.util.ArrayList;

public class MapParser {

    private static final String WALLS = "walls:";

	private ArrayList<EntityData> entityList = new ArrayList<>();

    public MapData parse (BufferedReader reader) throws java.io.IOException {

        MapData mapData = new MapData();

        String line;
        boolean readingWalls = false;

        while ((line = reader.readLine()) != null) {

            line = line.trim();

            // пропускаем пустое
            if (line.length() == 0) {
                continue;
            }

            // комментарии
            if (line.startsWith("//")) {
                continue;
            }

            // секция walls
            if (line.equalsIgnoreCase(WALLS)) {
                readingWalls = true;
                continue;
            }

            // если ещё не начали стены — значит это размер
            if (!readingWalls) {
                parseSize(line, mapData);
                continue;
            }

            // иначе читаем стены
            parseWall(line, mapData);
        }

        mapData.entityList = entityList.toArray(new EntityData[0]);
        entityList.clear();

        return mapData;
    }

    private void parseSize (String line, MapData mapData) {
        String[] parts = line.split(";");

        if (parts.length != 2) {
            throw new RuntimeException("Invalid size line: " + line);
        }

        int width = Integer.parseInt(parts[0]);
        int height = Integer.parseInt(parts[1]);

        mapData.width = width;
		mapData.height = height;
    }

    private void parseWall (String line, MapData mapData) {
        String[] parts = line.split(";");

        if (parts.length != 2) {
            throw new RuntimeException("Invalid wall line: " + line);
        }

        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);

		EntityData entityData = new EntityData();
		PositionData positionData = new PositionData();

		entityData.position = positionData;
		entityData.type = EntityType.WALL;

		positionData.x = x;
		positionData.y = y;

        entityList.add(entityData);
    }
}

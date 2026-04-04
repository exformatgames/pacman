package com.github.exformatgames.pacman;

import data.EntityData;
import data.EntityType;
import data.MapData;

public class TestGameMap {

    private final MapData data = new MapData();
    private final EntityData pacmanData = new EntityData();
	private final EntityData pacmanData2 = new EntityData();
	private final EntityData pacmanData3 = new EntityData();
    private EntityData foodData = new EntityData();


    private final GameWorld world;

    public TestGameMap(GameWorld world) {
        this.world = world;


        data.width = 20;
        data.height = 20;

        data.entityList = new EntityData[data.width * data.height];



		int index = -1;
        for (int x = 0; x < data.width; x++) {
            for (int y = 0; y < data.height; y++) {
                index++;
				if (x == 0 || x == data.width - 1 || y == 0 || y == data.height - 1) {
                    data.entityList[index] = new EntityData();
                    data.entityList[index].type = EntityType.WALL;
                    data.entityList[index].position.x = x;
                    data.entityList[index].position.y = y;
                }

                if (y == 3 && x != 5) {
                    data.entityList[index] = new EntityData();
                    data.entityList[index].type = EntityType.WALL;
                    data.entityList[index].position.x = x;
                    data.entityList[index].position.y = y;
                }

                if (x == 1 && y == 1) {
                    data.entityList[index] = pacmanData;
                    data.entityList[index].type = EntityType.PACMAN;
                    data.entityList[index].ID = 1;
                    data.entityList[index].position.x = x;
                    data.entityList[index].position.y = y;
                }

				if (x == 1 && y == 2) {
                    data.entityList[index] = pacmanData2;
                    data.entityList[index].type = EntityType.PACMAN;
                    data.entityList[index].ID = 2;
                    data.entityList[index].position.x = x;
                    data.entityList[index].position.y = y;
                }

                if (x == 5 && y == 5) {
                    data.entityList[index] = foodData;
                    data.entityList[index].type = EntityType.FOOD;
                    data.entityList[index].ID = 1;
                    data.entityList[index].position.x = x;
                    data.entityList[index].position.y = y;
                }

                if (x == 7 && y == 7) {
                    data.entityList[index] = pacmanData3;
                    data.entityList[index].type = EntityType.PACMAN;
                    data.entityList[index].ID = 3;
                    data.entityList[index].position.x = x;
                    data.entityList[index].position.y = y;
                }
            }
        }

        world.onGameMapReceived(data);
    }

    private float foodTimer = 0;
    private float pacmanTimer = 0;
	private float pacmanTimer2 = 0;
	private float pacmanTimer3 = 0;
    private int dir = 1;
    private int dir2 = 1;
    private int dir3 = 1;
    public void update(float dT) {

        pacmanTimer += dT;
		pacmanTimer2 += dT;
		pacmanTimer3 += dT;
        foodTimer += dT;

        float pacmanStepTime = 0.142f;
        if (pacmanTimer > pacmanStepTime) {
            pacmanTimer = 0;

            pacmanData.position.x += dir;
            if (pacmanData.position.x == data.width - 2) {
                dir = -1;
            }
            if (pacmanData.position.x == 1) {
                dir = 1;
            }

            world.onEntityTransformed(pacmanData);
        }

		if (pacmanTimer2 > 1) {
            pacmanTimer2 = 0;

            pacmanData2.position.x += dir2;
            if (pacmanData2.position.x == data.width - 2) {
                dir2 = -1;
            }
            if (pacmanData2.position.x == 1) {
                dir2 = 1;
            }

            world.onEntityTransformed(pacmanData2);
        }

        if (pacmanTimer3 > pacmanStepTime) {
            pacmanTimer3 = 0;

            pacmanData3.position.y += dir3;
            if (pacmanData3.position.y == data.height - 2) {
                dir3 = -1;
            }
            if (pacmanData3.position.y == 4) {
                dir3 = 1;
            }

            world.onEntityTransformed(pacmanData3);
        }

        if (foodTimer > 1) {
            foodTimer = 0;

            if (foodData == null) {
                foodData = new EntityData();
                foodData.type = EntityType.FOOD;
                foodData.position.x = 5;
                foodData.position.y = 5;
                foodData.ID++;

                world.onEntityCreated(foodData);
            } else {
                world.onEntityRemoved(foodData);
                foodData = null;
            }
        }
    }
}

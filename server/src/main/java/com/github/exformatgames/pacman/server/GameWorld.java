package com.github.exformatgames.pacman.server;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.github.exformatgames.pacman.server.ecs.EntityBuilder;
import com.github.exformatgames.pacman.server.ecs.entities.FoodEntityBuilder;
import com.github.exformatgames.pacman.server.ecs.systems.*;
import com.github.exformatgames.pacman.server.net.NetService;
import data.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

//ну.. миллиард геттеров лучше чем миллиард логики...
public class GameWorld {
    private final MapData mapData;
    private final GameField field;
    private final World world;
	private NetService netService;

	private final GameInputHandler gameInputHandler;
	private final MapDataHandler mapDataHandler;
	private final EntityEventHandler entityEventHandler;
	private final PlayerHandler playerHandler;

    private final Map<Integer, Integer> entityIDMap = new HashMap<>();
    private final Map<Integer, EntityData> entityMap = new HashMap<>();
    private final ArrayList<Integer> playerIDList = new ArrayList<>();
	private final Queue<Runnable> commandQueue = new ConcurrentLinkedQueue<>();

    private final FoodEntityBuilder foodEB = new FoodEntityBuilder();

    public GameWorld (MapData mapData) {
        this.mapData = mapData;

		gameInputHandler = new GameInputHandler(this);
		mapDataHandler = new MapDataHandler(this);
		entityEventHandler = new EntityEventHandler(this);
		playerHandler = new PlayerHandler(this);

        field = new GameField(mapData);

        world = new World(
            new WorldConfigurationBuilder()
			.alwaysDelayComponentRemoval(true)
			.with(new BaseSystem[]{
					  new PressedKeySystem(),
					  new ReleasedKeySystem(),
					  new DirectionSystem(field),
					  new FoodPeekUpSystem(field, this),
					  new MoveSystem(field, this)
				  }).build()
        );

        EntityBuilder.artemisWorld = world;

        initFood();
    }

	public void update (float dT) {
        while (!commandQueue.isEmpty()) {
            Runnable command = commandQueue.poll();
            if (command != null) {
                command.run();
            }
        }

        world.setDelta(dT);
        world.process();
    }

	public MapData buildMapData () {
		ArrayList<EntityData> mapList = new ArrayList<>();

		for (EntityData[] list : field.getMap()) {
			for (EntityData entityData : list) {
				if (entityData != null) {
					mapList.add(entityData);
				}
			}
		}

        mapData.entityList = mapList.toArray(new EntityData[mapList.size()]);

		return mapData;
	}

    public void addCommand (Runnable runnable) {
        commandQueue.add(runnable);
    }

    private void initFood() {
        for (int x = 0; x < field.getMap().length; x++) {
            for (int y = 0; y < field.getMap()[x].length; y++) {
                if (field.getMap()[x][y] == null) {
                    EntityData entityData = new EntityData();

                    entityData.type = EntityType.FOOD;
                    entityData.position = new PositionData();
                    entityData.position.x = x;
                    entityData.position.y = y;

                    int ID = foodEB.build(entityData);
                    entityData.ID = ID;

                    field.getMap()[x][y] = entityData;
                }
            }
        }
    }


    public GameField getField() {
        return field;
    }

    public int getEntityID (int playerID) {
        return entityIDMap.getOrDefault(playerID, -1);
    }

	public void setNetService (NetService netService) {
		this.netService = netService;
	}

	public NetService getNetService () {
		return netService;
	}

	public PlayerHandler getPlayerHandler () {
		return playerHandler;
	}

	public World getWorld () {
		return world;
	}

	public Map<Integer, Integer> getEntityIDMap () {
		return entityIDMap;
	}

	public Map<Integer, EntityData> getEntityMap () {
		return entityMap;
	}

	public ArrayList<Integer> getPlayerIDList () {
		return playerIDList;
	}

	public EntityEventHandler getEntityEventHandler () {
		return entityEventHandler;
	}

	public MapData getMapData () {
		return mapData;
	}

	public GameInputHandler getInputHandler () {
		return gameInputHandler;
	}

	public MapDataHandler getMapDataHandler () {
		return mapDataHandler;
	}
}

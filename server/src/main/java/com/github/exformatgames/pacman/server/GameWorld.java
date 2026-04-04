package com.github.exformatgames.pacman.server;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.github.exformatgames.pacman.server.ecs.EntityBuilder;
import com.github.exformatgames.pacman.server.ecs.entities.FoodEntityBuilder;
import com.github.exformatgames.pacman.server.ecs.systems.*;
import com.github.exformatgames.pacman.server.net.NetService;
import data.EntityData;
import data.EntityType;
import data.GameField;
import data.MapData;

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

	private final InputHandler inputHandler;
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

		inputHandler = new InputHandler(this);
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

        for (EntityData entityData : mapData.entityList) {
            if (entityData != null && entityData.type == EntityType.FOOD) {
                foodEB.build(entityData);
            }
        }
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
		EntityData[] map = new EntityData[mapData.width * mapData.height];
		int i = 0;
		for (EntityData[] list : field.getMap()) {
			for (EntityData entityData : list) {
				if (entityData != null) {
					map[i] = entityData;
					i++;
				}
			}
		}

		mapData.entityList = map;

		return mapData;
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

	public int getEntityID (int playerID) {
		int entityID = entityIDMap.getOrDefault(playerID, -1);
		return entityID;
	}

	public InputHandler getInputHandler () {
		return inputHandler;
	}

	public MapDataHandler getMapDataHandler () {
		return mapDataHandler;
	}

	public Queue<Runnable> getCommandQueue () {
		return commandQueue;
	}
}

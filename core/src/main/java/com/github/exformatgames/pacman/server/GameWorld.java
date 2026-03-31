package com.github.exformatgames.pacman.server;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.github.exformatgames.pacman.server.data.EntityData;
import com.github.exformatgames.pacman.server.data.EntityType;
import com.github.exformatgames.pacman.server.data.GameField;
import com.github.exformatgames.pacman.server.data.InputAction;
import com.github.exformatgames.pacman.server.data.MapData;
import com.github.exformatgames.pacman.server.data.PositionData;
import com.github.exformatgames.pacman.server.ecs.EntityBuilder;
import com.github.exformatgames.pacman.server.ecs.GameService;
import com.github.exformatgames.pacman.server.ecs.components.input.KeyPressedComponent;
import com.github.exformatgames.pacman.server.ecs.entities.FoodEntityBuilder;
import com.github.exformatgames.pacman.server.ecs.entities.PacmanEntityBuilder;
import com.github.exformatgames.pacman.server.ecs.systems.DirectionSystem;
import com.github.exformatgames.pacman.server.ecs.systems.FoodPeekUpSystem;
import com.github.exformatgames.pacman.server.ecs.systems.MoveSystem;
import com.github.exformatgames.pacman.server.ecs.systems.PressedKeySystem;
import com.github.exformatgames.pacman.server.ecs.systems.ReleasedKeySystem;
import com.github.exformatgames.pacman.server.ecs.utils.PacmanSpawnPoint;
import com.github.exformatgames.pacman.server.net.NetManager;

import java.util.HashMap;
import java.util.Map;

public class GameWorld implements GameService {
	private NetManager netManager;

	private final MapData mapData;

	private final Map<Integer, Integer> entityIDMap = new HashMap<>();
    private final Map<Integer, EntityData> entityMap = new HashMap<>();

	private final World world;

	private final PacmanEntityBuilder pacmanEB = new PacmanEntityBuilder();
	private final FoodEntityBuilder foodEB = new FoodEntityBuilder();

	private final GameField field;

	public GameWorld (MapData mapData) {
		this.mapData = mapData;

		field = new GameField(mapData);

		world = new World(
			new WorldConfigurationBuilder().
			alwaysDelayComponentRemoval(true).
			with(new BaseSystem[] {
					 new PressedKeySystem(),
					 new ReleasedKeySystem(),
					 new DirectionSystem(field),
					 new FoodPeekUpSystem(field, this),
					 new MoveSystem(field, this)
				 }).build());

		EntityBuilder.artemisWorld = world;


		for (EntityData entityData : mapData.entityList) {
			if (entityData != null && entityData.type == EntityType.FOOD) {
				foodEB.build(entityData);
			}
		}
	}


	public void update (float dT) {
		world.setDelta(dT);
		world.process();
	}

	@Override
	public void pressedButton (int playerID, InputAction action) {
		pacmanEB.addComponent(KeyPressedComponent.class);
	}

	@Override
    public void releaseButton (int playerID, InputAction action) {
		pacmanEB.addComponent(KeyPressedComponent.class);
	}

	//pacmanID = playerID
	@Override
	public void addPlayer (int ID) {
		EntityData data = new EntityData();
		data.ID = ID;
		data.type = EntityType.PACMAN;
		PositionData position = PacmanSpawnPoint.get(mapData);
		data.position.x = position.x;
		data.position.y = position.y;

		int entityID = pacmanEB.build(data);
		entityIDMap.put(ID, entityID);
		entityMap.put(ID, data);

		createdEntity(data);
    }

	@Override
	public void removePlayer (int ID) {
		EntityData data = entityMap.remove(ID);
		int entityID = entityIDMap.remove(ID);
		world.delete(entityID);
		removedEntity(data);
	}

	@Override
    public void createdEntity (EntityData data) {
        netManager.getNetService().createdEntity(data);
    }

	@Override
    public void positionChanged (EntityData data) {
        netManager.getNetService().positionChanged(data);
    }

	@Override
    public void removedEntity (EntityData data) {
        netManager.getNetService().removedEntity(data);
    }

	@Override
	public MapData getMapData () {
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
}

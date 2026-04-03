package com.github.exformatgames.pacman.server;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.github.exformatgames.pacman.server.data.EntityData;
import com.github.exformatgames.pacman.server.data.EntityType;
import com.github.exformatgames.pacman.server.data.GameField;
import com.github.exformatgames.pacman.server.data.InputData;
import com.github.exformatgames.pacman.server.data.MapData;
import com.github.exformatgames.pacman.server.data.PositionData;
import com.github.exformatgames.pacman.server.ecs.EntityBuilder;
import com.github.exformatgames.pacman.server.ecs.components.input.KeyPressedComponent;
import com.github.exformatgames.pacman.server.ecs.components.input.KeyReleasedComponent;
import com.github.exformatgames.pacman.server.ecs.entities.FoodEntityBuilder;
import com.github.exformatgames.pacman.server.ecs.entities.PacmanEntityBuilder;
import com.github.exformatgames.pacman.server.ecs.systems.DirectionSystem;
import com.github.exformatgames.pacman.server.ecs.systems.FoodPeekUpSystem;
import com.github.exformatgames.pacman.server.ecs.systems.MoveSystem;
import com.github.exformatgames.pacman.server.ecs.systems.PressedKeySystem;
import com.github.exformatgames.pacman.server.ecs.systems.ReleasedKeySystem;
import com.github.exformatgames.pacman.server.ecs.utils.PacmanSpawnPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.github.exformatgames.pacman.server.netty.NettyServer;
import com.github.exformatgames.pacman.server.netty.packet.ResponseGameMapPacket;

public class GameWorld {
    private final MapData mapData;
    private final GameField field;
    private final World world;
	private NettyServer server;

    private final Map<Integer, Integer> entityIDMap = new HashMap<>();
    private final Map<Integer, EntityData> entityMap = new HashMap<>();
    private final ArrayList<Integer> playerIDList = new ArrayList<>();

    private final PacmanEntityBuilder pacmanEB = new PacmanEntityBuilder();
    private final FoodEntityBuilder foodEB = new FoodEntityBuilder();

    // ===== Очередь команд для потокобезопасности =====
    private final Queue<Runnable> commandQueue = new ConcurrentLinkedQueue<>();

    public GameWorld(MapData mapData) {
        this.mapData = mapData;
		this.server = server;

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

    public void update(float dT) {
        // ===== выполняем все команды от внешних потоков =====
        while (!commandQueue.isEmpty()) {
            Runnable command = commandQueue.poll();
            if (command != null) {
                command.run();
            }
        }

        world.setDelta(dT);
        world.process();
    }

    public void pressedButton(final int playerID, final InputData action) {
        commandQueue.add(new Runnable() {
            @Override
            public void run() {
                Integer entityID = entityIDMap.get(playerID);
                if (entityID != null) {
                    pacmanEB.addComponent(entityID, KeyPressedComponent.class);
                }
            }
        });
    }

    public void releaseButton(final int playerID, final InputData action) {
        commandQueue.add(new Runnable() {
            @Override
            public void run() {
                Integer entityID = entityIDMap.get(playerID);
                if (entityID != null) {
                    pacmanEB.addComponent(entityID, KeyReleasedComponent.class);
                }
            }
        });
    }

	//pacmanID = playerID
    public void addPlayer(final int ID) {
        commandQueue.add(new Runnable() {
            @Override
            public void run() {
                playerIDList.add(ID);

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
        });
    }

    public void removePlayer(final int ID) {
        commandQueue.add(new Runnable() {
            @Override
            public void run() {
                playerIDList.remove((Integer)ID);

                EntityData data = entityMap.remove(ID);
                Integer entityID = entityIDMap.remove(ID);

                if (entityID != null) {
                    world.delete(entityID);
                }

                if (data != null) {
                    removedEntity(data);
                }
            }
        });
    }

    public void createdEntity (EntityData data) {
        //netManager.getNetService().createdEntity(data);
    }

    public void positionChanged (EntityData data) {
        //netManager.getNetService().positionChanged(data);
    }

    public void removedEntity (EntityData data) {
        //netManager.getNetService().removedEntity(data);
    }

	//я долго думал как его обозвать чтоб геймворлд был вне сетевого контекста... нуну..
	public void requestGameMap(final int clientID) {
		commandQueue.add(new Runnable() {
				@Override
				public void run() {
					MapData mapData = getMapData();
					//ой ля..... гуано крокодила пахнет краше...
					//сделаем вид что сервер прлностью абстрагироаан от игры, как и игра от сервера)))
					//это ж временное решение, я верну нетменеджер
					ResponseGameMapPacket packet = new ResponseGameMapPacket();
					packet.map = mapData;
					server.sendTo(clientID, packet);
				}
			});
	}

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

	public void setServer (NettyServer server) {
		this.server = server;
	}

	public NettyServer getServer () {
		return server;
	}
}

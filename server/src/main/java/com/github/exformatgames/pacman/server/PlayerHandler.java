package com.github.exformatgames.pacman.server;

import com.github.exformatgames.pacman.server.ecs.entities.PacmanEntityBuilder;
import com.github.exformatgames.pacman.server.ecs.utils.PacmanSpawnPoint;
import data.EntityData;
import data.EntityType;
import data.PositionData;

public class PlayerHandler {

	private final GameWorld gameWorld;
	private final PacmanEntityBuilder pacmanEB;

	public PlayerHandler (GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		pacmanEB = new PacmanEntityBuilder();
	}

	//pacmanID = playerID
    public void addPlayer (final int ID) {
        gameWorld.addCommand(new Runnable() {
				@Override
				public void run () {
					EntityData data = new EntityData();
					data.ID = ID;
					data.type = EntityType.PACMAN;
                    data.position = new PositionData();
					PositionData position = PacmanSpawnPoint.get(gameWorld.getMapData());
					data.position.x = position.x;
					data.position.y = position.y;
					int entityID = pacmanEB.build(data);

					gameWorld.getPlayerIDList().add(ID);
					gameWorld.getEntityIDMap().put(ID, entityID);
					gameWorld.getEntityMap().put(ID, data);
                    gameWorld.getField().getMap()[position.x][position.y] = data;

					gameWorld.getEntityEventHandler().createdEntity(data);
				}
			});
    }

    public void removePlayer (final int ID) {
        gameWorld.addCommand(new Runnable() {
				@Override
				public void run () {
					gameWorld.getPlayerIDList().remove((Integer)ID);

					EntityData data = gameWorld.getEntityMap().remove(ID);
					int entityID = gameWorld.getEntityIDMap().getOrDefault(ID, -1);

					if (entityID != -1) {
						gameWorld.getWorld().delete(entityID);
						gameWorld.getEntityIDMap().remove(ID);
					}

					if (data != null) {
						gameWorld.getEntityEventHandler().removedEntity(data);
					}
				}
			});
    }
}

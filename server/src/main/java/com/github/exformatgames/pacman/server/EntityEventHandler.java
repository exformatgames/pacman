package com.github.exformatgames.pacman.server;

import com.github.exformatgames.pacman.server.data.EntityData;

public class EntityEventHandler {
	
	private final GameWorld gameWorld;

	public EntityEventHandler (GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	public void createdEntity (EntityData data) {
		for (int playerID : gameWorld.getPlayerIDList()) {
			gameWorld.getNetService().getSendService().sendEntityCreated(playerID, data);
		}
    }

    public void transformedEntity (EntityData data) {
        for (int playerID : gameWorld.getPlayerIDList()) {
			gameWorld.getNetService().getSendService().sendEntityTransformed(playerID, data);
		}
    }

    public void removedEntity (EntityData data) {
        for (int playerID : gameWorld.getPlayerIDList()) {
			gameWorld.getNetService().getSendService().sendEntityRemoved(playerID, data);
		}
    }
}

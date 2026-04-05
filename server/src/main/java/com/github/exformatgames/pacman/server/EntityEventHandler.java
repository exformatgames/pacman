package com.github.exformatgames.pacman.server;

import data.EntityData;

public class EntityEventHandler {

	private final GameWorld gameWorld;

	public EntityEventHandler (GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public void createdEntity (EntityData data) {
        System.out.println("EntityEventHandler: entity created");
        for (int playerID : gameWorld.getPlayerIDList()) {
			gameWorld.getNetService().getSendService().sendEntityCreated(playerID, data);
		}
    }

    public void removedEntity (EntityData data) {
        System.out.println("EntityEventHandler: entity removed");
        for (int playerID : gameWorld.getPlayerIDList()) {
            gameWorld.getNetService().getSendService().sendEntityRemoved(playerID, data);
        }
    }

    public void transformedEntity (EntityData data) {
        System.out.println("EntityEventHandler: entity transformed");
        for (int playerID : gameWorld.getPlayerIDList()) {
			gameWorld.getNetService().getSendService().sendEntityTransformed(playerID, data);
		}
    }
}

package com.github.exformatgames.pacman.server;

import com.github.exformatgames.pacman.server.ecs.EntityBuilder;
import com.github.exformatgames.pacman.server.ecs.components.input.KeyPressedComponent;
import com.github.exformatgames.pacman.server.ecs.components.input.KeyReleasedComponent;
import data.InputData;

public class GameInputHandler {

	private final GameWorld gameWorld;

	public GameInputHandler(GameWorld gameWorld) {
		this.gameWorld = gameWorld;
	}

	public void pressedButton (final int playerID, final InputData action) {
        gameWorld.addCommand(new Runnable() {
				@Override
				public void run () {
					int entityID = gameWorld.getEntityID(playerID);
					if (entityID != -1) {
						EntityBuilder.addComponent(entityID, KeyPressedComponent.class).action = action;
					}
				}
			});
    }

	public void releaseButton (final int playerID, final InputData action) {
        gameWorld.addCommand(new Runnable() {
				@Override
				public void run () {
                    int entityID = gameWorld.getEntityID(playerID);
					if (entityID != -1) {
						EntityBuilder.addComponent(entityID, KeyReleasedComponent.class).action = action;
					}
				}
			});
    }
}

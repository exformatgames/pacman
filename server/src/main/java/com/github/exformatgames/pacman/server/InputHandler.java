package com.github.exformatgames.pacman.server;

import com.github.exformatgames.pacman.server.data.InputData;
import com.github.exformatgames.pacman.server.ecs.EntityBuilder;
import com.github.exformatgames.pacman.server.ecs.components.input.KeyPressedComponent;
import com.github.exformatgames.pacman.server.ecs.components.input.KeyReleasedComponent;
import java.util.Queue;

public class InputHandler {

	private final GameWorld gameWorld;

	private final Queue<Runnable> commandQueue;

	public InputHandler (GameWorld gameWorld) {
		this.gameWorld = gameWorld;
		commandQueue = gameWorld.getCommandQueue();
	}
	
	public void pressedButton (final int playerID, final InputData action) {
        commandQueue.add(new Runnable() {
				@Override
				public void run () {
					int entityID = gameWorld.getEntityID(playerID);
					if (entityID != -1) {
						EntityBuilder.addComponent(entityID, KeyPressedComponent.class);
					}
				}
			});
    }

	public void releaseButton (final int playerID, final InputData action) {
        commandQueue.add(new Runnable() {
				@Override
				public void run () {
					int entityID = gameWorld.getEntityID(playerID);
					if (entityID != -1) {
						EntityBuilder.addComponent(entityID, KeyReleasedComponent.class);
					}
				}
			});
    }
}

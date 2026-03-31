package com.github.exformatgames.pacman.server.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.github.exformatgames.pacman.server.ecs.components.input.KeyPressedComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.DirectionDownComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.DirectionLeftComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.DirectionRightComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.DirectionUpComponent;

public class PressedKeySystem extends IteratingSystem {

	private ComponentMapper<KeyPressedComponent> inputMapper;
	private ComponentMapper<DirectionLeftComponent> moveLeftMapper;
	private ComponentMapper<DirectionRightComponent> moveRightMapper;
	private ComponentMapper<DirectionUpComponent> moveUpMapper;
	private ComponentMapper<DirectionDownComponent> moveDownMapper;

	public PressedKeySystem () {
		super(Aspect.all(KeyPressedComponent.class));
	}

	@Override
	protected void process (int entityID) {
		KeyPressedComponent keyPressedComponent = inputMapper.get(entityID);

		switch (keyPressedComponent.action) {
			case MOVE_UP : {
					moveUpMapper.create(entityID);
					break;
				}
			case MOVE_DOWN : {
					moveDownMapper.create(entityID);
					break;
				}
			case MOVE_LEFT : {
					moveLeftMapper.create(entityID);
					break;
				}
			case MOVE_RIGHT : {
					moveRightMapper.create(entityID);
					break;
				}
		}

		inputMapper.remove(entityID);
	}
}

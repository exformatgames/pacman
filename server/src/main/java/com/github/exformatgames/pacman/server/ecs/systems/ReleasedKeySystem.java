package com.github.exformatgames.pacman.server.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.github.exformatgames.pacman.server.ecs.components.input.KeyReleasedComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.DirectionDownComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.DirectionLeftComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.DirectionRightComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.DirectionUpComponent;

public class ReleasedKeySystem extends IteratingSystem {

	private ComponentMapper<KeyReleasedComponent> inputMapper;
	private ComponentMapper<DirectionLeftComponent> moveLeftMapper;
	private ComponentMapper<DirectionRightComponent> moveRightMapper;
	private ComponentMapper<DirectionUpComponent> moveUpMapper;
	private ComponentMapper<DirectionDownComponent> moveDownMapper;

	public ReleasedKeySystem () {
		super(Aspect.all(KeyReleasedComponent.class));
	}

	@Override
	protected void process (int entityID) {
		KeyReleasedComponent keyReleasedComponent = inputMapper.get(entityID);

		switch (keyReleasedComponent.action) {
			case MOVE_UP : {
					moveUpMapper.remove(entityID);
					break;
				}
			case MOVE_DOWN : {
					moveDownMapper.remove(entityID);
					break;
				}
			case MOVE_LEFT : {
					moveLeftMapper.remove(entityID);
					break;
				}
			case MOVE_RIGHT : {
					moveRightMapper.remove(entityID);
					break;
				}
		}

		inputMapper.remove(entityID);
	}
}

package com.github.exformatgames.pacman.server.ecs.entities;

import data.EntityData;
import com.github.exformatgames.pacman.server.ecs.EntityBuilder;
import com.github.exformatgames.pacman.server.ecs.components.FoodComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.PositionComponent;

public class FoodEntityBuilder extends EntityBuilder {

	public int build (EntityData data) {
		createEntity();

		addComponent(FoodComponent.class);
		addComponent(PositionComponent.class).set(data.position.x, data.position.y);

		return entityID;
	}
}

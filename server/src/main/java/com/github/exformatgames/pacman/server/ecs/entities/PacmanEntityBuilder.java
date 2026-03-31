package com.github.exformatgames.pacman.server.ecs.entities;

import com.github.exformatgames.pacman.server.ecs.EntityBuilder;
import com.github.exformatgames.pacman.server.data.EntityData;
import com.github.exformatgames.pacman.server.ecs.components.PacmanComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.DirectionComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.PositionComponent;

public class PacmanEntityBuilder extends EntityBuilder {

	public int build(EntityData data) {
        createEntity();

		addComponent(DirectionComponent.class);
        addComponent(PacmanComponent.class).ID = data.ID;
        addComponent(PositionComponent.class).set(data.position.x, data.position.y);

        return entityID;
    }
}

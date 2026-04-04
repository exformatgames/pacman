package com.github.exformatgames.pacman.ecs.entities;

import com.github.exformatgames.pacman.ecs.EntityBuilder;
import com.github.exformatgames.pacman.ecs.components.game.PacmanComponent;
import com.github.exformatgames.pacman.ecs.components.render.SpriteComponent;
import com.github.exformatgames.pacman.ecs.components.transform.PositionComponent;
import data.EntityData;

public class PacmanEntityBuilder extends EntityBuilder {

	public int build(EntityData data) {
        createEntity();
        addComponent(PacmanComponent.class).ID = data.ID;
        addComponent(PositionComponent.class).position.set(data.position.x, data.position.y);
        addComponent(SpriteComponent.class)
            .init(assets.getGraphics().getTextureRegion("pacman"))
            .setPosition(data.position.x, data.position.y)
            .setSize(1)
            .setOriginCenter();

        return entityID;
    }
}

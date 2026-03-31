package com.github.exformatgames.pacman.ecs.entities;

import com.github.exformatgames.pacman.data.EntityData;
import com.github.exformatgames.pacman.ecs.EntityBuilder;
import com.github.exformatgames.pacman.ecs.components.render.SpriteComponent;

public class WallEntityBuilder extends EntityBuilder {
    public int build(EntityData data) {
        createEntity();

        addComponent(SpriteComponent.class)
            .init(assets.getGraphics().getTextureRegion("wall-center_center"))
            .setPosition(data.position.x, data.position.y)
            .setSize(1)
            .setOriginCenter();

        return entityID;
    }
}

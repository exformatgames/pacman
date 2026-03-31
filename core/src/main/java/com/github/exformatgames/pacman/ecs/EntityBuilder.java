package com.github.exformatgames.pacman.ecs;

import com.artemis.PooledComponent;
import com.artemis.World;
import com.github.exformatgames.pacman.Assets.Assets;

public class EntityBuilder {

    public static World artemisWorld;
    public static Assets assets;

    protected int entityID;

    public int createEntity() {
        entityID = artemisWorld.create();
        return entityID;
    }

    public <T extends PooledComponent> T addComponent(Class<T> componentType) {
        T type = artemisWorld.edit(entityID).create(componentType);

        return type;
    }

    public static <T extends PooledComponent> T addComponent(int entityID, Class<T> componentType) {
        T type = artemisWorld.edit(entityID).create(componentType);

        return type;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public int getEntityID() {
        return entityID;
    }
}

package com.github.exformatgames.pacman.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.github.exformatgames.pacman.ecs.components.game.EntityRemoveComponent;

public class EntityRemoveSystem extends IteratingSystem {

    private ComponentMapper<EntityRemoveComponent> removeMapper;

    public EntityRemoveSystem() {
        super(Aspect.all(EntityRemoveComponent.class));
    }

    @Override
    protected void process(int entityId) {
        EntityRemoveComponent removeComponent = removeMapper.get(entityId);

        if ((removeComponent.timer -= getWorld().getDelta()) <= 0) {
            getWorld().delete(entityId);
        }
    }
}

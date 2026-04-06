package com.github.exformatgames.pacman.ecs.components.game;

import com.artemis.PooledComponent;

public class EntityRemoveComponent extends PooledComponent {
    public float timer;

    @Override
    protected void reset() {
        timer = 0;
    }
}

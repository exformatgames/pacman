package com.github.exformatgames.pacman.ecs.components.game;

import com.artemis.PooledComponent;

public class FoodComponent extends PooledComponent {
    public int ID;

    @Override
    protected void reset() {
        ID = -1;
    }
}

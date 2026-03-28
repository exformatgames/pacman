package com.github.exformatgames.pacman.ecs.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent extends PooledComponent {

    public final Vector2 position = new Vector2();

    @Override
    protected void reset() {
        position.setZero();
    }
}

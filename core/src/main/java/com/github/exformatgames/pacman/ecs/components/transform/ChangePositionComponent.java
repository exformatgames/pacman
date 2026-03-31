package com.github.exformatgames.pacman.ecs.components.transform;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

public class ChangePositionComponent extends PooledComponent {
    public final Vector2 position = new Vector2();
    public float speed = 0;

    @Override
    protected void reset() {
        position.setZero();
        speed = 0;
    }
}

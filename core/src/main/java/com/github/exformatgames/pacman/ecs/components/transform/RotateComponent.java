package com.github.exformatgames.pacman.ecs.components.transform;

import com.artemis.PooledComponent;

public class RotateComponent extends PooledComponent {

    public float angle = 0;

    @Override
    protected void reset() {
        angle = 0;
    }
}

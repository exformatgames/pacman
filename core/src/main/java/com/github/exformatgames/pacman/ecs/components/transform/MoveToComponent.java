package com.github.exformatgames.pacman.ecs.components.transform;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

public class MoveToComponent extends PooledComponent {

    public Vector2 targetPosition = new Vector2();
    public Vector2 oldPosition = new Vector2();

	public float speed = 0;

    @Override
    protected void reset() {
        targetPosition.setZero();
		oldPosition.setZero();
		speed = 0;
    }
}

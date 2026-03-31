package com.github.exformatgames.pacman.server.ecs.components.transform;

import com.artemis.PooledComponent;

public class DirectionComponent extends PooledComponent {

	public Direction target = Direction.NONE;

	@Override
	protected void reset () {
		target =Direction.NONE;
	}
	
	public enum Direction {
		NONE,
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
}

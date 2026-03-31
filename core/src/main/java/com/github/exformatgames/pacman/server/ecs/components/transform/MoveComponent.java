package com.github.exformatgames.pacman.server.ecs.components.transform;

import com.artemis.PooledComponent;

public class MoveComponent extends PooledComponent {

	public int x = 0;
	public int y = 0;

	public MoveComponent set (int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	
	@Override
	protected void reset () {
		x = 0;
		y = 0;
	}
}

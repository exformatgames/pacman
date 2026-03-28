package com.github.exformatgames.pacman.server.ecs.components.transform;

import com.artemis.PooledComponent;

public class PositionComponent extends PooledComponent {

	public int x = 0;
	public int y = 0;

	@Override
	protected void reset () {
		x = 0;
		y = 0;
	}
}

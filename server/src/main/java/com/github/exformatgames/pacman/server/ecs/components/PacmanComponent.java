package com.github.exformatgames.pacman.server.ecs.components;

import com.artemis.PooledComponent;

public class PacmanComponent extends PooledComponent {

	public int ID = -1;
	
	@Override
	protected void reset () {
		ID = -1;
	}
}

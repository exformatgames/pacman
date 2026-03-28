package com.github.exformatgames.pacman.server.ecs.components;

import com.artemis.PooledComponent;

public class PacmanComponent extends PooledComponent {

	public int playerID = -1;
	
	@Override
	protected void reset () {
		playerID = -1;
	}
}

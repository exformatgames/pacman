package com.github.exformatgames.pacman.server.ecs.components.input;

import com.artemis.PooledComponent;
import com.github.exformatgames.pacman.server.data.InputData;

public class KeyReleasedComponent extends PooledComponent {

	public InputData action;

	@Override
	protected void reset () {
		action = null;
	}
}

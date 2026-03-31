package com.github.exformatgames.pacman.server.ecs.components.input;

import com.artemis.PooledComponent;
import com.github.exformatgames.pacman.server.data.InputAction;

public class KeyReleasedComponent extends PooledComponent {

	public InputAction action;

	@Override
	protected void reset () {
		action = null;
	}
}

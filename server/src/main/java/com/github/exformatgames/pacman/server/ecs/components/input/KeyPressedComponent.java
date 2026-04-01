package com.github.exformatgames.pacman.server.ecs.components.input;

import com.artemis.PooledComponent;

public class KeyPressedComponent extends PooledComponent {

	public InputAction action;

	@Override
	protected void reset () {
		action = null;
	}
}

package com.github.exformatgames.pacman.server.ecs.components.input;

import com.artemis.PooledComponent;
import data.InputData;

public class KeyPressedComponent extends PooledComponent {

	public InputData action;

	@Override
	protected void reset () {
		action = null;
	}
}

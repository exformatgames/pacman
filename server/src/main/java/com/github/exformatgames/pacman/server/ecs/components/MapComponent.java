package com.github.exformatgames.pacman.server.ecs.components;

import com.artemis.PooledComponent;
import data.GameField;
import data.MapData;

public class MapComponent extends PooledComponent {

	public MapData mapData;
	public GameField field;

	@Override
	protected void reset () {
		mapData = null;
		field = null;
	}
}

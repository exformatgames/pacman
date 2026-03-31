package com.github.exformatgames.pacman.server.ecs.components;

import com.artemis.PooledComponent;
import com.github.exformatgames.pacman.server.data.GameField;
import com.github.exformatgames.pacman.server.data.MapData;

public class MapComponent extends PooledComponent {

	public MapData mapData;
	public GameField field;
	
	@Override
	protected void reset () {
		mapData = null;
		field = null;
	}
}

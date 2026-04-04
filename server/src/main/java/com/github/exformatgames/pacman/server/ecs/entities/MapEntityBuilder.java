package com.github.exformatgames.pacman.server.ecs.entities;

import com.github.exformatgames.pacman.server.ecs.EntityBuilder;
import data.GameField;
import data.MapData;
import com.github.exformatgames.pacman.server.ecs.components.MapComponent;

public class MapEntityBuilder extends EntityBuilder {

	public void build (MapData data, GameField field) {
		createEntity();
		MapComponent mapComponent = addComponent(MapComponent.class);
		mapComponent.mapData = data;
		mapComponent.field = field;

	}
}

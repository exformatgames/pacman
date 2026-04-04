package com.github.exformatgames.pacman.server.ecs.entities;

import com.github.exformatgames.pacman.server.ecs.EntityBuilder;
import com.github.exformatgames.pacman.server.ecs.components.MapComponent;
import data.GameField;
import data.MapData;

public class MapEntityBuilder extends EntityBuilder {

	public void build (MapData data, GameField field) {
		createEntity();
		MapComponent mapComponent = addComponent(MapComponent.class);
		mapComponent.mapData = data;
		mapComponent.field = field;

	}
}

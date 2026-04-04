package com.github.exformatgames.pacman.server.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.github.exformatgames.pacman.server.GameWorld;
import data.EntityData;
import data.EntityType;
import data.GameField;
import com.github.exformatgames.pacman.server.ecs.components.PacmanComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.MoveComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.PositionComponent;

public class FoodPeekUpSystem extends IteratingSystem {

	private final GameField field;
	private final GameWorld world;

	private ComponentMapper<PositionComponent> positionMapper;
	private ComponentMapper<MoveComponent> moveMapper;


	public FoodPeekUpSystem (GameField field, GameWorld world) {
		super(Aspect.all(PacmanComponent.class, PositionComponent.class, MoveComponent.class));
		this.field = field;
		this.world = world;
	}

	@Override
	protected void process (int entityID) {
		PositionComponent position = positionMapper.get(entityID);
		MoveComponent move = moveMapper.get(entityID);

		int x = position.x + move.x;
		int y = position.y + move.y;

		EntityData  data = field.getMap()[x][y];

		if (data != null && data.type == EntityType.FOOD) {
			world.getEntityEventHandler().removedEntity(data);
			field.getMap()[x][y] = null;
			getWorld().delete(entityID);
		}
	}
}

package com.github.exformatgames.pacman.server.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IntervalIteratingSystem;
import com.artemis.systems.IteratingSystem;
import com.github.exformatgames.pacman.server.GameWorld;
import com.github.exformatgames.pacman.server.ecs.GameConstants;
import com.github.exformatgames.pacman.server.ecs.components.transform.MoveComponent;
import com.github.exformatgames.pacman.server.ecs.components.transform.PositionComponent;
import data.EntityData;
import data.GameField;

public class MoveSystem extends IntervalIteratingSystem {

	private final GameWorld world;
	private final GameField field;

	private ComponentMapper<PositionComponent> positionMapper;
	private ComponentMapper<MoveComponent> moveMapper;


	public MoveSystem (GameField field, GameWorld world) {
		super(Aspect.all(MoveComponent.class), GameConstants.PACMAN_MOVE_TIME);
		this.field = field;
		this.world = world;
	}


	@Override
	protected void process (int entityID) {
        System.out.println("MoveSystem.process: entity transformed");
		PositionComponent position = positionMapper.get(entityID);
		MoveComponent move = moveMapper.get(entityID);

		EntityData entityData = field.getMap()[position.x][position.y];

        System.out.println("MoveSystem.process: entity: " + entityData.toString());


        position.x += move.x;
		position.y += move.y;
		entityData.position.x = position.x;
		entityData.position.y = position.y;

		field.getMap()[position.x][position.y] = entityData;
        field.getMap()[position.x - move.x][position.y - move.y] = null;


        moveMapper.remove(entityID);

		world.getEntityEventHandler().transformedEntity(entityData);
	}
}

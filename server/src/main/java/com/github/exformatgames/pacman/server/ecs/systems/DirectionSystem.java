package com.github.exformatgames.pacman.server.ecs.systems;

//TODO : весь запас ифов что у меня был, вложен всюда..
//оставь надеджу всяк сюда входящий....
//not work..... ???

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IntervalIteratingSystem;
import com.artemis.systems.IteratingSystem;
import com.github.exformatgames.pacman.server.ecs.GameConstants;
import com.github.exformatgames.pacman.server.ecs.components.transform.*;
import com.github.exformatgames.pacman.server.ecs.components.transform.DirectionComponent.Direction;
import data.EntityData;
import data.EntityType;
import data.GameField;

public class DirectionSystem extends IteratingSystem {

	private ComponentMapper<PositionComponent> positionMapper;
	private ComponentMapper<MoveComponent> moveMapper;
	private ComponentMapper<DirectionComponent> directionMapper;
	private ComponentMapper<DirectionLeftComponent> moveLeftMapper;
	private ComponentMapper<DirectionRightComponent> moveRightMapper;
	private ComponentMapper<DirectionUpComponent> moveUpMapper;
	private ComponentMapper<DirectionDownComponent> moveDownMapper;

	private final GameField field;

	public DirectionSystem (GameField field) {
		super(Aspect.one(DirectionLeftComponent.class, DirectionRightComponent.class, DirectionUpComponent.class, DirectionDownComponent.class));
		this.field = field;
	}

	@Override
	protected void process (int entityID) {
		PositionComponent position = positionMapper.get(entityID);
		DirectionComponent directionComponent = directionMapper.get(entityID);

		boolean up = moveUpMapper.has(entityID);
		boolean down = moveDownMapper.has(entityID);
		boolean left = moveLeftMapper.has(entityID);
		boolean right = moveRightMapper.has(entityID);

		boolean horizontalOppositeDir = right && left;
		boolean verticalOppositeDir = up && down;

		Direction target = Direction.NONE;

		if (horizontalOppositeDir && verticalOppositeDir) {
			System.out.println("create Black hole... ");
			return;
		}

		if (horizontalOppositeDir && ! (up || down)) { // tudy-sudy
			if (canMove(position, directionComponent.target)) {
				target = directionComponent.target;
			} else {
				if (directionComponent.target == Direction.LEFT && canMove(position, Direction.RIGHT)) {
					target = Direction.RIGHT;
				} else if (directionComponent.target == Direction.RIGHT && canMove(position, Direction.LEFT)) {
					target = Direction.LEFT;
				}
			}
		}

		if (verticalOppositeDir && ! (right || left)) { // jump jum jump so very high...
            if (canMove(position, directionComponent.target)) {
				target = directionComponent.target;
			} else {
				if (directionComponent.target == Direction.UP && canMove(position, Direction.DOWN)) {
					target = Direction.DOWN;
				} else if (directionComponent.target == Direction.DOWN && canMove(position, Direction.UP)) {
					target = Direction.UP;
				}
			}
		}

		if (target == Direction.NONE && up && (right || left)) { // up diagonal
			target = upDiagonal(right, left, position);
		}

		if (target == Direction.NONE && down && (right || left)) { // down diagonal
			target = downDiagonal(right, left, position);
		}

		if (target == Direction.NONE) {
			target = singleDir(up, down, right, left, position);
		}

		directionComponent.target = target;

		move(target, entityID);
	}


	private Direction singleDir (boolean up, boolean down, boolean right, boolean left, PositionComponent position) {
		Direction target = Direction.NONE;

		if (up) {
			if (canMove(position, Direction.UP)) {
				target = Direction.UP;
			}
		} else if (down) {
			if (canMove(position, Direction.DOWN)) {
				target = Direction.DOWN;
			}
		} else if (right) {
			if (canMove(position, Direction.RIGHT)) {
				target = Direction.RIGHT;
			}
		} else if (left) {
			if (canMove(position, Direction.LEFT)) {
				target = Direction.LEFT;
			}
		}

		return target;
	}

	private Direction upDiagonal (boolean right, boolean left, PositionComponent position) {
		Direction target = Direction.UP;
		if (canMove(position, target)) {
			return target; //move up
		} else {
			if (right) {
				target = Direction.RIGHT;
				if (canMove(position, target)) {
					return target;//move right
				}
			} else {
				target = Direction.LEFT;
				if (canMove(position, target)) {
					return target;//move left
				}
			}
		}
		return Direction.NONE;
	}

	private Direction downDiagonal (boolean right, boolean left, PositionComponent position) {
		Direction target = Direction.DOWN;
		if (canMove(position, target)) {
			return target; //move down
		} else {
			if (right) {
				target = Direction.RIGHT;
				if (canMove(position, target)) {
					return target;//move right
				}
			} else {
				target = Direction.LEFT;
				if (canMove(position, target)) {
					return target;//move left
				}
			}
		}
		return Direction.NONE;
	}

	private boolean canMove (PositionComponent positionComponent, Direction dir) {
		int x = positionComponent.x;
		int y = positionComponent.y;

		EntityData upCell = null;
		EntityData downCell = null;
		EntityData rightCell = null;
		EntityData leftCell = null;

		if (field.getMap()[x].length > y + 1) {
			upCell = field.getMap()[positionComponent.x][positionComponent.y + 1];
		}
		if (y - 1 >= 0) {
			downCell = field.getMap()[positionComponent.x][positionComponent.y - 1];
		}
		if (field.getMap().length > x + 1) {
			rightCell = field.getMap()[positionComponent.x + 1][positionComponent.y];
		}
		if (x - 1 >= 0) {
			leftCell = field.getMap()[positionComponent.x - 1][positionComponent.y];
		}

		switch (dir) {
			case UP:
				return upCell == null || upCell.type != EntityType.WALL;
			case DOWN:
				return downCell == null || downCell.type != EntityType.WALL;
			case LEFT:
				return leftCell == null || leftCell.type != EntityType.WALL;
			case RIGHT:
				return rightCell == null || rightCell.type != EntityType.WALL;
		}

		return false;
	}

	private void move (Direction dir, int entityID) {
		switch (dir) {
			case UP : {
					moveMapper.create(entityID).y = 1;
					break;
				}
			case DOWN : {
					moveMapper.create(entityID).y = -1;
					break;
				}
			case LEFT : {
					moveMapper.create(entityID).x = -1;
					break;
				}
			case RIGHT : {
					moveMapper.create(entityID).x = 1;
					break;
				}
		}
	}
}

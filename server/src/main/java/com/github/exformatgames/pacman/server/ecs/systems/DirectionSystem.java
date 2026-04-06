package com.github.exformatgames.pacman.server.ecs.systems;

//TODO : весь запас ифов что у меня был, вложен всюда..
//оставь надеджу всяк сюда входящий....

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
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

    //TODO при активном спаме интпута, пускает в стену..
	private boolean canMove (PositionComponent positionComponent, Direction dir) {
		int x = positionComponent.x;
		int y = positionComponent.y;

        int targetX = x;
        int targetY = y;

        switch (dir) {
            case UP : targetY++; break;
            case DOWN : targetY--; break;
            case LEFT : targetX--; break;
            case RIGHT : targetX++; break;
        }

		if (targetX > 0 && targetX < field.getMap().length && targetY > 0 && targetY < field.getMap()[0].length) {
            EntityData entityData = field.getMap()[targetX][targetY];

            if (entityData == null) return true;

            if (entityData.type == EntityType.FOOD) return true;

            if (entityData.type == EntityType.WALL) return false;

            if (entityData.type == EntityType.PACMAN) return false;
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

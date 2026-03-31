package com.github.exformatgames.pacman.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.github.exformatgames.pacman.ecs.components.render.SpriteComponent;
import com.github.exformatgames.pacman.ecs.components.transform.MoveToComponent;
import com.github.exformatgames.pacman.ecs.components.transform.PositionComponent;

public class MoveSystem extends IteratingSystem {

    private ComponentMapper<MoveToComponent> moveMapper;
    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<SpriteComponent> spriteMapper;

	public MoveSystem() {
        super(Aspect.all(MoveToComponent.class));
    }


    @Override
    protected void process(int entityId) {
        PositionComponent positionComponent = positionMapper.get(entityId);
        MoveToComponent moveComponent = moveMapper.get(entityId);
        SpriteComponent spriteComponent = spriteMapper.get(entityId);

        float dT = getWorld().delta;

        float totalDeltaX = moveComponent.targetPosition.x - moveComponent.oldPosition.x;
        float totalDeltaY = moveComponent.targetPosition.y - moveComponent.oldPosition.y;

        Vector2 dir = Pools.obtain(Vector2.class).set(totalDeltaX, totalDeltaY).nor();
        float moveDistance = moveComponent.speed * dT;
		positionComponent.position.add(dir.x * moveDistance, dir.y * moveDistance);
        Pools.free(dir);

        if (Math.abs(totalDeltaX) > Math.abs(totalDeltaY)) {
            spriteComponent.setRotation(totalDeltaX > 0 ? 0 : 180);
        } else if (Math.abs(totalDeltaY) > 0) {
            spriteComponent.setRotation(totalDeltaY > 0 ? 90 : 270);
        }

        float remainingX = moveComponent.targetPosition.x - positionComponent.position.x;
        float remainingY = moveComponent.targetPosition.y - positionComponent.position.y;

        if (Math.abs(remainingX) < 0.1f && Math.abs(remainingY) < 0.1f) {
            positionComponent.position.set(moveComponent.targetPosition);
            moveMapper.remove(entityId);
        }

		spriteComponent.setPosition(positionComponent.position.x, positionComponent.position.y);
    }
}

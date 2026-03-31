package com.github.exformatgames.pacman.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.github.exformatgames.pacman.GameContext;
import com.github.exformatgames.pacman.ecs.components.render.AnimationComponent;
import com.github.exformatgames.pacman.ecs.components.transform.ChangePositionComponent;
import com.github.exformatgames.pacman.ecs.components.transform.MoveToComponent;
import com.github.exformatgames.pacman.ecs.components.transform.PositionComponent;
import com.badlogic.gdx.math.Vector2;

public class ChangePositionSystem extends IteratingSystem {

    private final GameContext context;

    private ComponentMapper<ChangePositionComponent> changeMapper;
    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<MoveToComponent> moveMapper;
    private ComponentMapper<AnimationComponent> animationMapper;

    public ChangePositionSystem(GameContext context) {
        super(Aspect.all(ChangePositionComponent.class));
        this.context = context;
    }

    @Override
    protected void process(int entityId) {
        ChangePositionComponent changePositionComponent = changeMapper.get(entityId);
        PositionComponent positionComponent = positionMapper.get(entityId);
        MoveToComponent moveComponent = moveMapper.create(entityId);
        AnimationComponent animationComponent = animationMapper.create(entityId);

        moveComponent.oldPosition.set(positionComponent.position);
        moveComponent.targetPosition.set(changePositionComponent.position);
        moveComponent.speed = changePositionComponent.speed;
		animationComponent.animation = context.getAssets().getAnimations().get("pacman-run");

        changeMapper.remove(entityId);
    }
}

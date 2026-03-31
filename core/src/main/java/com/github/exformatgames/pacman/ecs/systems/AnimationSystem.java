package com.github.exformatgames.pacman.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.exformatgames.pacman.ecs.components.render.AnimationComponent;
import com.github.exformatgames.pacman.ecs.components.render.SpriteComponent;

public class AnimationSystem extends IteratingSystem {

    private ComponentMapper<AnimationComponent> animationMapper;
    private ComponentMapper<SpriteComponent> spriteMapper;

    public AnimationSystem () {
        super(Aspect.all(AnimationComponent.class, SpriteComponent.class));
    }

    @Override
    protected void process(int entityId) {
        AnimationComponent animationComponent = animationMapper.get(entityId);
        SpriteComponent spriteComponent = spriteMapper.get(entityId);

		animationComponent.time += getWorld().delta;
		
        TextureRegion region = animationComponent.animation.getKeyFrame(animationComponent.time);
        spriteComponent.setUV(region);

        if (animationComponent.animation.isAnimationFinished(animationComponent.time)) {
            spriteComponent.setUV(animationComponent.animation.getKeyFrame(0));

            animationMapper.remove(entityId);
        }
    }
}

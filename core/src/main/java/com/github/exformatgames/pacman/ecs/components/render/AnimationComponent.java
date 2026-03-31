package com.github.exformatgames.pacman.ecs.components.render;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent extends PooledComponent {

    public Animation<TextureRegion> animation;
    public float time = 0;

    @Override
    protected void reset() {
        animation = null;
        time = 0;
    }
}

package com.github.exformatgames.pacman.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.exformatgames.pacman.ecs.components.render.SpriteComponent;

public class SpriteRenderSystem extends IteratingSystem {

    public static ComponentMapper<SpriteComponent> component;

    private final Viewport viewport;
    private final SpriteBatch spriteBatch;

    public SpriteRenderSystem(Viewport viewport, SpriteBatch spriteBatch) {
        super(Aspect.all(SpriteComponent.class));
        this.viewport = viewport;
        this.spriteBatch = spriteBatch;
    }

    @Override
    protected void begin() {
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
    }

    @Override
    protected void end() {
        spriteBatch.end();
    }

    @Override
    protected void process(int entityID) {
        SpriteComponent spriteComponent = component.get(entityID);

        if (spriteComponent.spriteComponentArray.isEmpty()) {
            spriteBatch.draw(spriteComponent.texture, spriteComponent.getVertices(), 0, SpriteComponent.SPRITE_SIZE);
        }
        else {
            for (SpriteComponent component : spriteComponent.spriteComponentArray) {
                spriteBatch.draw(component.texture, component.getVertices(), 0, SpriteComponent.SPRITE_SIZE);
            }
        }
    }
}

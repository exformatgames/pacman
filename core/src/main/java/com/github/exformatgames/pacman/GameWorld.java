package com.github.exformatgames.pacman;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.exformatgames.pacman.data.EntityData;
import com.github.exformatgames.pacman.data.EntityType;
import com.github.exformatgames.pacman.data.MapData;
import com.github.exformatgames.pacman.ecs.EntityBuilder;
import com.github.exformatgames.pacman.ecs.components.transform.ChangePositionComponent;
import com.github.exformatgames.pacman.ecs.entities.FoodEntityBuilder;
import com.github.exformatgames.pacman.ecs.entities.PacmanEntityBuilder;
import com.github.exformatgames.pacman.ecs.entities.WallEntityBuilder;
import com.github.exformatgames.pacman.ecs.systems.AnimationSystem;
import com.github.exformatgames.pacman.ecs.systems.ChangePositionSystem;
import com.github.exformatgames.pacman.ecs.systems.MoveSystem;
import com.github.exformatgames.pacman.ecs.systems.SoundSystem;
import com.github.exformatgames.pacman.ecs.systems.SpriteRenderSystem;
import com.github.exformatgames.pacman.managers.net.listeners.EntityCreatedListener;
import com.github.exformatgames.pacman.managers.net.listeners.EntityPositionChangedListener;
import com.github.exformatgames.pacman.managers.net.listeners.EntityRemovedListener;
import com.github.exformatgames.pacman.managers.net.listeners.ResponseMapListener;

public class GameWorld implements EntityCreatedListener, EntityRemovedListener, EntityPositionChangedListener, ResponseMapListener {

    private final World world;

    private final IntMap<Integer> pacmanMap = new IntMap<>();
    private final IntMap<Integer> foodMap = new IntMap<>();

    private final FoodEntityBuilder foodEntityBuilder = new FoodEntityBuilder();
    private final PacmanEntityBuilder pacmanEntityBuilder = new PacmanEntityBuilder();
    private final WallEntityBuilder wallEntityBuilder = new WallEntityBuilder();


    private final TestGameMap testGameMap;

	private final Viewport viewport;

    public GameWorld(GameContext context, Viewport viewport, SpriteBatch spriteBatch) {
		this.viewport = viewport;

        world = new World(
            new WorldConfigurationBuilder().
                alwaysDelayComponentRemoval(true).
                with(new BaseSystem[] {
                    new ChangePositionSystem(context),
                    new MoveSystem(),
                    new SoundSystem(context),
                    new AnimationSystem(),
                    new SpriteRenderSystem(viewport, spriteBatch)
                }).
                build());

        EntityBuilder.artemisWorld = world;
        EntityBuilder.assets = context.getAssets();

        context.getNetManager().addEntityCreatedListener(this);
        context.getNetManager().addEntityRemovedListener(this);
        context.getNetManager().addEntityPositionChangedListener(this);
        context.getNetManager().addResponseMapListener(this);


        testGameMap = new TestGameMap(this);
    }


    public void update(float dT) {
        testGameMap.update(dT);

        world.setDelta(dT);
        world.process();
    }

    @Override
    public void responseMap(MapData data) {
		viewport.setWorldSize(data.width, data.height);
		viewport.apply(true);

		for (EntityData entityData : data.entityList) {
			if (entityData != null) {
				createdEntity(entityData);
			}
		}
    }

    @Override
    public void createdEntity(EntityData data) {
        switch (data.type) {
            case PACMAN: {
                int entityID = pacmanEntityBuilder.build(data);
                pacmanMap.put(data.ID, entityID);
                break;
            }
            case FOOD: {
                int entityID = foodEntityBuilder.build(data);
                foodMap.put(data.ID, entityID);
                break;
            }
            case WALL: {
                wallEntityBuilder.build(data);
                break;
            }
        }
    }

    @Override
    public void positionChanged(EntityData data) {
        if (data.type == EntityType.PACMAN) {
            int entityID = pacmanMap.get(data.ID);
            ChangePositionComponent changePositionComponent = EntityBuilder.addComponent(entityID, ChangePositionComponent.class);
            changePositionComponent.position.set(data.position.x, data.position.y);
            changePositionComponent.speed = data.speed;
        }
    }

    @Override
    public void removedEntity(EntityData data) {
        switch (data.type) {
            case PACMAN: {
                int entityID = pacmanMap.get(data.ID);
                world.delete(entityID);
                pacmanMap.remove(data.ID);
                break;
            }
            case FOOD: {
                int entityID = foodMap.get(data.ID);
                world.delete(entityID);
                foodMap.remove(data.ID);
                break;
            }
        }
    }

    public void dispose() {
        world.dispose();
    }
}

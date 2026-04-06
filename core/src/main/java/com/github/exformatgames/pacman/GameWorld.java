package com.github.exformatgames.pacman;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.exformatgames.pacman.ecs.components.game.EntityRemoveComponent;
import com.github.exformatgames.pacman.ecs.systems.*;
import com.github.exformatgames.pacman.net.service.GameMapService;
import com.github.exformatgames.pacman.net.service.ServerGameEventService;
import com.github.exformatgames.pacman.ecs.EntityBuilder;
import com.github.exformatgames.pacman.ecs.components.transform.ChangePositionComponent;
import com.github.exformatgames.pacman.ecs.entities.FoodEntityBuilder;
import com.github.exformatgames.pacman.ecs.entities.PacmanEntityBuilder;
import com.github.exformatgames.pacman.ecs.entities.WallEntityBuilder;
import data.EntityData;
import data.EntityType;
import data.MapData;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GameWorld implements ServerGameEventService.EntityCreatedListener, ServerGameEventService.EntityRemovedListener, ServerGameEventService.EntityTransformedListener, GameMapService.GameMapReceivedListener {

    private World world;

    private final IntMap<Integer> pacmanMap = new IntMap<>();
    private final IntMap<Integer> foodMap = new IntMap<>();

    private final FoodEntityBuilder foodEntityBuilder = new FoodEntityBuilder();
    private final PacmanEntityBuilder pacmanEntityBuilder = new PacmanEntityBuilder();
    private final WallEntityBuilder wallEntityBuilder = new WallEntityBuilder();

	private final ConcurrentLinkedQueue<Runnable> eventQueue = new ConcurrentLinkedQueue<>();

	//for test reaction, render
    //private final TestGameMap testGameMap;

	private final Viewport viewport;
    private final SpriteBatch spriteBatch;

    public GameWorld(GameContext context, Viewport viewport, SpriteBatch spriteBatch) {
		this.viewport = viewport;
        this.spriteBatch = spriteBatch;
        EntityBuilder.assets = context.getAssets();


		context.getClient().setEventQueue(eventQueue);
        context.getClient().getGameEventService().addEntityCreatedListener(this);
        context.getClient().getGameEventService().addEntityTransformedListener(this);
        context.getClient().getGameEventService().addEntityRemovedListener(this);
        context.getClient().getGameMapService().addListener(this);

		//for test reaction, render
        //testGameMap = new TestGameMap(this);
    }

    public void show(GameContext context) {
        if (world != null) {
            world.dispose();
        }
        world = new World(
            new WorldConfigurationBuilder().
                alwaysDelayComponentRemoval(true).
                with(new BaseSystem[] {
                    new ChangePositionSystem(context),
                    new MoveSystem(),
                    new SoundSystem(context),
                    new AnimationSystem(),
                    new SpriteRenderSystem(viewport, spriteBatch),
                    new EntityRemoveSystem()
                }).
                build());

        EntityBuilder.artemisWorld = world;
    }

    public void update(float dT) {
		//TODO: for test reaction, render
        //testGameMap.update(dT);

		Runnable task;
		while ((task = eventQueue.poll()) != null) {
			task.run();
		}

        world.setDelta(dT);
        world.process();
    }


    @Override
    public void onGameMapReceived(MapData data) {
        viewport.setWorldSize(data.width, data.height);
        viewport.apply(true);

        for (EntityData entityData : data.entityList) {
            if (entityData != null) {
                onEntityCreated(entityData);
            }
        }
    }

    @Override
    public void onEntityCreated(EntityData data) {
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
    public void onEntityTransformed(EntityData data) {
        if (data.type == EntityType.PACMAN) {
            int entityID = pacmanMap.get(data.ID);
            ChangePositionComponent changePositionComponent = EntityBuilder.addComponent(entityID, ChangePositionComponent.class);
            changePositionComponent.position.set(data.position.x, data.position.y);
            changePositionComponent.speed = data.speed;
        }
    }

    @Override
    public void onEntityRemoved(EntityData data) {
        switch (data.type) {
            case PACMAN: {
                int entityID = pacmanMap.get(data.ID);
                world.delete(entityID);
                pacmanMap.remove(data.ID);
                break;
            }
            case FOOD: {
                int entityID = foodMap.get(data.ID);
                EntityBuilder.addComponent(entityID, EntityRemoveComponent.class).timer = 0.3f;
                //world.delete(entityID);
                foodMap.remove(data.ID);
                break;
            }
        }
    }

    public void dispose() {
        if (world != null) {
            world.dispose();
        }
    }
}

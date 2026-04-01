package com.github.exformatgames.pacman;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.exformatgames.pacman.client.service.GameMapService;
import com.github.exformatgames.pacman.client.service.ServerGameEventService;
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

public class GameWorld implements ServerGameEventService.EntityCreatedListener, ServerGameEventService.EntityRemovedListener, ServerGameEventService.EntityTransformedListener, GameMapService.GameMapReceivedListener {

    private final World world;

    private final IntMap<Integer> pacmanMap = new IntMap<>();
    private final IntMap<Integer> foodMap = new IntMap<>();

    private final FoodEntityBuilder foodEntityBuilder = new FoodEntityBuilder();
    private final PacmanEntityBuilder pacmanEntityBuilder = new PacmanEntityBuilder();
    private final WallEntityBuilder wallEntityBuilder = new WallEntityBuilder();

	//for test reaction, render
    //private final TestGameMap testGameMap;

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

        context.getClient().getGameEventService().addEntityCreatedListener(this);
        context.getClient().getGameMapService().addListener(this);

		//for test reaction, render
        //testGameMap = new TestGameMap(this);
    }


    public void update(float dT) {
		//TODO: for test reaction, render
        //testGameMap.update(dT);

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

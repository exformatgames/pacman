package com.github.exformatgames.pacman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.exformatgames.pacman.GameContext;
import com.github.exformatgames.pacman.GameWorld;
import com.github.exformatgames.pacman.UI.GameLayout;

public class GameScreen implements Screen {
    public static final String NAME = "GameScreen";
    private final GameContext context;
    private final Viewport uiViewport;
    private final Viewport gameViewport;
    private final Stage stage;
    private final GameWorld gameWorld;


    public GameScreen (GameContext context) {
        this.context = context;

        uiViewport = new ExtendViewport(360, 640);
        gameViewport = new FitViewport(30, 30);

        SpriteBatch batch = new SpriteBatch();

        stage = new Stage(uiViewport, batch);
        gameWorld = new GameWorld(context, gameViewport, batch);
        GameLayout layout = new GameLayout(context);

        stage.addActor(layout);

		layout.show();
		context.getLocalizationManager().add(layout);
    }

    @Override
    public void show () {
        context.getAudioManager().playGameMusic();
        gameWorld.show(context);
        context.getClient().getGameMapService().requestMap();
        context.getClient().getGameSessionService().joinGame();

		Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render (float dT) {
        ScreenUtils.clear(0, 0, 0, 1);

        gameWorld.update(dT);

        uiViewport.apply();
        stage.act(dT);
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        uiViewport.update(width, height, true);
        gameViewport.update(width, height, true);
    }

    @Override
    public void pause () {
        context.getAudioManager().stopMusic();
    }

    @Override
    public void resume () {
        context.getAudioManager().playMenuMusic();
    }

    @Override
    public void hide () {
        context.getAudioManager().stopMusic();
    }

    @Override
    public void dispose() {
        gameWorld.dispose();
        stage.dispose();
    }
}

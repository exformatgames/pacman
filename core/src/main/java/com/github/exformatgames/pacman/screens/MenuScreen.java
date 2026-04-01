package com.github.exformatgames.pacman.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.github.exformatgames.pacman.GameContext;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.exformatgames.pacman.UI.MenuLayout;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.exformatgames.pacman.UI.ExitLayout;

public class MenuScreen implements Screen {

	public static final String NAME = "MenuScreen";

	private final GameContext context;

	private final Stage stage;

    private float reconnectTimer = 0;
    private float reconnectTimeTick = 5;
    private int reconnectCounter = 5;
    private int reconnectMax = 10;


    public MenuScreen(GameContext context) {
		this.context = context;

		stage = new Stage(new FitViewport(360, 640));

		initLayouts();

		Gdx.input.setCatchKey(Input.Keys.BACK, true);
	}

	@Override
	public void show () {
		Gdx.input.setInputProcessor(stage);
        if ( ! context.getClient().getConnectionService().isConnected()) {
            context.getClient().getConnectionService().connect(context.getHost(), context.getPort());
        }
        context.getLayoutManager().show(MenuLayout.NAME);
        context.getAudioManager().playMenuMusic();
	}

	@Override
	public void render (float dT) {
        reconnectTimer += dT;
        if (reconnectTimer > reconnectTimeTick && ! context.getClient().getConnectionService().isConnected()) {
            reconnectTimer = 0;
            reconnectCounter++;
            if (reconnectCounter > reconnectMax) {
                context.getClient().getConnectionService().connect(context.getHost(), context.getPort());
            }
        }


		ScreenUtils.clear(Color.GRAY);
		stage.act(dT);
		stage.draw();
	}

	@Override
	public void resize (int width, int height) {
		stage.getViewport().apply();
		stage.getViewport().update(width, height);
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
	public void dispose () {
		stage.dispose();
        context.getClient().dispose();
        context.getAssets().dispose();
	}

	private void initLayouts() {
        MenuLayout menuLayout = new MenuLayout(context);
        ExitLayout exitLayout = new ExitLayout(context);

		stage.addActor(menuLayout);
		stage.addActor(exitLayout);

		context.getLocalizationManager().add(menuLayout);
		context.getLocalizationManager().add(exitLayout);

		context.getLayoutManager().add(menuLayout, MenuLayout.NAME);
		context.getLayoutManager().add(exitLayout, ExitLayout.NAME);

        context.getClient().addConnectionStatusListener(menuLayout);
        context.getClient().addConnectionStatusListener(menuLayout);
	}
}

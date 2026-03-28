package com.github.exformatgames.pacman.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.github.exformatgames.pacman.app.AppContext;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.exformatgames.pacman.UI.MainMenuLayout;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.exformatgames.pacman.UI.Layout;
import com.github.exformatgames.pacman.UI.AuthLayout;
import com.github.exformatgames.pacman.UI.ExitLayout;
import com.github.exformatgames.pacman.UI.WaitLayout;

public class MainMenuScreen implements Screen {

	public static final String NAME = "MainMenuScreen";

	private final AppContext context;

	private final Stage stage;

	private MainMenuLayout menuLayout;
	private AuthLayout authLayout;
	private ExitLayout exitLayout;
	private WaitLayout waitLayout;

	public MainMenuScreen (AppContext context) {
		this.context = context;

		stage = new Stage(new FitViewport(360, 640));

		initLayouts();

		Gdx.input.setCatchKey(Input.Keys.BACK, true);
	}

	@Override
	public void show () {
		Gdx.input.setInputProcessor(stage);
		context.getLayoutManager().show(MainMenuLayout.NAME);
		context.getAudioManager().playMenuMusic();
	}

	@Override
	public void render (float dT) {
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
		//stop music
	}

	@Override
	public void resume () {
		//play music
	}

	@Override
	public void hide () {
		//stop music
	}

	@Override
	public void dispose () {
		stage.dispose();
	}
	
	private void initLayouts() {
		menuLayout = new MainMenuLayout(context);
		authLayout = new AuthLayout(context);
		exitLayout = new ExitLayout(context);
		waitLayout = new WaitLayout(context);

		stage.addActor(menuLayout);
		stage.addActor(authLayout);
		stage.addActor(exitLayout);
		stage.addActor(waitLayout);

		context.getLocalizationManager().add(menuLayout);
		context.getLocalizationManager().add(authLayout);
		context.getLocalizationManager().add(exitLayout);
		context.getLocalizationManager().add(waitLayout);

		context.getLayoutManager().add(menuLayout, MainMenuLayout.NAME);
		context.getLayoutManager().add(authLayout, AuthLayout.NAME);
		context.getLayoutManager().add(exitLayout, ExitLayout.NAME);
		context.getLayoutManager().add(waitLayout, WaitLayout.NAME);
	}
}

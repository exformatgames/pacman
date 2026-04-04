package com.github.exformatgames.pacman.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.github.exformatgames.pacman.GameContext;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.github.exformatgames.pacman.UI.TestNetLayout;
import com.badlogic.gdx.Gdx;

public class NetTestScreen implements Screen {

	private final GameContext context;


	private Stage stage;
	private TestNetLayout layout;

	public NetTestScreen (GameContext context) {
		this.context = context;

		stage = new Stage(new FitViewport(480, 720));
		layout = new TestNetLayout(context);

		stage.addActor(layout);

	}



	@Override
	public void show () {
		layout.show();
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render (float dt) {
		ScreenUtils.clear(Color.GRAY);

		stage.act(dt);
		stage.draw();
	}

	@Override
	public void resize (int w, int h) {
		stage.getViewport().update(w, h, true);
	}

	@Override
	public void pause () {
		// TODO: Implement this method
	}

	@Override
	public void resume () {
		// TODO: Implement this method
	}

	@Override
	public void hide () {
		// TODO: Implement this method
	}

	@Override
	public void dispose () {
		// TODO: Implement this method
	}
}

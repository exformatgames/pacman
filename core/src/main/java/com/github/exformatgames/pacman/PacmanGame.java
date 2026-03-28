package com.github.exformatgames.pacman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.github.exformatgames.pacman.screens.StartLoadingScreen;
import java.util.HashMap;
import java.util.Map;

public class PacmanGame extends Game {

	public static final String TAG = "PacmanGame";

	private Map<String, Screen> screenMap = new HashMap<>();

	@Override
	public void create () {
		StartLoadingScreen startLoadingScreen = new StartLoadingScreen(this);

		setScreen(startLoadingScreen);
	}

	public void showScreen (String name) {
		Screen newScreen = screenMap.get(name);
		if (newScreen != null) {
			setScreen(newScreen);

			log("add screen: " + name);
		}
	}

	public void addScreen (Screen screen, String name) {
		if (screen != null && name != null) {
			screenMap.put(name, screen);

			log("add screen: " + name);
		}
	}

	private void log (String msg) {
		Gdx.app.log(TAG, msg);
	}

	@Override
	public void dispose () {
		for (Screen screen : screenMap.values()) {
			screen.dispose();
		}
		//assets.dispose();
	}
}

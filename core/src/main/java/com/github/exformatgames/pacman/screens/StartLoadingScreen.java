package com.github.exformatgames.pacman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.github.exformatgames.pacman.Assets.Assets;
import com.github.exformatgames.pacman.Assets.LoadingAssets;
import com.github.exformatgames.pacman.UI.StartLoadingLayout;
import com.github.exformatgames.pacman.managers.AppDataManager;
import com.github.exformatgames.pacman.managers.data.PreferencesAppDataService;
import com.github.exformatgames.pacman.PacmanGame;
import com.github.exformatgames.pacman.GameContext;
import com.github.exformatgames.pacman.managers.UIAudioManager;
import com.github.exformatgames.pacman.managers.LocalizationManager;
import com.github.exformatgames.pacman.managers.NetManager;
import com.github.exformatgames.pacman.data.AppData;
import com.github.exformatgames.pacman.managers.LayoutManager;

public class StartLoadingScreen implements Screen {

	public static final String NAME = "StartLoadingScreen";
	public static final String SKIN_PATH = "ui/loading/LoadingScreenSkin.json";

	private final PacmanGame game;

	private final Stage stage;

	private final AssetManager assetManager;
	private final LoadingAssets loadingAssets;

    private final AppDataManager appDataManager;

	private Skin skin;

	public StartLoadingScreen (PacmanGame game) {
		this.game = game;

		assetManager = new AssetManager();

		stage = new Stage(new FitViewport(1080, 1920));

		loadingAssets = new LoadingAssets(assetManager);
		appDataManager = new AppDataManager();

		PreferencesAppDataService preferencesService = new PreferencesAppDataService();
		preferencesService.setManager(appDataManager);

		appDataManager.setService(preferencesService);
	}

	@Override
	public void show () {
		skin = new Skin(Gdx.files.internal(SKIN_PATH));
		StartLoadingLayout layout = new StartLoadingLayout(skin);

		stage.addActor(layout);
		ScreenUtils.clear(Color.GRAY);
		stage.act();
		stage.draw();

		loadingAssets.loadAll();
		appDataManager.loadData();
	}

	@Override
	public void render (float dT) {
		ScreenUtils.clear(Color.GRAY);

		stage.act(dT);
		stage.draw();

		assetManager.update();

		if (assetManager.isFinished()) {
            Assets assets = new Assets(assetManager);

			GameContext context = new GameContext();
			context.setDataManager(appDataManager);
			context.setAudioManager(new UIAudioManager(appDataManager.getData(), assets));
			context.setLocalizationManager(new LocalizationManager(assets));
			context.setLayoutManager(new LayoutManager());
            context.setNetManager(new NetManager());
            context.setGame(game);
			context.setAssets(assets);

			MenuScreen menuScreen = new MenuScreen(context);
			GameScreen gameScreen = new GameScreen(context);

			AppData data = appDataManager.getData();
			context.getLocalizationManager().change(data.lang);

			game.addScreen(menuScreen, MenuScreen.NAME);
			game.addScreen(gameScreen, GameScreen.NAME);

			game.showScreen(MenuScreen.NAME);
		}
	}

	@Override
	public void resize (int width, int height) {
		stage.getViewport().apply();
		stage.getViewport().update(width, height);
	}

	@Override
	public void pause () {}

	@Override
	public void resume () {}

	@Override
	public void hide () {
		dispose();
	}

	@Override
	public void dispose () {
		skin.dispose();
		stage.dispose();
	}
}

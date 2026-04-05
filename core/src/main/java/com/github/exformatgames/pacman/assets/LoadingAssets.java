package com.github.exformatgames.pacman.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.exformatgames.pacman.assets.utils.JsonLoader;


public class LoadingAssets {

	private final String TAG = "LoadingAssets";

	public final static String DEFAULT_MUSIC_PATH = "audio/music/";
	public final static String DEFAULT_SOUND_PATH = "audio/sound/";
	public final static String DEFAULT_TEXTURE_ATLAS_PATH = "graphics/texture_atlas/";
	public final static String PLACEHOLDER_TEXTURE_PATH = "graphics/textures/placeholder.png";
	public final static String WHITE_TEXTURE_PATH = "graphics/textures/white.png";
	public final static String DEFAULT_ANIMATIONS_PATH = "animations/";
	public final static String DEFAULT_I18N_BUNDLE_PATH = "i18n/";
	public final static String DEFAULT_SKIN_PATH = "ui/skin.json";
	public final static Array<String> ANIMATION_PATH_LIST = new Array<>();

	private final AssetManager assetManager;

    public LoadingAssets (AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public void loadAll () {
		loadTextureAtlas();
		loadAnimations();
		loadI18nBundle();
		loadSkin();
		loadMusic();
		loadSound();

		log("end init loading.");
	}

	private void loadMusic () {
        assetManager.load("audio/music/GameMusic.mp3", Music.class);
        assetManager.load("audio/music/MenuMusic.mp3", Music.class);
	}

	private void loadSound () {
        assetManager.load("audio/sound/button.wav", Sound.class);
        assetManager.load("audio/sound/checkbox.wav", Sound.class);
        assetManager.load("audio/sound/switch_layout.wav", Sound.class);
	}

	private void loadI18nBundle () {
        assetManager.load("i18n/bundle_en", I18NBundle.class);
        assetManager.load("i18n/bundle_ru", I18NBundle.class);
	}

	private void loadSkin () {
		FileHandle file = Gdx.files.internal(DEFAULT_SKIN_PATH);

		if (file.length() > 0) {
			log("loading skin: " + file.path());
			assetManager.load(DEFAULT_SKIN_PATH, Skin.class);
		}
	}

	private void loadTextureAtlas () {
		assetManager.load(PLACEHOLDER_TEXTURE_PATH, Texture.class);
		assetManager.load(WHITE_TEXTURE_PATH, Texture.class);
        log("loading placeholders..");

        assetManager.load("graphics/texture_atlas/pacman.atlas", TextureAtlas.class);
        log("loading atlas..");
	}

	private void loadAnimations () {

		JsonLoader<AnimationData> loader = new JsonLoader<AnimationData>(new InternalFileHandleResolver());
		assetManager.setLoader(AnimationData.class, loader);

		JsonLoader.JsonParameter<AnimationData> param = new JsonLoader.JsonParameter<>(AnimationData.class);
        assetManager.load("animations/pacman-run.json", AnimationData.class, param);
        ANIMATION_PATH_LIST.add("animations/pacman-run.json");
	}

	private void log (String msg) {
		Gdx.app.log(TAG, msg);
	}
}

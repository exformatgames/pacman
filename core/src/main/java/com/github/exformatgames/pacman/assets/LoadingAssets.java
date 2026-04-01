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
		FileHandle[] handle = Gdx.files.internal(DEFAULT_MUSIC_PATH).list();

		for (FileHandle file: handle) {
			if (! file.isDirectory()) {
				log("loading music: " + file.path());
				assetManager.load(file.path(), Music.class);
			}
		}
	}

	private void loadSound () {
		FileHandle[] handle = Gdx.files.internal(DEFAULT_SOUND_PATH).list();

		for (FileHandle file: handle) {
			if (! file.isDirectory()) {
				log("loading sound: " + file.path());
				assetManager.load(file.path(), Sound.class);
			}
		}
	}

	private void loadI18nBundle () {
		FileHandle[] handle = Gdx.files.internal(DEFAULT_I18N_BUNDLE_PATH).list();

		if (handle.length > 0) {

			assetManager.load(DEFAULT_I18N_BUNDLE_PATH + "bundle_ru", I18NBundle.class);
			assetManager.load(DEFAULT_I18N_BUNDLE_PATH + "bundle_en", I18NBundle.class);
		}
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

        FileHandle[] handle = Gdx.files.internal(DEFAULT_TEXTURE_ATLAS_PATH).list();
		for (FileHandle file: handle) {
			if (! file.isDirectory()) {
                if (file.name().substring(file.name().indexOf('.'), file.name().length()).equals(".atlas")) {
					log("loading atlas: " + file.path());
					//assetManager.load(file.path(), TextureAtlas.class);
                }
			}
		}
	}

	private void loadAnimations () {
		FileHandle[] handle = Gdx.files.internal(DEFAULT_ANIMATIONS_PATH).list();

		JsonLoader<AnimationData> loader = new JsonLoader<AnimationData>(new InternalFileHandleResolver());
		assetManager.setLoader(AnimationData.class, loader);

		JsonLoader.JsonParameter<AnimationData> param = new JsonLoader.JsonParameter<>(AnimationData.class);

		for (FileHandle file: handle) {
			if (! file.isDirectory()) {
                if (file.name().substring(file.name().indexOf('.'), file.name().length()).equals(".json")) {
					String path = file.path();

					log("loading animations: " + path);

					assetManager.load(path, AnimationData.class, param);
					ANIMATION_PATH_LIST.add(path);
                }
			}
		}
	}

	private void log (String msg) {
		Gdx.app.log(TAG, msg);
	}
}

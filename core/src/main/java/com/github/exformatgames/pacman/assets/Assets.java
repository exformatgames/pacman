package com.github.exformatgames.pacman.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {

	private final AssetManager assetManager;

	private final Audio audio;
	private final Graphics graphics;
	private final Animations animations;
	private final I18NBundle en_i18Bundle;
	private final I18NBundle ru_i18Bundle;
	private final Skin skin;

	public Assets (AssetManager assetManager) {
		this.assetManager = assetManager;

		String atlasFilename = LoadingAssets.DEFAULT_TEXTURE_ATLAS_PATH + "pacman.atlas";
		String en_bundleFilename = "i18n/bundle_en";
		String ru_bundleFilename = "i18n/bundle_ru";

		TextureAtlas atlas = assetManager.get(atlasFilename, TextureAtlas.class);

		audio = new Audio(assetManager);
		graphics = new Graphics(atlas);
		en_i18Bundle = assetManager.get(en_bundleFilename, I18NBundle.class);
		ru_i18Bundle = assetManager.get(ru_bundleFilename, I18NBundle.class);
		animations = new Animations(graphics);

		for (String animPath : LoadingAssets.ANIMATION_PATH_LIST) {
			AnimationData data = assetManager.get(animPath);
			Animation<TextureRegion> animation = animations.build(data);

			animations.addAnimation(animation, data.name);
		}

		TextureRegion placeholderTextureRegion = new TextureRegion(assetManager.get(LoadingAssets.PLACEHOLDER_TEXTURE_PATH, Texture.class));
		TextureRegion whiteTextureRegion = new TextureRegion(assetManager.get(LoadingAssets.WHITE_TEXTURE_PATH, Texture.class));

		atlas.addRegion(Graphics.PLACEHOLDER_IMAGE_NAME, placeholderTextureRegion);
		atlas.addRegion(Graphics.WHITE_IMAGE_NAME, whiteTextureRegion);

		skin = assetManager.get(LoadingAssets.DEFAULT_SKIN_PATH);
	}

	public Skin getSkin () {
		return skin;
	}

	public Audio getAudio () {
		return audio;
	}

	public Graphics getGraphics () {
		return graphics;
	}

	public Animations getAnimations () {
		return animations;
	}

	public I18NBundle getEN_I18Bundle () {
		return en_i18Bundle;
	}

	public I18NBundle getRU_I18Bundle () {
		return ru_i18Bundle;
	}

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void dispose () {
        assetManager.dispose();
    }
}

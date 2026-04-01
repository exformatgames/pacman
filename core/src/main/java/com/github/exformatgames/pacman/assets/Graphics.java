package com.github.exformatgames.pacman.assets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Graphics {

	public final static String PLACEHOLDER_IMAGE_NAME = "placeholder";
	public final static String WHITE_IMAGE_NAME = "WHITE";

	private final TextureAtlas atlas;


	public Graphics (TextureAtlas atlas) {
		this.atlas = atlas;
	}

	public TextureRegion getTextureRegion (String name, int index) {
		TextureRegion region = null;

		if (index < 0) {
			region = atlas.findRegion(name);
		} else {
			region = atlas.findRegion(name, index);
		}

		if (region == null) {
			region = atlas.findRegion(PLACEHOLDER_IMAGE_NAME);
		}

		return region;
	}

	public TextureRegion getTextureRegion (String name) {
		return getTextureRegion(name, -1);
	}
}

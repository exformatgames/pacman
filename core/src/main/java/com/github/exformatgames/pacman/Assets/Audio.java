package com.github.exformatgames.pacman.Assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio {
	private final AssetManager assetManager;

	private final String PLACEHOLDER_SOUND_PATH = "audio/sound/placeholder.wav";
	private final String PLACEHOLDER_MUSIC_PATH = "audio/music/placeholder.mp3";

	public Audio (AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public Sound getSound (String fileName) {
		Sound sound = assetManager.get(fileName);
		if (sound == null) {
			sound = assetManager.get(PLACEHOLDER_SOUND_PATH);
		}
		return sound;
	}

	public Music getMusic (String fileName) {
		Music music = assetManager.get(fileName);
		if (music == null) {
			music = assetManager.get(PLACEHOLDER_MUSIC_PATH);
		}
		return music;
	}
}

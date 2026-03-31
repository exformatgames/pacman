package com.github.exformatgames.pacman.managers;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.github.exformatgames.pacman.Assets.Assets;
import com.github.exformatgames.pacman.data.AppData;

public class UIAudioManager {

	private final AppData appData;
	private final Assets assets;

	private final Array<Music> activeMusicList = new Array<>();

	public UIAudioManager (AppData appData, Assets assets) {
		this.appData = appData;
		this.assets = assets;
	}



    public void playButtonSound () {
        playSound("audio/sound/button.wav");
    }

    public void playCheckBoxSound () {
        playSound("audio/sound/checkbox.wav");
    }

    public void playChangeLayoutSound () {
        playSound("audio/sound/switch_layout.wav");
    }

    public void playMenuMusic () {
        playMusic("audio/music/MenuMusic.mp3");
    }

    public void playGameMusic () {
        playMusic("audio/music/GameMusic.mp3");
    }

	public void playMusic (String name) {
		if (appData.musicOn && appData.volumeValue > 0) {
			Music music = assets.getAudio().getMusic(name);
			music.setVolume(appData.volumeValue);
			music.setLooping(true);
			music.play();

            activeMusicList.add(music);
		}
	}

    public void playSound (String name) {
        if (appData.soundOn && appData.volumeValue > 0) {
            assets.getAudio().getSound(name).play(appData.volumeValue);
        }
    }

    public void playSound (Sound sound) {
        if (appData.soundOn && appData.volumeValue > 0) {
            sound.play(appData.volumeValue);
        }
    }

	public void stopMusic () {
		for (Music music : activeMusicList) {
			music.stop();
		}
	}

	public void changeVolume (float volume) {
		for (Music music : activeMusicList) {
			music.setVolume(volume);
		}
	}
}

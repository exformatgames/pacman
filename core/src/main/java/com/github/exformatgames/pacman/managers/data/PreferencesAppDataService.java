package com.github.exformatgames.pacman.managers.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.github.exformatgames.pacman.managers.AppDataManager;
import com.github.exformatgames.pacman.data.AppData;

public class PreferencesAppDataService extends AppDataManager.AppDataServiceI {

	//попросил гпт напомнить как с ними работать, писал сам..

	private final static String NAME_PREFERENCES = "pacman_appData";

	private final static String KEY_VOLUME = "volume";
	private final static String KEY_SOUND = "sound";
	private final static String KEY_MUSIC = "music";
	private final static String KEY_LANG = "lang";

	private Preferences preferences;

	public PreferencesAppDataService () {
		preferences = Gdx.app.getPreferences(NAME_PREFERENCES);
	}

	@Override
	public void save (AppData data) {
		preferences.putBoolean(KEY_SOUND, data.soundOn);
		preferences.putBoolean(KEY_MUSIC, data.musicOn);
		preferences.putFloat(KEY_VOLUME, data.volumeValue);
		preferences.putString(KEY_LANG, data.lang);

		preferences.flush();
	}

	@Override
	public void load () {
		AppData data = new AppData();

		data.soundOn = preferences.getBoolean(KEY_SOUND, data.soundOn);
		data.musicOn = preferences.getBoolean(KEY_MUSIC, data.musicOn);
		data.volumeValue = preferences.getFloat(KEY_VOLUME, data.volumeValue);
		data.lang = preferences.getString(KEY_LANG, data.lang);

		loadRequest(data);
	}
}

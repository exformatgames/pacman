package com.github.exformatgames.pacman.managers;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.github.exformatgames.pacman.Assets.Assets;

public class LocalizationManager {

	private Array<Localizable> localizableList = new Array<>();

	private I18NBundle enBundle;
	private I18NBundle ruBundle;

	private I18NBundle activeBundle;

	public LocalizationManager (Assets assets) {
		enBundle = assets.getEN_I18Bundle();
		ruBundle = assets.getRU_I18Bundle();

		activeBundle = enBundle;
	}

	public void change (String lang) {
		switch (lang) {
			case "english" : {
					activeBundle = enBundle;
					break;
				}
			case "русский" : {
					activeBundle = ruBundle;
					break;
				}
		}

		for (Localizable l : localizableList) {
			l.applyLocale(activeBundle);
		}
	}

	public I18NBundle getActiveBundle () {
		return activeBundle;
	}

	public void add (Localizable localizable) {
		localizableList.add(localizable);
	}

    public static interface Localizable {

        void applyLocale(I18NBundle bundle);
    }
}

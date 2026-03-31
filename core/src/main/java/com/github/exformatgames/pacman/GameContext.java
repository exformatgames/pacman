package com.github.exformatgames.pacman;

import com.github.exformatgames.pacman.Assets.Assets;
import com.github.exformatgames.pacman.managers.AppDataManager;
import com.github.exformatgames.pacman.managers.LayoutManager;
import com.github.exformatgames.pacman.managers.LocalizationManager;
import com.github.exformatgames.pacman.managers.UIAudioManager;
import com.github.exformatgames.pacman.managers.NetManager;

public class GameContext {
	private PacmanGame game;
	private UIAudioManager audioManager;
	private AppDataManager dataManager;
	private LocalizationManager localizationManager;
	private LayoutManager layoutManager;
    private NetManager netManager;
    private Assets assets;

    public NetManager getNetManager() {
        return netManager;
    }

    public void setNetManager(NetManager netManager) {
        this.netManager = netManager;
    }

	public void setLayoutManager (LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}

	public LayoutManager getLayoutManager () {
		return layoutManager;
	}


	public void setLocalizationManager (LocalizationManager localizationManager) {
		this.localizationManager = localizationManager;
	}

	public LocalizationManager getLocalizationManager () {
		return localizationManager;
	}


	public void setAssets (Assets assets) {
		this.assets = assets;
	}

	public Assets getAssets () {
		return assets;
	}


	public void setGame (PacmanGame game) {
		this.game = game;
	}

	public PacmanGame getGame () {
		return game;
	}

	public void setAudioManager (UIAudioManager audioManager) {
		this.audioManager = audioManager;
	}

	public UIAudioManager getAudioManager () {
		return audioManager;
	}

	public void setDataManager (AppDataManager dataManager) {
		this.dataManager = dataManager;
	}

	public AppDataManager getDataManager () {
		return dataManager;
	}}

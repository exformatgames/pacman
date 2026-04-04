package com.github.exformatgames.pacman;

import com.github.exformatgames.pacman.assets.Assets;
import com.github.exformatgames.pacman.net.Client;
import com.github.exformatgames.pacman.managers.AppDataManager;
import com.github.exformatgames.pacman.managers.LayoutManager;
import com.github.exformatgames.pacman.managers.LocalizationManager;
import com.github.exformatgames.pacman.managers.UIAudioManager;

public class GameContext {
	private PacmanGame game;
	private UIAudioManager audioManager;
	private AppDataManager dataManager;
	private LocalizationManager localizationManager;
	private LayoutManager layoutManager;
    private Client client;
    private Assets assets;

    private String host = "localhost";
    private int port = 8080;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
	}

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

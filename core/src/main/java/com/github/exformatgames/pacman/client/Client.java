package com.github.exformatgames.pacman.client;

import com.badlogic.gdx.utils.Array;
import com.github.exformatgames.pacman.client.service.ButtonEventService;
import com.github.exformatgames.pacman.client.service.ConnectionService;
import com.github.exformatgames.pacman.client.service.GameMapService;
import com.github.exformatgames.pacman.client.service.GameSessionService;
import com.github.exformatgames.pacman.client.service.ServerGameEventService;

public abstract class Client implements ConnectionStatusListener {

	protected ConnectionService connectionService;
	protected GameSessionService gameSessionService;
	protected GameMapService gameMapService;
	protected ButtonEventService buttonEventService;
	protected ServerGameEventService gameEventService = new ServerGameEventService();;

	protected Array<ConnectionStatusListener> statusListenerList = new Array<>();

	@Override
	public void onConnected () {
		for (ConnectionStatusListener l : statusListenerList) {
			l.onConnected();
		}
	}

	@Override
	public void onDisconnected () {
		for (ConnectionStatusListener l : statusListenerList) {
			l.onConnected();
		}
	}


	public void addConnectionStatusListener (ConnectionStatusListener listener) {
		statusListenerList.add(listener);
	}

	public void setGameMapService (GameMapService gameMapService) {
		this.gameMapService = gameMapService;
	}

	public GameMapService getGameMapService () {
		return gameMapService;
	}

	public void setConnectionService (ConnectionService connectionService) {
		this.connectionService = connectionService;
	}

	public ConnectionService getConnectionService () {
		return connectionService;
	}

	public void setGameSessionService (GameSessionService gameSessionService) {
		this.gameSessionService = gameSessionService;
	}

	public GameSessionService getGameSessionService () {
		return gameSessionService;
	}

	public void setButtonEventService (ButtonEventService buttonEventService) {
		this.buttonEventService = buttonEventService;
	}

	public ButtonEventService getButtonEventService () {
		return buttonEventService;
	}

	public void setGameEventService (ServerGameEventService gameEventService) {
		this.gameEventService = gameEventService;
	}

	public ServerGameEventService getGameEventService () {
		return gameEventService;
	}

	public abstract void dispose ();
}

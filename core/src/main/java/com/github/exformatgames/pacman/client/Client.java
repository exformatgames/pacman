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

    public GameMapService getGameMapService () {
		return gameMapService;
	}
	public ConnectionService getConnectionService () {
		return connectionService;
	}
	public GameSessionService getGameSessionService () {
		return gameSessionService;
	}
	public ButtonEventService getButtonEventService () {
		return buttonEventService;
	}
	public ServerGameEventService getGameEventService () {
		return gameEventService;
	}

	public abstract void dispose ();
}

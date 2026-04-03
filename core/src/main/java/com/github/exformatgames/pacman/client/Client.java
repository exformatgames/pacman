package com.github.exformatgames.pacman.client;

import com.badlogic.gdx.utils.Array;
import com.github.exformatgames.pacman.client.service.ButtonEventService;
import com.github.exformatgames.pacman.client.service.ConnectionService;
import com.github.exformatgames.pacman.client.service.GameMapService;
import com.github.exformatgames.pacman.client.service.GameSessionService;
import com.github.exformatgames.pacman.client.service.ServerGameEventService;
import com.github.exformatgames.pacman.client.service.TestService;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Client {

	protected ConnectionService connectionService;
	protected GameSessionService gameSessionService;
	protected GameMapService gameMapService;
	protected ButtonEventService buttonEventService;
	protected ServerGameEventService gameEventService;
	protected TestService testService;
	protected Array<ConnectionStatusListener> statusListenerList = new Array<>();
	private  ConcurrentLinkedQueue<Runnable> eventQueue;


	public void onConnected () {
        for (ConnectionStatusListener l : statusListenerList) {
            l.onConnected();
        }
	}

	public void onDisconnected () {
        for (ConnectionStatusListener l : statusListenerList) {
            l.onDisconnected();
        }
	}

	public void addConnectionStatusListener (ConnectionStatusListener listener) {
		statusListenerList.add(listener);
	}

	public void setEventQueue (ConcurrentLinkedQueue<Runnable> eventQueue) {
		this.eventQueue = eventQueue;
	}

	public ConcurrentLinkedQueue<Runnable> getEventQueue () {
		return eventQueue;
	}

	public void setTestService (TestService testService) {
		this.testService = testService;
	}

	public TestService getTestService () {
		return testService;
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

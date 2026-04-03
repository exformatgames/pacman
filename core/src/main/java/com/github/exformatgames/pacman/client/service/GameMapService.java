package com.github.exformatgames.pacman.client.service;

import com.badlogic.gdx.utils.Array;
import com.github.exformatgames.pacman.client.Client;
import com.github.exformatgames.pacman.data.MapData;

public abstract class GameMapService {

	private final Array<GameMapReceivedListener> listenerList = new Array<>();

	protected final Client client;

	public GameMapService (Client client) {
		this.client = client;
	}

    public abstract void requestMap ();

    public void onGameMapReceived (final MapData mapData) {
		client.getEventQueue().add(new Runnable() {
				@Override
				public void run () {
					for (GameMapReceivedListener l : listenerList) {
						l.onGameMapReceived(mapData);
					}
				}
			});
	}

	public void addListener (GameMapReceivedListener listener) {
		listenerList.add(listener);
	}

	public interface GameMapReceivedListener {
		void onGameMapReceived (MapData mapData);
	}
}

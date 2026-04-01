package com.github.exformatgames.pacman.client.service;

import com.github.exformatgames.pacman.data.MapData;
import com.badlogic.gdx.utils.Array;

public abstract class GameMapService {

	private final Array<GameMapReceivedListener> listenerList = new Array<>();

    public abstract void requestMap();

    public void onGameMapReceived(MapData mapData) {
		for (GameMapReceivedListener l : listenerList) {
			l.onGameMapReceived(mapData);
		}
	}

	public void addListener(GameMapReceivedListener listener) {
		listenerList.add(listener);
	}

	public interface GameMapReceivedListener {
		void onGameMapReceived(MapData mapData);
	}
}

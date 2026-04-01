package com.github.exformatgames.pacman.server.server.service;


import com.github.exformatgames.pacman.server.data.MapData;

import java.util.ArrayList;

public abstract class GameMapService {

	private final ArrayList<GameMapReceivedListener> listenerList = new ArrayList<>();

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

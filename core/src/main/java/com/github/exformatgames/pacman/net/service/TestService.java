package com.github.exformatgames.pacman.net.service;

import com.badlogic.gdx.utils.Array;
import com.github.exformatgames.pacman.net.Client;

public abstract class TestService {

	private final Array<ResponsePongListener> listenerList = new Array<>();
	protected final Client client;

	public TestService (Client client) {
		this.client = client;
	}


    public abstract void requestPing();

    public void responsePong() {
		client.getEventQueue().add(new Runnable() {
				@Override
				public void run () {
					for (ResponsePongListener l : listenerList) {
						l.pong();
					}
				}
			});
	}

	public void addListener(ResponsePongListener listener) {
		listenerList.add(listener);
	}

	public interface ResponsePongListener {
		void pong();
	}
}

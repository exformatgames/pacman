package com.github.exformatgames.pacman.app.net;

import com.github.exformatgames.pacman.data.MapData;
import com.github.exformatgames.pacman.data.PlayerData;
import com.github.exformatgames.pacman.data.PositionData;
import com.github.exformatgames.pacman.data.FoodData;

public class NetManager {

	private String hoost;
	private int port;

	private ReconnectToken reconnectToken;

	public void connect (String host, int port) {
		this.hoost = host;
		this.port = port;
	}

	public void connectCallback (boolean success) {}


	public void requestReconnect () {}

	public void responseReconnect (ReconnectToken newToken) {}

	public void disconnect () {}
	
	private void ping() {}
	public void responsePing() {}


	public void sendInputTouchDown (int buttonID) {}
	public void sendInputTouchUp (int buttonID) {}


	public void requestGameMap () {}
	public void responseGameMap (MapData map) {}

    public void newPositionPlayerEvent (PositionData position, PlayerData player) {}
	public void connectNewPlayerEvent (PositionData position, PlayerData player) {}
	public void disconnectPlayerEvent (PlayerData player) {}

	public void createNewFoodEvent (FoodData data) {}
    public void destroyFoodEvent (FoodData data) {}

	public void createNewEntityEvent(/*EntityData*/) {}
	
	public void update (float dT) {
		//check connection status
		// if disconnect -> reconnect
	}
}

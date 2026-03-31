package com.github.exformatgames.pacman.server.net;

import com.github.exformatgames.pacman.server.ecs.GameService;

public class NetManager {
	private NetService netService;
	private GameService gameService;


	public void setGameService (GameService gameService) {
		this.gameService = gameService;
	}

	public GameService getGameService () {
		return gameService;
	}


	public void setNetService (NetService service) {
		this.netService = service;
	}

	public NetService getNetService () {
		return netService;
	}
}

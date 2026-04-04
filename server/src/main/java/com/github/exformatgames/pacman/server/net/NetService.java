package com.github.exformatgames.pacman.server.net;

import com.github.exformatgames.pacman.server.GameWorld;
import com.github.exformatgames.pacman.server.net.netty.NettyServer;

public class NetService {
	protected SendService sendService;

	private GameWorld gameWorld;
	private NettyServer server;

	public NetService (GameWorld gameWorld, NettyServer server) {
		this.gameWorld = gameWorld;
		this.server = server;
	}



	public GameWorld getGameWorld () {
		return gameWorld;
	}

	public NettyServer getServer () {
		return server;
	}

	public SendService getSendService () {
		return sendService;
	}
}

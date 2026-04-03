package com.github.exformatgames.pacman.client.netty.services;

import com.github.exformatgames.pacman.client.netty.NettyClient;
import com.github.exformatgames.pacman.client.netty.packet.ExitGamePacket;
import com.github.exformatgames.pacman.client.netty.packet.JoinGamePacket;
import com.github.exformatgames.pacman.client.service.GameSessionService;

public class GameSession implements GameSessionService {

	private final NettyClient client;

	public GameSession (NettyClient client) {
		this.client = client;
	}

	@Override
	public void joinGame() {
		client.getPacketSender().send(new JoinGamePacket());
	}

	@Override
	public void exitGame () {
		client.getPacketSender().send(new ExitGamePacket());
	}
}

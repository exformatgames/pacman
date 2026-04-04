package com.github.exformatgames.pacman.net.netty.services;

import com.github.exformatgames.pacman.net.netty.NettyClient;
import net.netty.packet.ExitGamePacket;
import net.netty.packet.JoinGamePacket;
import com.github.exformatgames.pacman.net.service.GameSessionService;

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

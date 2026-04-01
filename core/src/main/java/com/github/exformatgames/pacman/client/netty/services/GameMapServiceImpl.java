package com.github.exformatgames.pacman.client.netty.services;

import com.github.exformatgames.pacman.client.service.GameMapService;
import com.github.exformatgames.pacman.client.netty.NettyClient;
import com.github.exformatgames.pacman.client.netty.packet.RequestGameMapPacket;

public class GameMapServiceImpl extends GameMapService {

	private final NettyClient client;

	public GameMapServiceImpl (NettyClient client) {
		this.client = client;
	}

	@Override
	public void requestMap () {
		client.getPacketSender().send(new RequestGameMapPacket());
	}
}

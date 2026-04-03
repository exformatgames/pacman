package com.github.exformatgames.pacman.client.netty.services;

import com.github.exformatgames.pacman.client.service.GameMapService;
import com.github.exformatgames.pacman.client.netty.NettyClient;
import com.github.exformatgames.pacman.client.netty.packet.RequestGameMapPacket;

public class GameMapServiceImpl extends GameMapService {

	public GameMapServiceImpl (NettyClient client) {
		super(client);
	}

	@Override
	public void requestMap () {
		((NettyClient)client).getPacketSender().send(new RequestGameMapPacket());
	}
}

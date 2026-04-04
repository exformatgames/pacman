package com.github.exformatgames.pacman.net.netty.services;

import com.github.exformatgames.pacman.net.service.GameMapService;
import com.github.exformatgames.pacman.net.netty.NettyClient;
import net.netty.packet.RequestGameMapPacket;

public class GameMapServiceImpl extends GameMapService {

	public GameMapServiceImpl (NettyClient client) {
		super(client);
	}

	@Override
	public void requestMap () {
		((NettyClient)client).getPacketSender().send(new RequestGameMapPacket());
	}
}

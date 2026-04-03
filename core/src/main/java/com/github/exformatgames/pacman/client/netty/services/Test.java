package com.github.exformatgames.pacman.client.netty.services;

import com.github.exformatgames.pacman.client.netty.NettyClient;
import com.github.exformatgames.pacman.client.netty.packet.Packet;
import com.github.exformatgames.pacman.client.netty.packet.PacketType;
import com.github.exformatgames.pacman.client.service.TestService;

public class Test extends TestService {

	public Test (NettyClient client) {
		super(client);
	}

	@Override
	public void requestPing () {
		Packet packet = new Packet() {

			@Override
			public PacketType getType () {
				return PacketType.PING;
			}
		};

		((NettyClient)client).getPacketSender().send(packet);
	}
}

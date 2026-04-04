package com.github.exformatgames.pacman.net.netty.services;

import com.github.exformatgames.pacman.net.netty.NettyClient;
import com.github.exformatgames.pacman.net.service.TestService;
import net.netty.packet.Packet;
import net.netty.packet.PacketType;

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

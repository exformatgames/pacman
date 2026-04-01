package com.github.exformatgames.pacman.client.netty;

import com.github.exformatgames.pacman.client.netty.services.Connection;
import com.github.exformatgames.pacman.client.netty.packet.Packet;
import io.netty.channel.Channel;

public class PacketSender {

	private final NettyClient client;

	public PacketSender (NettyClient client) {
		this.client = client;
	}

	public void send (Packet packet) {
		Channel channel = ((Connection)client.getConnectionService()).getChannel();
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(packet);

            System.out.println("send packet: " + packet.toString());
        }
	}
}

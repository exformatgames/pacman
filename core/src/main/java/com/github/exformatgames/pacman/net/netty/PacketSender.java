package com.github.exformatgames.pacman.net.netty;

import com.github.exformatgames.pacman.net.netty.services.Connection;
import io.netty.channel.Channel;
import net.netty.packet.Packet;

public class PacketSender {

	private final NettyClient client;

	public PacketSender (NettyClient client) {
		this.client = client;
	}

	public void send (Packet packet) {
        System.out.println("send packet: " + packet.toString());
        Channel channel = ((Connection)client.getConnectionService()).getChannel();
        if ((channel != null) && channel.isActive()) {
            channel.writeAndFlush(packet);

            System.out.println("end send packet: " + packet.toString());
        }
	}
}

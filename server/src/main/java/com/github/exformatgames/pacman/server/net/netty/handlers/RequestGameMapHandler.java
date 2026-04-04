package com.github.exformatgames.pacman.server.net.netty.handlers;

import com.github.exformatgames.pacman.server.GameWorld;
import com.github.exformatgames.pacman.server.net.netty.NettyServer;
import io.netty.channel.Channel;
import net.netty.packet.RequestGameMapPacket;
import net.netty.utils.PacketHandler;

public class RequestGameMapHandler implements PacketHandler<RequestGameMapPacket> {

	private final NettyServer server;
	private final GameWorld gameWorld;

	public RequestGameMapHandler (NettyServer server, GameWorld gameWorld) {
		this.server = server;
		this.gameWorld = gameWorld;
	}

	@Override
	public void handle (Channel channel, RequestGameMapPacket packet) {
		gameWorld.getMapDataHandler().requestGameMap(server.getClientID(channel));
	}
}

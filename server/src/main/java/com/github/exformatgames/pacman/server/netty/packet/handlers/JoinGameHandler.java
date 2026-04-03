package com.github.exformatgames.pacman.server.netty.packet.handlers;

import com.github.exformatgames.pacman.server.GameWorld;
import com.github.exformatgames.pacman.server.netty.NettyServer;
import com.github.exformatgames.pacman.server.netty.packet.PacketHandler;
import com.github.exformatgames.pacman.server.netty.packet.StartGamePacket;
import io.netty.channel.Channel;

public class JoinGameHandler implements PacketHandler<StartGamePacket> {

	private final NettyServer server;
	private final GameWorld gameWorld;

	public JoinGameHandler (NettyServer server, GameWorld gameWorld) {
		this.server = server;
		this.gameWorld = gameWorld;
	}

	@Override
	public void handle (Channel channel, StartGamePacket packet) {
		gameWorld.getPlayerHandler().addPlayer(server.getClientID(channel));
	}
}

package com.github.exformatgames.pacman.server.net.netty.handlers;

import com.github.exformatgames.pacman.server.GameWorld;
import com.github.exformatgames.pacman.server.net.netty.NettyServer;
import net.netty.packet.ExitGamePacket;
import net.netty.utils.PacketHandler;
import io.netty.channel.Channel;

public class ExitGameHandler implements PacketHandler<ExitGamePacket> {

	private final NettyServer server;
	private final GameWorld gameWorld;

	public ExitGameHandler (NettyServer server, GameWorld gameWorld) {
		this.server = server;
		this.gameWorld = gameWorld;
	}

	@Override
	public void handle (Channel channel, ExitGamePacket packet) {
		gameWorld.getPlayerHandler().removePlayer(server.getClientID(channel));
	}
}

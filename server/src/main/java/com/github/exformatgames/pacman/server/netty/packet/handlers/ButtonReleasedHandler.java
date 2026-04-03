package com.github.exformatgames.pacman.server.netty.packet.handlers;

import com.github.exformatgames.pacman.server.GameWorld;
import com.github.exformatgames.pacman.server.netty.NettyServer;
import com.github.exformatgames.pacman.server.netty.packet.ButtonReleasedPacket;
import com.github.exformatgames.pacman.server.netty.packet.PacketHandler;
import io.netty.channel.Channel;

public class ButtonReleasedHandler implements PacketHandler<ButtonReleasedPacket> {

	private final NettyServer server;
	private final GameWorld gameWorld;

	public ButtonReleasedHandler (NettyServer server, GameWorld gameWorld) {
		this.server = server;
		this.gameWorld = gameWorld;
	}

	@Override
	public void handle (Channel channel, ButtonReleasedPacket packet) {
		gameWorld.getInputHandler().releaseButton(server.getClientID(channel), packet.action);
	}
}

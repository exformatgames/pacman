package com.github.exformatgames.pacman.server.net.netty.handlers;

import com.github.exformatgames.pacman.server.GameWorld;
import com.github.exformatgames.pacman.server.net.netty.NettyServer;
import net.netty.packet.ButtonReleasedPacket;
import net.netty.utils.PacketHandler;
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

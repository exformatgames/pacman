package com.github.exformatgames.pacman.server.net.netty.handlers;

import com.github.exformatgames.pacman.server.GameWorld;
import com.github.exformatgames.pacman.server.net.netty.NettyServer;
import io.netty.channel.Channel;
import net.netty.packet.ButtonPressedPacket;
import net.netty.utils.PacketHandler;

public class ButtonPressedHandler implements PacketHandler<ButtonPressedPacket> {

	private final NettyServer server;
	private final GameWorld gameWorld;

	public ButtonPressedHandler (NettyServer server, GameWorld gameWorld) {
		this.server = server;
		this.gameWorld = gameWorld;
	}

	@Override
	public void handle (Channel channel, ButtonPressedPacket packet) {
		gameWorld.getInputHandler().pressedButton(server.getClientID(channel), packet.action);
	}
}

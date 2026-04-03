package com.github.exformatgames.pacman.server.netty.packet.handlers;

import com.github.exformatgames.pacman.server.GameWorld;
import com.github.exformatgames.pacman.server.data.InputData;
import com.github.exformatgames.pacman.server.netty.NettyServer;
import com.github.exformatgames.pacman.server.netty.packet.ButtonPressedPacket;
import com.github.exformatgames.pacman.server.netty.packet.PacketHandler;
import io.netty.channel.Channel;

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

package com.github.exformatgames.pacman.server.net.netty.handlers;

import com.github.exformatgames.pacman.server.GameWorld;
import com.github.exformatgames.pacman.server.net.netty.NettyServer;
import io.netty.channel.Channel;
import net.netty.packet.JoinGamePacket;
import net.netty.utils.PacketHandler;

public class JoinGameHandler implements PacketHandler<JoinGamePacket> {

	private final NettyServer server;
	private final GameWorld gameWorld;

	public JoinGameHandler (NettyServer server, GameWorld gameWorld) {
		this.server = server;
		this.gameWorld = gameWorld;
	}

	@Override
	public void handle (Channel channel, JoinGamePacket packet) {
        System.out.println("JoinGameHandler");
		gameWorld.getPlayerHandler().addPlayer(server.getClientID(channel));
	}

    @Override
    public String toString() {
        return "JoinGameHandler{}";
    }
}

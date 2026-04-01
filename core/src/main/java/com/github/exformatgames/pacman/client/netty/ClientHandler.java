package com.github.exformatgames.pacman.client.netty;

import com.github.exformatgames.pacman.client.netty.packet.EntityPacket;
import com.github.exformatgames.pacman.client.netty.packet.MapPacket;
import com.github.exformatgames.pacman.client.netty.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Packet> {

    private NettyClient client;

	public ClientHandler (NettyClient client) {
		this.client = client;
	}

    @Override
    public void channelActive (ChannelHandlerContext ctx) {
        client.onConnected();
    }

    @Override
    public void channelInactive (ChannelHandlerContext ctx) {
        client.onDisconnected();
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, Packet msg) {
        switch (msg.getType()) {
            case RESPONSE_GAME_MAP:
                MapPacket mapPacket = (MapPacket) msg;
				client.getGameMapService().onGameMapReceived(mapPacket.data);
                break;
            case ENTITY_CREATED : {
					EntityPacket entityPacket = (EntityPacket) msg;
					client.getGameEventService().onEntityCreated(entityPacket.data);
					break;
				}
            case ENTITY_REMOVED : {
					EntityPacket entityPacket = (EntityPacket) msg;
					client.getGameEventService().onEntityRemoved(entityPacket.data);
					break;
				}
            case ENTITY_POSITION_CHANGED : {
					EntityPacket entityPacket = (EntityPacket) msg;
					client.getGameEventService().onEntityTransformed(entityPacket.data);
					break;
				}
        }
    }
}

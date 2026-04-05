package com.github.exformatgames.pacman.net.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.netty.packet.EntityPacket;
import net.netty.packet.MapPacket;
import net.netty.packet.Packet;

public class ClientHandler extends SimpleChannelInboundHandler<Packet> {

    private NettyClient client;

    public ClientHandler(NettyClient client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        client.onConnected();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        client.onDisconnected();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {
        System.out.println("ClientHandler.read.PacketType: " + msg.getType());

        switch (msg.getType()) {
            case PONG: {
                System.out.println("ClientHandler.read.PONG");
                client.getTestService().responsePong();
                break;
            }
            case RESPONSE_GAME_MAP:
                MapPacket mapPacket = (MapPacket) msg;
                client.getGameMapService().onGameMapReceived(mapPacket.data);
                break;
            case ENTITY_CREATED: {
                EntityPacket entityPacket = (EntityPacket) msg;
                client.getGameEventService().onEntityCreated(entityPacket.data);
                break;
            }
            case ENTITY_REMOVED: {
                EntityPacket entityPacket = (EntityPacket) msg;
                client.getGameEventService().onEntityRemoved(entityPacket.data);
                break;
            }
            case ENTITY_TRANSFORMED: {
                EntityPacket entityPacket = (EntityPacket) msg;
                client.getGameEventService().onEntityTransformed(entityPacket.data);
                break;
            }
        }
    }
}

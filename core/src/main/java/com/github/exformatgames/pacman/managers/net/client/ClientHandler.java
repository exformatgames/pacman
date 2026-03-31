package com.github.exformatgames.pacman.managers.net.client;

import com.github.exformatgames.pacman.managers.NetManager;
import com.github.exformatgames.pacman.managers.net.client.packet.EntityPacket;
import com.github.exformatgames.pacman.managers.net.client.packet.MapPacket;
import com.github.exformatgames.pacman.managers.net.client.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Packet> {

    private final NetManager netManager;

    public ClientHandler(NetManager netManager) {
        this.netManager = netManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        netManager.connected();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        netManager.disconnected();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {

        switch (msg.getType()) {

            case RESPONSE_GAME_MAP:
                MapPacket mapPacket = (MapPacket) msg;
                netManager.responseGameMap(mapPacket.data);
                break;

            case ENTITY_CREATED:
                netManager.entityCreated(((EntityPacket) msg).data);
                break;

            case ENTITY_REMOVED:
                netManager.entityRemoved(((EntityPacket) msg).data);
                break;

            case ENTITY_POSITION_CHANGED:
                netManager.entityPositionChanged(((EntityPacket) msg).data);
                break;
        }
    }
}

package com.github.exformatgames.pacman.server.net.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.netty.packet.Packet;
import net.netty.packet.PacketType;
import net.netty.utils.PacketHandler;

import java.util.HashMap;
import java.util.Map;

public class ServerHandler extends SimpleChannelInboundHandler<Packet> {

	private final Map<PacketType, PacketHandler> handlerMap = new HashMap<>();

	private final NettyServer service;

	public ServerHandler (NettyServer service) {
		this.service = service;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		service.addClient(ctx.channel());
	}

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        service.removeClient(ctx.channel());
	}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {

		PacketHandler handler = handlerMap.get(packet.getType());
		if (handler != null) {
            handler.handle(ctx.channel(), packet);
		}
        else {
            switch (packet.getType()) {
                case PING: {
                    service.sendTo(service.getClientID(ctx.channel()), new Packet() {
                        @Override
                        public PacketType getType() {
                            return PacketType.PONG;
                        }
                    });
                    break;
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

	public void addPacketHandler(PacketType type, PacketHandler handler) {
		handlerMap.put(type, handler);
	}
}

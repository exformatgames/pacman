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
        System.out.println("channelActive");
		service.addClient(ctx.channel());
	}

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("channelInactive");
        service.removeClient(ctx.channel());
	}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
        System.out.println("ServertHandler.read.PacketType: " + packet.getType());

		PacketHandler handler = handlerMap.get(packet.getType());
		if (handler != null) {
            System.out.println("ServertHandler.read.handler: " + handler.toString());
            handler.handle(ctx.channel(), packet);
		}
        else {
            switch (packet.getType()) {
                case PING: {
                    System.out.println("ServerHandler.read0.PING");

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

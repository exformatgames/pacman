package com.github.exformatgames.pacman.server.netty;

import com.github.exformatgames.pacman.server.netty.packet.Packet;
import com.github.exformatgames.pacman.server.netty.packet.PacketHandler;
import com.github.exformatgames.pacman.server.netty.packet.PacketType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.HashMap;
import java.util.Map;

public class ServerHandler extends SimpleChannelInboundHandler<Packet> {

	private final Map<PacketType, PacketHandler> handlerMap = new HashMap<>();

	private final NettyServer service;

	public ServerHandler (NettyServer service) {
		this.service = service;
	}

	//была мысль и сюда хендлеры сделать.. но чет перебор мне кажется..
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

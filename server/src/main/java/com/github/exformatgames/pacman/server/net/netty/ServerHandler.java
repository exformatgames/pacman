package com.github.exformatgames.pacman.server.net.netty;

/**
 * Author: Exformat, ChatGPT
 */

import com.github.exformatgames.pacman.server.net.NetService;
import com.github.exformatgames.pacman.server.net.netty.packet.InputPacket;
import com.github.exformatgames.pacman.server.net.netty.packet.Packet;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<Packet> {

    private final NetService netService;

    private int clientId;

    public ServerHandler(NetService netService) {
        this.netService = netService;
    }

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		clientId = ctx.channel().hashCode();

		((NettyService) netService).registerClient(clientId, ctx.channel());

		netService.onClientConnected(clientId);
	}

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        netService.onClientDisconnected(clientId);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {

        switch (packet.getType()) {

            case START_GAME:
                netService.onStartGame(clientId);
                System.out.println("start. playerID: " + clientId);
                break;

            case EXIT_GAME:
                netService.onExitGame(clientId);
                System.out.println("exit. playerID: " + clientId);
                break;

            case INPUT:
                InputPacket input = (InputPacket) packet;
                netService.onInput(clientId, input.action, input.pressed);

                System.out.println("input. playerID: " + clientId);
                break;

            case REQUEST_GAME_MAP:
                netService.onRequestMap(clientId);
                System.out.println("requestMap. playerID: " + clientId);
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}

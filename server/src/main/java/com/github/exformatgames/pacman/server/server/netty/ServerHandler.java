package com.github.exformatgames.pacman.server.server.netty;

/**
 * Author: Exformat, ChatGPT
 */

import com.github.exformatgames.pacman.server.net.NetService;
import com.github.exformatgames.pacman.server.server.netty.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<Packet> {


    private int clientId;

    public ServerHandler(NetService netService) {
    }

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		clientId = ctx.channel().hashCode();
	}

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {

        switch (packet.getType()) {

            case START_GAME:

                System.out.println("start. playerID: " + clientId);
                break;

            case EXIT_GAME:

                System.out.println("exit. playerID: " + clientId);
                break;

            case PRESSED_BUTTON:

                System.out.println("input. playerID: " + clientId);
                break;

            case RELEASED_BUTTON:

                break;

            case REQUEST_GAME_MAP:

                System.out.println("requestMap. playerID: " + clientId);
                break;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

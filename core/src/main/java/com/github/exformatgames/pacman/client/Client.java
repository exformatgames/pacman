package com.github.exformatgames.pacman.client;

//ChatGPT

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    private Channel channel;
    private EventLoopGroup group;

    public void connect(String host, int port) {
        group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ClientInitializer());

        bootstrap.connect(host, port).addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) {
					if (future.isSuccess()) {
						channel = future.channel();
						//System.out.println("Connected!");
					} else {
						//System.out.println("Failed to connect");
					}
				}
			});
    }

    public void send(Object msg) {
        if (channel != null) {
            channel.writeAndFlush(msg);
        }
    }

    public void disconnect() {
        if (group != null) {
            group.shutdownGracefully();
        }
    }
}

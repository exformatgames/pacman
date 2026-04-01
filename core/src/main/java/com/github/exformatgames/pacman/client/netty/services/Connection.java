package com.github.exformatgames.pacman.client.netty.services;

import com.github.exformatgames.pacman.client.netty.NettyClient;
import com.github.exformatgames.pacman.client.service.ConnectionService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Connection implements ConnectionService {

	private Channel channel;
	private final EventLoopGroup group;
	private final Bootstrap bootstrap;

	public Connection (final NettyClient client) {
		group = client.getGroup();
		this.bootstrap = client.getBootstrap();

		bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel (SocketChannel ch) {
					ChannelPipeline p = ch.pipeline();
					p.addLast(client.getDecoder());
					p.addLast(client.getEncoder());
					p.addLast(client.getClientHandler());
				}
			});
	}

	@Override
	public void connect (String host, int port) {
        ChannelFuture future = bootstrap.connect(host, port);

        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    channel = future.channel();
                    System.out.println("connected...");
                }
                else {
                    channel = null;
                    System.out.println("failed to connect...");

                    future.cause().printStackTrace();
                }
            }
        });
	}

	@Override
	public void disconnect () {
		if (channel != null) {
            channel.close();
        }
	}

	public Channel getChannel () {
		return channel;
	}

	@Override
	public boolean isConnected () {
		return channel != null && channel.isActive();
	}
}

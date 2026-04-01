package com.github.exformatgames.pacman.server.server.netty;

/**
 * Author: Exformat, ChatGPT
 */

import com.github.exformatgames.pacman.server.server.Server;
import com.github.exformatgames.pacman.server.server.netty.packet.PacketDecoder;
import com.github.exformatgames.pacman.server.server.netty.packet.PacketEncoder;
import com.github.exformatgames.pacman.server.server.netty.packet.PacketRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer extends Server {

    private final int port;

    private final PacketRegistry registry;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public NettyServer(int port) {
        this.port = port;
        this.netService = netService;
        this.registry = registry;
    }

    @Override
    public void start(int port) {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();

                    pipeline.addLast(new PacketDecoder(registry));
                    pipeline.addLast(new PacketEncoder(registry));
                    pipeline.addLast(new ServerHandler(netService));
                }
            });

        ChannelFuture future = bootstrap.bind(port);
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> f) {
                if (!f.isSuccess()) {
                    f.cause().printStackTrace();
                } else {
                    System.out.println("server started on port: " + port);
                }
            }
        });

        future.channel().closeFuture();
    }

    @Override
    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

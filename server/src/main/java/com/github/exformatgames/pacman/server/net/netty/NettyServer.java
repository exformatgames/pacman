package com.github.exformatgames.pacman.server.net.netty;

/**
 * Author: Exformat, ChatGPT
 */

import com.github.exformatgames.pacman.server.net.NetService;
import com.github.exformatgames.pacman.server.net.netty.packet.PacketDecoder;
import com.github.exformatgames.pacman.server.net.netty.packet.PacketEncoder;
import com.github.exformatgames.pacman.server.net.netty.packet.PacketRegistry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    private final int port;
    private final NetService netService;
    private final PacketRegistry registry;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public NettyServer(int port, NetService netService, PacketRegistry registry) {
        this.port = port;
        this.netService = netService;
        this.registry = registry;
    }

    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        try {
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

            //ChannelFuture future = bootstrap.bind(port).sync();
            ChannelFuture future = bootstrap.bind(port);
            future.addListener(f -> {
                if (!f.isSuccess()) {
                    f.cause().printStackTrace();
                } else {
                    System.out.println("server started on port: " + port);
                }
            });

            future.channel().closeFuture();


        } finally {

        }
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}

package com.github.exformatgames.pacman.managers.net.client;

import com.github.exformatgames.pacman.managers.NetManager;
import com.github.exformatgames.pacman.managers.net.client.packet.Packet;
import com.github.exformatgames.pacman.managers.net.client.packet.PacketDecoder;
import com.github.exformatgames.pacman.managers.net.client.packet.PacketEncoder;
import com.github.exformatgames.pacman.managers.net.client.packet.PacketRegistry;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    private Channel channel;
    private final PacketRegistry registry;
    private final NetManager netManager;

    public NettyClient(PacketRegistry registry, NetManager netManager) {
        this.registry = registry;
        this.netManager = netManager;
    }

    public void connect(String host, int port) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) {

					ChannelPipeline p = ch.pipeline();

					p.addLast(new PacketDecoder(registry));
					p.addLast(new PacketEncoder(registry));
					p.addLast(new ClientHandler(netManager));
				}
			});

        ChannelFuture future = bootstrap.connect(host, port).sync();
        channel = future.channel();
    }

    public void send(Packet packet) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(packet);
        }
    }

    public void disconnect() {
        if (channel != null) {
            channel.close();
        }
    }
}

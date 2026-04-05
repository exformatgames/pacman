package com.github.exformatgames.pacman.server.net.netty;

import com.github.exformatgames.pacman.server.GameWorld;
import com.github.exformatgames.pacman.server.net.netty.handlers.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.netty.packet.Packet;
import net.netty.packet.PacketType;
import net.netty.utils.PacketDecoder;
import net.netty.utils.PacketEncoder;
import net.netty.utils.PacketRegistry;
import net.netty.utils.readers.ButtonPressedPacketReader;
import net.netty.utils.readers.ButtonReleasedPacketReader;
import net.netty.utils.readers.EntityPacketReader;
import net.netty.utils.readers.MapPacketReader;
import net.netty.utils.writers.ButtonPressedPacketWriter;
import net.netty.utils.writers.ButtonReleasedPacketWriter;
import net.netty.utils.writers.EntityPacketWriter;
import net.netty.utils.writers.MapPacketWriter;

import java.util.HashMap;
import java.util.Map;

public class NettyServer {

    private final int port;

	//ох.. по хорошему нужно чтоб игра подписалась на события в нетсервисе и не было этой жесткой связи..
	//но мне бы для начала просто запустить этот бедлам..
	private final GameWorld gameWorld;

    private final PacketRegistry registry = new PacketRegistry();
	private final Map<Integer, Channel> clientMap = new HashMap<>();
	private final Map<Channel, Integer> channelMap = new HashMap<>();

	private int newClientID = 0;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
	//private ServerHandler serverHandler;

    public NettyServer (int port, GameWorld gameWorld) {
        this.port = port;
		this.gameWorld = gameWorld;

		//serverHandler = new ServerHandler(this);
        registerPackets();
    }

    public void start () {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel (SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();

					pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));

                    pipeline.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 4));
					pipeline.addLast(new PacketDecoder(registry));

                    pipeline.addLast(new LengthFieldPrepender(4));
                    pipeline.addLast(new PacketEncoder(registry));

                    ServerHandler serverHandler = new ServerHandler(NettyServer.this);

                    registerHandlers(serverHandler);
                    pipeline.addLast(serverHandler);
                }
            });

        ChannelFuture future = bootstrap.bind(port);
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
				@Override
				public void operationComplete (Future<? super Void> f) {
					if (!f.isSuccess()) {
						f.cause().printStackTrace();
					} else {
						System.out.println("server started on port: " + port);
					}
				}
			});

        future.channel().closeFuture();
    }

	public void sendTo (int clientId, Packet packet) {
        Channel channel = clientMap.get(clientId);
        if (channel != null) {
            channel.writeAndFlush(packet);
        }
    }

    public void broadcast (Packet packet) {
        for (int clientId : clientMap.keySet()) {
            sendTo(clientId, packet);
        }
    }

    public void stop () {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

	private void registerPackets () {
		registry.register(PacketType.PRESSED_BUTTON, new ButtonPressedPacketReader(), new ButtonPressedPacketWriter());
        registry.register(PacketType.RELEASED_BUTTON, new ButtonReleasedPacketReader(), new ButtonReleasedPacketWriter());
        registry.register(PacketType.RESPONSE_GAME_MAP, new MapPacketReader(), new MapPacketWriter());
        registry.register(PacketType.ENTITY_CREATED, new EntityPacketReader(), new EntityPacketWriter());
        registry.register(PacketType.ENTITY_REMOVED, new EntityPacketReader(), new EntityPacketWriter());
        registry.register(PacketType.ENTITY_TRANSFORMED, new EntityPacketReader(), new EntityPacketWriter());
	}

	private void registerHandlers (ServerHandler serverHandler) {
		serverHandler.addPacketHandler(PacketType.PRESSED_BUTTON, new ButtonPressedHandler(this, gameWorld));
		serverHandler.addPacketHandler(PacketType.RELEASED_BUTTON, new ButtonReleasedHandler(this, gameWorld));
		serverHandler.addPacketHandler(PacketType.JOIN_GAME, new JoinGameHandler(this, gameWorld));
		serverHandler.addPacketHandler(PacketType.EXIT_GAME, new ExitGameHandler(this, gameWorld));
		serverHandler.addPacketHandler(PacketType.REQUEST_GAME_MAP, new RequestGameMapHandler(this, gameWorld));
	}

	public int getClientID(Channel channel) {
		return channelMap.get(channel);
	}


	public void addClient (Channel channel) {
		int ID = ++newClientID;
		clientMap.put(ID, channel);
		channelMap.put(channel, ID);

		System.out.println("client connected: " + ID);
	}

	public void removeClient (Channel channel) {
		int ID = channelMap.get(channel);

		channelMap.remove(channel);
		clientMap.remove(ID);

        System.out.println("client disconnected: " + ID);
	}
}

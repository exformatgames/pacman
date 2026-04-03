package com.github.exformatgames.pacman.client.netty;

import com.github.exformatgames.pacman.client.Client;
import com.github.exformatgames.pacman.client.netty.packet.PacketDecoder;
import com.github.exformatgames.pacman.client.netty.packet.PacketEncoder;
import com.github.exformatgames.pacman.client.netty.packet.PacketRegistry;
import com.github.exformatgames.pacman.client.netty.packet.PacketType;
import com.github.exformatgames.pacman.client.netty.packet.readers.ButtonPressedPacketReader;
import com.github.exformatgames.pacman.client.netty.packet.readers.ButtonReleasedPacketReader;
import com.github.exformatgames.pacman.client.netty.packet.readers.EntityPacketReader;
import com.github.exformatgames.pacman.client.netty.packet.readers.MapPacketReader;
import com.github.exformatgames.pacman.client.netty.packet.writers.ButtonPressedPacketWriter;
import com.github.exformatgames.pacman.client.netty.packet.writers.ButtonReleasedPacketWriter;
import com.github.exformatgames.pacman.client.netty.packet.writers.EntityPacketWriter;
import com.github.exformatgames.pacman.client.netty.packet.writers.MapPacketWriter;
import com.github.exformatgames.pacman.client.netty.services.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import com.github.exformatgames.pacman.client.service.ServerGameEventService;

public class NettyClient extends Client {

	private final EventLoopGroup group;
	private final Bootstrap bootstrap;
	private final PacketRegistry registry;
	private final ClientHandler clientHandler;
	private final PacketEncoder encoder;
	private final PacketDecoder decoder;

	private final PacketSender packetSender;

	public NettyClient () {
        registry = new PacketRegistry();
        registerPackets();

        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
		clientHandler = new ClientHandler(this);
		encoder = new PacketEncoder(registry);
		decoder = new PacketDecoder(registry);

		connectionService = new Connection(this);
		gameSessionService = new GameSession(this);
		gameMapService = new GameMapServiceImpl(this);
		buttonEventService = new ButtonEventServiceImpl(this);
		gameEventService = new ServerGameEventService(this);
		testService  = new Test(this);


		packetSender = new PacketSender(this);
	}

	public PacketSender getPacketSender () {
		return packetSender;
	}

	public PacketEncoder getEncoder () {
		return encoder;
	}

	public PacketDecoder getDecoder () {
		return decoder;
	}

	public EventLoopGroup getGroup () {
		return group;
	}

	public Bootstrap getBootstrap () {
		return bootstrap;
	}

	public PacketRegistry getRegistry () {
		return registry;
	}

	public ClientHandler getClientHandler () {
		return clientHandler;
	}

	private void registerPackets() {
		registry.register(PacketType.PRESSED_BUTTON, new ButtonPressedPacketReader(), new ButtonPressedPacketWriter());
		registry.register(PacketType.RELEASED_BUTTON, new ButtonReleasedPacketReader(), new ButtonReleasedPacketWriter());
		registry.register(PacketType.RESPONSE_GAME_MAP, new MapPacketReader(), new MapPacketWriter());
		registry.register(PacketType.ENTITY_CREATED, new EntityPacketReader(), new EntityPacketWriter());
		registry.register(PacketType.ENTITY_REMOVED, new EntityPacketReader(), new EntityPacketWriter());
		registry.register(PacketType.ENTITY_TRANSFORMED, new EntityPacketReader(), new EntityPacketWriter());
	}

	@Override
	public void dispose () {
		connectionService.disconnect();
		if (group != null) {
			group.shutdownGracefully();
		}
	}
}

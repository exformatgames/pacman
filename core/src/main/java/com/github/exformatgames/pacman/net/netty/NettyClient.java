package com.github.exformatgames.pacman.net.netty;

import com.github.exformatgames.pacman.net.Client;
import com.github.exformatgames.pacman.net.netty.services.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import com.github.exformatgames.pacman.net.service.ServerGameEventService;
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

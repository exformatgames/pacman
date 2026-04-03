package com.github.exformatgames.pacman.server.net;

import com.github.exformatgames.pacman.server.data.MapData;
import com.github.exformatgames.pacman.server.data.EntityData;
import com.github.exformatgames.pacman.server.netty.NettyServer;
import io.netty.channel.Channel;
import com.github.exformatgames.pacman.server.netty.packet.ResponseGameMapPacket;
import com.github.exformatgames.pacman.server.netty.packet.EntityPacket;
import com.github.exformatgames.pacman.server.netty.packet.PacketType;

//фпень.. пишу минуту, название выбираю 15...
public class SendService {
	
	private final NettyServer server;

	public SendService (NettyServer server) {
		this.server = server;
	}
	
	public void sendMapData(int clientID, MapData data) {
		ResponseGameMapPacket packet = new ResponseGameMapPacket();
		packet.map = data;
		server.sendTo(clientID, packet);
	}
	
	public void sendEntityCreated(int clientID, EntityData data) {
		EntityPacket packet = new EntityPacket();
		packet.type = PacketType.ENTITY_CREATED;
		packet.data = data;
		server.sendTo(clientID, packet);
	}
	
	public void sendEntityRemoved(int clientID, EntityData data) {
		EntityPacket packet = new EntityPacket();
		packet.type = PacketType.ENTITY_REMOVED;
		packet.data = data;
		server.sendTo(clientID, packet);
	}
	
	public void sendEntityTransformed(int clientID, EntityData data) {
		EntityPacket packet = new EntityPacket();
		packet.type = PacketType.ENTITY_TRANSFORMED;
		packet.data = data;
		server.sendTo(clientID, packet);
	}
}

package com.github.exformatgames.pacman.server.net;

import com.github.exformatgames.pacman.server.net.netty.NettyServer;
import data.EntityData;
import data.MapData;
import net.netty.packet.EntityPacket;
import net.netty.packet.PacketType;
import net.netty.packet.ResponseGameMapPacket;

//фпень.. пишу минуту, название выбираю 15...
public class SendService {

	private final NettyServer server;

	public SendService (NettyServer server) {
		this.server = server;
	}

	public void sendMapData(int clientID, MapData data) {
        System.out.println("SendService: sendMap. clientID: " + clientID);
		ResponseGameMapPacket packet = new ResponseGameMapPacket();
		packet.map = data;
		server.sendTo(clientID, packet);
	}

	public void sendEntityCreated(int clientID, EntityData data) {
		EntityPacket packet = new EntityPacket();
		packet.setType(PacketType.ENTITY_CREATED);
		packet.data = data;
		server.sendTo(clientID, packet);
	}

	public void sendEntityRemoved(int clientID, EntityData data) {
		EntityPacket packet = new EntityPacket();
		packet.setType(PacketType.ENTITY_REMOVED);
		packet.data = data;
		server.sendTo(clientID, packet);
	}

	public void sendEntityTransformed(int clientID, EntityData data) {
		EntityPacket packet = new EntityPacket();
        packet.setType(PacketType.ENTITY_TRANSFORMED);
		packet.data = data;
		server.sendTo(clientID, packet);
	}
}

package com.github.exformatgames.pacman.server.net.netty.packet.readers;

import com.github.exformatgames.pacman.server.data.EntityData;
import com.github.exformatgames.pacman.server.data.MapData;
import com.github.exformatgames.pacman.server.net.netty.packet.MapPacket;
import com.github.exformatgames.pacman.server.net.netty.packet.PacketType;
import io.netty.buffer.ByteBuf;

public class MapPacketReader implements PacketReader<MapPacket> {

	private EntityPacketReader entityReader = new EntityPacketReader();

	@Override
	public MapPacket read (ByteBuf in) {
		MapData data = new MapData();
		data.ID = in.readInt();
		data.width = in.readInt();
		data.height = in.readInt();

		int entityCount = in.readInt();

		data.entityList = new EntityData[entityCount];

		for (int i = 0; i < entityCount; i++) {
           EntityData entityData = entityReader.readBuffer(in);
		   data.entityList[i] = entityData;
        }

		MapPacket packet = new MapPacket();
		packet.data = data;
		packet.type = PacketType.RESPONSE_GAME_MAP;
		return packet;
	}
}

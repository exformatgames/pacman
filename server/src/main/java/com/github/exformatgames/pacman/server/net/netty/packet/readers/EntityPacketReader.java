package com.github.exformatgames.pacman.server.net.netty.packet.readers;

import com.github.exformatgames.pacman.server.data.EntityData;
import com.github.exformatgames.pacman.server.data.EntityType;
import com.github.exformatgames.pacman.server.data.PositionData;
import com.github.exformatgames.pacman.server.net.netty.packet.EntityPacket;

import io.netty.buffer.ByteBuf;

public class EntityPacketReader implements PacketReader<EntityPacket> {
    @Override
    public EntityPacket read(ByteBuf in) {

        //EntityPacket packet = new EntityPacket();
		//packet.data = readBuffer(in);
        //return packet;

		return null;
    }


    public EntityData readBuffer(ByteBuf in) {
        int packetSize = 4 * 5; //id = 4, type = 4, x = 4, y = 4, speed = 4

        if (in.readableBytes() < packetSize) {
            return null;
        }

        EntityData data = new EntityData();
        PositionData pos = new PositionData();

        data.ID = in.readInt();
        data.type = EntityType.values()[in.readInt()];
        pos.x = in.readInt();
        pos.y = in.readInt();
        data.speed = in.readInt();

        data.position = pos;

        return data;
    }
}

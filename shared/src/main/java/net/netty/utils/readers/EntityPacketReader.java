package net.netty.utils.readers;

import data.EntityData;
import data.EntityType;
import data.PositionData;
import net.netty.packet.EntityPacket;
import io.netty.buffer.ByteBuf;

public class EntityPacketReader implements PacketReader<EntityPacket> {
    @Override
    public EntityPacket read(ByteBuf in) {

        EntityPacket packet = new EntityPacket();
		packet.data = readBuffer(in);
        return packet;
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

package net.netty.packet;


import data.EntityData;

public class EntityPacket extends Packet {

    public EntityData data;
	public PacketType type;

	@Override
	public PacketType getType () {
		return type;
	}

    @Override
    public String toString() {
        return "EntityPacket{" +
            "data=" + data.toString() +
            ", type=" + type +
            '}';
    }
}

package net.netty.packet;


import data.EntityData;

public class EntityPacket extends Packet {

    public EntityData data;

    @Override
    public String toString() {
        return "EntityPacket{" +
            "data=" + data.toString() +
            ", type=" + type +
            '}';
    }
}

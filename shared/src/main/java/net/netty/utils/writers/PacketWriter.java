package net.netty.utils.writers;

import net.netty.packet.Packet;
import io.netty.buffer.ByteBuf;

public interface PacketWriter<T extends Packet> {
    void write(T packet, ByteBuf out);
}

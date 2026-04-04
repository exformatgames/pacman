package net.netty.utils.readers;

import net.netty.packet.Packet;
import io.netty.buffer.ByteBuf;

public interface PacketReader<T extends Packet> {

    T read(ByteBuf in);
}

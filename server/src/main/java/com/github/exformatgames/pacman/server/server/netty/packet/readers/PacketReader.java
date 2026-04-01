package com.github.exformatgames.pacman.server.server.netty.packet.readers;

import com.github.exformatgames.pacman.server.server.netty.packet.Packet;
import io.netty.buffer.ByteBuf;

public interface PacketReader<T extends Packet> {

    T read(ByteBuf in);
}

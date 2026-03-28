package com.github.exformatgames.pacman.client.packet;

import io.netty.buffer.ByteBuf;

public interface Packet {
    int getId();
    void write(ByteBuf out);
    void read(ByteBuf in);
}

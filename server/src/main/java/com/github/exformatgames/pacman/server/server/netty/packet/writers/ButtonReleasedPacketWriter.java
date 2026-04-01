package com.github.exformatgames.pacman.server.server.netty.packet.writers;

import com.github.exformatgames.pacman.server.server.netty.packet.ButtonReleasedPacket;
import io.netty.buffer.ByteBuf;

public class ButtonReleasedPacketWriter implements PacketWriter<ButtonReleasedPacket> {

    @Override
    public void write(ButtonReleasedPacket packet, ByteBuf out) {
        out.writeInt(packet.action.ordinal());
    }
}

package com.github.exformatgames.pacman.client.netty.packet.writers;

import com.github.exformatgames.pacman.client.netty.packet.InputPacket;
import io.netty.buffer.ByteBuf;

public class InputPacketWriter implements PacketWriter<InputPacket> {

    @Override
    public void write(InputPacket packet, ByteBuf out) {
        out.writeInt(packet.action.ordinal());
        out.writeBoolean(packet.pressed);
    }
}

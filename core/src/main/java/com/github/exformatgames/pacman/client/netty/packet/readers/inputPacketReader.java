package com.github.exformatgames.pacman.client.netty.packet.readers;

import com.github.exformatgames.pacman.data.InputData;
import com.github.exformatgames.pacman.client.netty.packet.InputPacket;

import io.netty.buffer.ByteBuf;

public class inputPacketReader implements PacketReader<InputPacket> {
    @Override
    public InputPacket read(ByteBuf in) {
        InputPacket packet = new InputPacket();

        packet.action = InputData.values()[in.readInt()];
        packet.pressed = in.readBoolean();

        return packet;
    }
}

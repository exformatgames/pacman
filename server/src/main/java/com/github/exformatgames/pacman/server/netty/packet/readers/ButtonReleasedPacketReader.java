package com.github.exformatgames.pacman.server.netty.packet.readers;

import com.github.exformatgames.pacman.server.data.InputData;
import com.github.exformatgames.pacman.server.netty.packet.ButtonReleasedPacket;
import io.netty.buffer.ByteBuf;

public class ButtonReleasedPacketReader implements PacketReader<ButtonReleasedPacket> {
    @Override
    public ButtonReleasedPacket read(ByteBuf in) {
        ButtonReleasedPacket packet = new ButtonReleasedPacket();


        packet.action = InputData.values()[in.readInt()];

        return packet;
    }
}

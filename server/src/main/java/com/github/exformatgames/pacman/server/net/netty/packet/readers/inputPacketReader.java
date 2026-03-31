package com.github.exformatgames.pacman.server.net.netty.packet.readers;

import com.github.exformatgames.pacman.server.data.InputAction;
import com.github.exformatgames.pacman.server.net.netty.packet.InputPacket;

import io.netty.buffer.ByteBuf;

public class inputPacketReader implements PacketReader<InputPacket> {
    @Override
    public InputPacket read(ByteBuf in) {
        InputPacket packet = new InputPacket();

        packet.action = InputAction.values()[in.readInt()];
        packet.pressed = in.readBoolean();

        return packet;
    }
}

package com.github.exformatgames.pacman.client.netty.packet;

import com.github.exformatgames.pacman.data.InputData;

public class InputPacket extends Packet {

    public InputData action;
    public boolean pressed;



    @Override
    public PacketType getType() {
        return PacketType.INPUT;
    }
}

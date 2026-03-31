package com.github.exformatgames.pacman.server.net.netty.packet;


import com.github.exformatgames.pacman.server.data.InputAction;

public class InputPacket extends Packet {

    public InputAction action;
    public boolean pressed;



    @Override
    public PacketType getType() {
        return PacketType.INPUT;
    }
}

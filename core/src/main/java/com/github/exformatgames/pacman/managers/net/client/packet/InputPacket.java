package com.github.exformatgames.pacman.managers.net.client.packet;

import com.github.exformatgames.pacman.managers.net.InputAction;

public class InputPacket extends Packet {

    public InputAction action;
    public boolean pressed;



    @Override
    public PacketType getType() {
        return PacketType.INPUT;
    }
}

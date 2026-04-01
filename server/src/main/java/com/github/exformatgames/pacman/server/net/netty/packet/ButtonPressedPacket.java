package com.github.exformatgames.pacman.server.net.netty.packet;


import com.github.exformatgames.pacman.server.data.InputData;

public class ButtonPressedPacket extends Packet {

	public InputData action;

	@Override
	public PacketType getType () {
		return PacketType.RELEASED_BUTTON;
	}

    @Override
    public String toString() {
        return "ButtonPressedPacket{" +
            "action=" + action +
            '}';
    }
}

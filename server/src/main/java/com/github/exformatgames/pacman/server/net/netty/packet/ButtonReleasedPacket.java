package com.github.exformatgames.pacman.server.net.netty.packet;

import com.github.exformatgames.pacman.client.netty.packet.Packet;
import com.github.exformatgames.pacman.data.InputData;

public class ButtonReleasedPacket extends Packet {

	public InputData action;

	@Override
	public PacketType getType () {
		return PacketType.PRESSED_BUTTON;
	}

    @Override
    public String toString() {
        return "ButtonReleasedPacket{" +
            "action=" + action +
            '}';
    }
}

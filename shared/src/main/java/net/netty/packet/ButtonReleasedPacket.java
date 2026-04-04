package net.netty.packet;

import data.InputData;

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

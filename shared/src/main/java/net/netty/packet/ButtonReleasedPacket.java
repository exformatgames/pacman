package net.netty.packet;

import data.InputData;

public class ButtonReleasedPacket extends Packet {

	public InputData action;

	@Override
	public PacketType getType () {
		return PacketType.RELEASED_BUTTON;
	}

    @Override
    public String toString() {
        return "ButtonReleasedPacket{" +
            "action=" + action +
            '}';
    }
}

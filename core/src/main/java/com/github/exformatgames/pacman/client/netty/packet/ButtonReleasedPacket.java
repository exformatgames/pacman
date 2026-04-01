package com.github.exformatgames.pacman.client.netty.packet;

import com.github.exformatgames.pacman.data.InputData;

public class ButtonReleasedPacket extends Packet {

	public InputData action;

	@Override
	public PacketType getType () {
		return PacketType.PRESSED_BUTTON;
	}
}

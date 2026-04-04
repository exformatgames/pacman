package com.github.exformatgames.pacman.net.netty.services;

import com.github.exformatgames.pacman.net.netty.NettyClient;
import com.github.exformatgames.pacman.net.service.ButtonEventService;
import data.InputData;
import net.netty.packet.ButtonPressedPacket;
import net.netty.packet.ButtonReleasedPacket;

public class ButtonEventServiceImpl implements ButtonEventService {

	private final NettyClient client;

	public ButtonEventServiceImpl (NettyClient client) {
		this.client = client;
	}

	@Override
	public void onButtonPressed (InputData inputData) {
		ButtonPressedPacket p = new ButtonPressedPacket();
		p.action = inputData;
		client.getPacketSender().send(p);
	}

	@Override
	public void onButtonReleased (InputData inputData) {
		ButtonReleasedPacket p = new ButtonReleasedPacket();
		p.action = inputData;
		client.getPacketSender().send(p);
	}
}

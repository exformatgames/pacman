package com.github.exformatgames.pacman.client.netty.services;

import com.github.exformatgames.pacman.data.InputData;
import com.github.exformatgames.pacman.client.netty.NettyClient;
import com.github.exformatgames.pacman.client.netty.packet.ButtonPressedPacket;
import com.github.exformatgames.pacman.client.netty.packet.ButtonReleasedPacket;
import com.github.exformatgames.pacman.client.service.ButtonEventService;

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

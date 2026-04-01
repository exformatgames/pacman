package com.github.exformatgames.pacman.client.service;

public interface ConnectionService {
	void connect(String host, int port);
	void disconnect();
	boolean isConnected();
}

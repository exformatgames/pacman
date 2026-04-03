package com.github.exformatgames.pacman.server.netty.packet;

public enum PacketType {
	PING,
	PONG,
    JOIN_GAME,
    EXIT_GAME,
	PRESSED_BUTTON,
	RELEASED_BUTTON,
    REQUEST_GAME_MAP,
    RESPONSE_GAME_MAP,
    ENTITY_CREATED,
    ENTITY_REMOVED,
    ENTITY_TRANSFORMED,
}

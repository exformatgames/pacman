package com.github.exformatgames.pacman.server.net.netty.packet;

public enum PacketType {
    START_GAME,
    EXIT_GAME,
    INPUT,
    REQUEST_GAME_MAP,
    RESPONSE_GAME_MAP,
    ENTITY_CREATED,
    ENTITY_REMOVED,
    ENTITY_POSITION_CHANGED,
}

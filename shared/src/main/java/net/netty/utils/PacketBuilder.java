package net.netty.utils;

import net.netty.packet.*;

public class PacketBuilder {

    public static Packet build(PacketType type) {
        switch (type) {
            case PING: {
                return new Packet() {
                    @Override
                    public PacketType getType() {
                        return PacketType.PING;
                    }
                };
            }
            case PONG: {
                return new Packet() {
                    @Override
                    public PacketType getType() {
                        return PacketType.PONG;
                    }
                };
            }
            case JOIN_GAME: {
                return new JoinGamePacket();
            }
            case EXIT_GAME: {
                return new ExitGamePacket();
            }
            case REQUEST_GAME_MAP: {
                return new RequestGameMapPacket();
            }
        }

        return null;
    }
}

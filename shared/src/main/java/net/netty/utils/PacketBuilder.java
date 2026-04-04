package net.netty.utils;

import net.netty.packet.Packet;
import net.netty.packet.PacketType;

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
                return new Packet() {
                    @Override
                    public PacketType getType() {
                        return PacketType.JOIN_GAME;
                    }
                };
            }
            case EXIT_GAME: {
                return new Packet() {
                    @Override
                    public PacketType getType() {
                        return PacketType.EXIT_GAME;
                    }
                };
            }
            case REQUEST_GAME_MAP: {
                return new Packet() {
                    @Override
                    public PacketType getType() {
                        return PacketType.REQUEST_GAME_MAP;
                    }
                };
            }
        }

        return null;
    }
}

package net.netty.utils;

import net.netty.packet.PacketType;
import net.netty.utils.readers.PacketReader;
import net.netty.utils.writers.PacketWriter;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistry {
    private final Map<Integer, PacketWriter> writers = new HashMap<>();
    private final Map<Integer, PacketReader> readers = new HashMap<>();

    public void register(PacketType type, PacketReader reader, PacketWriter writer) {
        writers.put(type.ordinal(), writer);
        readers.put(type.ordinal(), reader);
    }

    public PacketWriter getWriter(PacketType type) {
        return writers.get(type.ordinal());
    }

    public PacketReader getReader(PacketType type) {
        return readers.get(type.ordinal());
    }
}

package com.github.exformatgames.pacman.managers.net.client.packet;

import com.badlogic.gdx.utils.IntMap;
import com.github.exformatgames.pacman.managers.net.client.packet.readers.PacketReader;
import com.github.exformatgames.pacman.managers.net.client.packet.writers.PacketWriter;

public class PacketRegistry {
    private final IntMap<PacketWriter> writers = new IntMap<>();
    private final IntMap<PacketReader> readers = new IntMap<>();

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

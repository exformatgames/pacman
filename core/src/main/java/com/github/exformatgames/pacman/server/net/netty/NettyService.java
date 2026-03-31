package com.github.exformatgames.pacman.server.net.netty;

/**
 * Author: Exformat, ChatGPT
 */

import com.github.exformatgames.pacman.server.data.EntityData;
import com.github.exformatgames.pacman.server.data.InputAction;
import com.github.exformatgames.pacman.server.data.MapData;
import com.github.exformatgames.pacman.server.net.NetManager;
import com.github.exformatgames.pacman.server.net.NetService;
import com.github.exformatgames.pacman.server.net.netty.packet.Packet;
import com.github.exformatgames.pacman.server.net.netty.packet.PacketRegistry;
import com.github.exformatgames.pacman.server.net.netty.packet.PacketType;
import com.github.exformatgames.pacman.server.net.netty.packet.readers.EntityPacketReader;
import com.github.exformatgames.pacman.server.net.netty.packet.readers.MapPacketReader;
import com.github.exformatgames.pacman.server.net.netty.packet.readers.inputPacketReader;
import com.github.exformatgames.pacman.server.net.netty.packet.writers.EntityPacketWriter;
import com.github.exformatgames.pacman.server.net.netty.packet.writers.InputPacketWriter;
import com.github.exformatgames.pacman.server.net.netty.packet.writers.MapPacketWriter;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;

public class NettyService implements NetService {

    private final NetManager netManager;

    // clientId -> Channel
    private final Map<Integer, Channel> clients = new HashMap<>();

    private final PacketRegistry registry = new PacketRegistry();
    private final NettyServer server;


    public NettyService(NetManager netManager) {
        this.netManager = netManager;

        registry.register(PacketType.INPUT, new inputPacketReader(), new InputPacketWriter());
        registry.register(PacketType.RESPONSE_GAME_MAP, new MapPacketReader(), new MapPacketWriter());
        registry.register(PacketType.ENTITY_CREATED, new EntityPacketReader(), new EntityPacketWriter());
        registry.register(PacketType.ENTITY_REMOVED, new EntityPacketReader(), new EntityPacketWriter());
        registry.register(PacketType.ENTITY_POSITION_CHANGED, new EntityPacketReader(), new EntityPacketWriter());

        server = new NettyServer(8080, this, registry);
    }

    @Override
    public void startServer() {
        try {
            server.start();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerClient(int clientId, Channel channel) {
        clients.put(clientId, channel);
    }

    // =========================
    // Client -> Server
    // =========================

    @Override
    public void onClientConnected(int clientId) {
        netManager.getGameService().addPlayer(clientId);
    }

    @Override
    public void onClientDisconnected(int clientId) {
        netManager.getGameService().removePlayer(clientId);
        clients.remove(clientId);
    }

    @Override
    public void onStartGame(int clientId) {
        // можно пока игнор
    }

    @Override
    public void onExitGame(int clientId) {
        netManager.getGameService().removePlayer(clientId);
    }

    @Override
    public void onInput(int clientId, InputAction action, boolean pressed) {
        if (pressed) {
            netManager.getGameService().pressedButton(clientId, action);
        } else {
            netManager.getGameService().releaseButton(clientId, action);
        }
    }

    @Override
    public void onRequestMap(int clientId) {
        MapData map = netManager.getGameService().getMapData();

        //sendTo(clientId, new MapPacket(map));
    }

    // =========================
    // Server -> Client
    // =========================

    @Override
    public void createdEntity(EntityData data) {
        //broadcast(new EntityCreatePacket(data));
    }

    @Override
    public void positionChanged(EntityData data) {
        //broadcast(new EntityPositionPacket(data));
    }

    @Override
    public void removedEntity(EntityData data) {
        //broadcast(new EntityRemovePacket(data));
    }

    @Override
    public void sendMapData(MapData data) {
        //broadcast(new MapPacket(data));
    }

    // =========================
    // Helpers
    // =========================

    private void sendTo(int clientId, Packet packet) {
        Channel ch = clients.get(clientId);
        if (ch != null) {
            ch.writeAndFlush(packet);
        }
    }

    private void broadcast(Packet packet) {
        for (Channel ch : clients.values()) {
            ch.writeAndFlush(packet);
        }
    }
}

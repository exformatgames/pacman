package com.github.exformatgames.pacman.server.server.netty;

/**
 * Author: Exformat, ChatGPT
 */

import com.github.exformatgames.pacman.server.data.EntityData;
import com.github.exformatgames.pacman.server.data.MapData;
import com.github.exformatgames.pacman.server.net.NetManager;
import com.github.exformatgames.pacman.server.net.NetService;
import com.github.exformatgames.pacman.server.net.netty.packet.readers.inputPacketReader;
import com.github.exformatgames.pacman.server.net.netty.packet.writers.InputPacketWriter;
import com.github.exformatgames.pacman.server.server.netty.packet.*;
import com.github.exformatgames.pacman.server.server.netty.packet.readers.EntityPacketReader;
import com.github.exformatgames.pacman.server.server.netty.packet.readers.MapPacketReader;
import com.github.exformatgames.pacman.server.server.netty.packet.writers.EntityPacketWriter;
import com.github.exformatgames.pacman.server.server.netty.packet.writers.MapPacketWriter;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class NettyService implements NetService {

    private final NetManager netManager;

    // clientId -> Channel
    private final Map<Integer, Channel> clients = new HashMap<>();

    private final PacketRegistry registry = new PacketRegistry();
    private final NettyServer server;

    private final Map<Integer, Integer> activePlayerIDList = new HashMap<>();


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
        System.out.println("Client connected: " + clientId);
    }

    @Override
    public void onClientDisconnected(int clientId) {
        netManager.getGameService().removePlayer(clientId);
        clients.remove(clientId);
        System.out.println("Client disconnected: " + clientId);
        activePlayerIDList.remove(clientId);
    }

    @Override
    public void onStartGame(int clientId) {
        System.out.println("Client start game: " + clientId);
        netManager.getGameService().addPlayer(clientId);
        activePlayerIDList.put(clientId, clientId);
    }

    @Override
    public void onExitGame(int clientId) {
        System.out.println("Client exit game: " + clientId);
        netManager.getGameService().removePlayer(clientId);
        activePlayerIDList.remove(clientId);
    }

    @Override
    public void onInput(int clientId, InputAction action, boolean pressed) {
        System.out.println("Client input: " + clientId + " " + action + " " + pressed);
        if (pressed) {
            netManager.getGameService().pressedButton(clientId, action);
        } else {
            netManager.getGameService().releaseButton(clientId, action);
        }
    }

    @Override
    public void onRequestMap(int clientId) {
        System.out.println("Client request map: " + clientId);
        MapData map = netManager.getGameService().getMapData();

        MapPacket packet = new MapPacket();
        packet.data = map;
        packet.type = PacketType.RESPONSE_GAME_MAP;

        sendTo(clientId, packet);
    }

    // =========================
    // Server -> Client
    // =========================

    @Override
    public void createdEntity(EntityData data) {
        EntityPacket packet = new EntityPacket();
        packet.data = data;
        packet.type = PacketType.ENTITY_CREATED;

        broadcast(packet);
    }

    @Override
    public void positionChanged(EntityData data) {
        EntityPacket packet = new EntityPacket();
        packet.data = data;
        packet.type = PacketType.ENTITY_POSITION_CHANGED;

        broadcast(packet);
    }

    @Override
    public void removedEntity(EntityData data) {
        EntityPacket packet = new EntityPacket();
        packet.data = data;
        packet.type = PacketType.ENTITY_REMOVED;

        broadcast(packet);
    }

    @Override
    public void sendMapData(MapData data) {
        MapPacket packet = new MapPacket();
        packet.data = data;
        packet.type = PacketType.RESPONSE_GAME_MAP;
        broadcast(packet);
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
        for (int clientId : activePlayerIDList.keySet()) {
            sendTo(clientId, packet);
        }
    }
}

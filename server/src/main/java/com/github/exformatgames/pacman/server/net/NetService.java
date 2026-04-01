package com.github.exformatgames.pacman.server.net;

import com.github.exformatgames.pacman.server.data.EntityData;
import com.github.exformatgames.pacman.server.data.MapData;

/**
 * Author: Exformat, ChatGPT
 */
public interface NetService {

    void startServer();

    // =========================
    // Client -> Server (входящие)
    // =========================

    void onClientConnected(int clientId);

    void onClientDisconnected(int clientId);

    void onStartGame(int clientId);

    void onExitGame(int clientId);

    void onInput(int clientId, InputAction action, boolean pressed);

    void onRequestMap(int clientId);


    // =========================
    // Server -> Client (исходящие)
    // =========================

    void createdEntity(EntityData data);

    void positionChanged(EntityData data);

    void removedEntity(EntityData data);

    void sendMapData(MapData data);
}



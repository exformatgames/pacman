package com.github.exformatgames.pacman.server.server;

import com.github.exformatgames.pacman.server.server.service.*;

public abstract class Server {

    protected ConnectionService connectionService;
    protected GameSessionService gameSessionService;
    protected GameMapService gameMapService;
    protected ButtonEventService buttonEventService;
    protected ServerGameEventService gameEventService = new ServerGameEventService();;

    public abstract void start(int port);

    public abstract void stop();

    public ConnectionService getConnectionService() {
        return connectionService;
    }
    public GameSessionService getGameSessionService() {
        return gameSessionService;
    }
    public GameMapService getGameMapService() {
        return gameMapService;
    }
    public ButtonEventService getButtonEventService() {
        return buttonEventService;
    }
    public ServerGameEventService getGameEventService() {
        return gameEventService;
    }
}

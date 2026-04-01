package com.github.exformatgames.pacman.server;

import com.github.exformatgames.pacman.server.data.MapData;
import com.github.exformatgames.pacman.server.net.NetManager;
import com.github.exformatgames.pacman.server.net.netty.NettyService;

/** Launches the server application. */
public class ServerLauncher {

    private static long SERVER_UPDATE_TICK = 1_000_000_000 / 60;


    public static void main(String[] args) {
        MapLoader mapLoader = new MapLoader();
        MapData mapData = mapLoader.load("pacman_field.txt");

        GameWorld gameWorld = new GameWorld(mapData);

        NetManager netManager = new NetManager();
        netManager.setNetService(new NettyService(netManager));
        netManager.setGameService(gameWorld);

        System.out.println("leeeeeeeeeee..?...");

        netManager.getNetService().startServer();

        System.out.println("start?...");

        long timer = 0;
        long oldTime = System.nanoTime();

        while (true) {
            long currentTime = System.nanoTime();
            long deltaNanoTime = currentTime - oldTime;

            timer += deltaNanoTime;
            oldTime = currentTime;

            if (timer > SERVER_UPDATE_TICK) {
                float deltaTime = (float) timer / 1_000_000_000;
                gameWorld.update(deltaTime);
                timer = 0;
            }
        }
    }
}

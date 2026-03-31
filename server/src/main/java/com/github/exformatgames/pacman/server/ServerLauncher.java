package com.github.exformatgames.pacman.server;

import com.github.exformatgames.pacman.server.data.MapData;
import com.github.exformatgames.pacman.server.net.NetManager;
import com.github.exformatgames.pacman.server.net.netty.NettyService;

/** Launches the server application. */
public class ServerLauncher {

    private static float SERVERUPDATETIME = 0.033f;


    public static void main(String[] args) {
        MapLoader mapLoader = new MapLoader();
        MapData mapData = mapLoader.load("pacman_field.txt");

        GameWorldV2 gameWorld = new GameWorldV2(mapData);

        NetManager netManager = new NetManager();
        netManager.setNetService(new NettyService(netManager));
        netManager.setGameService(gameWorld);


        long timer = 0;
        long oldTime = System.nanoTime();

        while (true) {
            long currentTime = System.nanoTime();
            long deltaNanoTime = currentTime - oldTime;

            float deltaTime = 0;//convert nano to float

            if (deltaTime > SERVERUPDATETIME) {
                gameWorld.update(deltaTime);
                deltaTime = 0;
            }
        }
    }
}

package com.github.exformatgames.pacman.server;


import com.github.exformatgames.pacman.server.net.netty.NettyServer;
import com.github.exformatgames.pacman.server.net.NetService;
import data.MapData;

public class ServerLauncher {

    private static long SERVER_UPDATE_TICK = 1_000_000_000 / 60;


    public static void main (String[] args) {
        MapLoader mapLoader = new MapLoader();
        MapData mapData = mapLoader.load("pacman_field.txt");

		GameWorld gameWorld = new GameWorld(mapData);
		NettyServer server = new NettyServer(8080, gameWorld);

		NetService netService = new NetService(gameWorld, server);

		gameWorld.setNetService(netService);


		server.start();
        System.out.println("start.");

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

package com.github.exformatgames.pacman.server;

import com.github.exformatgames.pacman.server.data.MapData;

/**
 * @author Exformat, ChatGPT
 */
public class MapLoader {

    public MapData load(String path) {

        java.io.BufferedReader reader = null;

        try {
            reader = new java.io.BufferedReader(
				new java.io.FileReader(path)
            );

            MapParser parser = new MapParser();
            return parser.parse(reader);

        } catch (java.io.IOException e) {
            throw new RuntimeException("Failed to load map: " + path, e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (java.io.IOException ignored) {
                }
            }
        }
    }
}

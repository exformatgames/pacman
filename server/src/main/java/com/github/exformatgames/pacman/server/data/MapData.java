package com.github.exformatgames.pacman.server.data;

import java.util.Arrays;

public class MapData {
	public int ID;

	public int width;
	public int height;

	public EntityData[] entityList;

    @Override
    public String toString() {
        return "MapData{" +
                "ID=" + ID +
                ", width=" + width +
                ", height=" + height +
                ", entityList=" + Arrays.toString(entityList) +
                '}';
    }
}

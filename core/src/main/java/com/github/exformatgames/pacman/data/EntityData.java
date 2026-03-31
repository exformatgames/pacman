package com.github.exformatgames.pacman.data;

public class EntityData {
	public int ID;
    public EntityType type;
    public PositionData position;
    public int speed;

    public EntityData() {
        position = new PositionData();
        speed = 7;
    }
}

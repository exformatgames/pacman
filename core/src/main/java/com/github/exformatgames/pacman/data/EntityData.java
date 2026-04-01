package com.github.exformatgames.pacman.data;

public class EntityData {
	public int ID;
    public EntityType type;
    public PositionData position;
    public int speed;

    @Override
    public String toString() {
        return "EntityData{" +
            "ID=" + ID +
            ", type=" + type +
            ", position=" + position +
            ", speed=" + speed +
            '}';
    }
}

package com.github.exformatgames.pacman.data;

public class MapData {
	
	public int ID;

	public int width;
	public int height;
	
	public WallData[] wallList;
	public PacmanData[] pacmanList;
	public PlayerData[] playerList;
	public FoodData[] foodList;
	public GhostData[] ghostList;
	public BonusData[] bonusList;
	
	public PositionData[] pacmanSpawnPoint;
	public PositionData[] ghostSpawnPoint;
	public PositionData[] bonusSpawnPoint;
}

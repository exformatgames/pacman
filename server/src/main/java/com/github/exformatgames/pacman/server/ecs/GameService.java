package com.github.exformatgames.pacman.server.ecs;

import com.github.exformatgames.pacman.server.data.EntityData;
import com.github.exformatgames.pacman.server.data.MapData;

public interface GameService {
	void pressedButton (int playerID, InputAction action);

	void releaseButton (int playerID, InputAction action);

	void addPlayer (int ID);

	void removePlayer (int ID);

	void createdEntity (EntityData data);

    void positionChanged (EntityData data);

	void removedEntity (EntityData data);

	MapData getMapData ();
}

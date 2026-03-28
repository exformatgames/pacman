package com.github.exformatgames.pacman.server.ecs.systems.map;

import com.artemis.systems.IntervalIteratingSystem;
import com.artemis.Aspect;
import com.github.exformatgames.pacman.server.ecs.GameConstants;

public class DeltaMapDataSystem extends IntervalIteratingSystem {

	public DeltaMapDataSystem() {
		super(Aspect.all(), GameConstants.UPDATE_MAPDATA_TIME);
	}
	
	
	@Override
	protected void process (int entityID) {
		
	}
}

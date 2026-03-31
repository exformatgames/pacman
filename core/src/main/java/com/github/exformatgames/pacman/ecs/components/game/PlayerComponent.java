package com.github.exformatgames.pacman.ecs.components.game;

import com.artemis.PooledComponent;

public class PlayerComponent extends PooledComponent {

    public int ID;
    public int pacmanID;

    @Override
    protected void reset() {
        ID = -1;
        pacmanID = -1;
    }
}

package com.github.exformatgames.pacman.ecs.components.audio;

import com.artemis.PooledComponent;
import com.badlogic.gdx.audio.Sound;

public class SoundComponent extends PooledComponent {

    public Sound sound;

    @Override
    protected void reset() {
        sound = null;
    }
}

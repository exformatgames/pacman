package com.github.exformatgames.pacman.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.github.exformatgames.pacman.GameContext;
import com.github.exformatgames.pacman.ecs.components.audio.SoundComponent;

public class SoundSystem extends IteratingSystem {

    private final GameContext context;

    private ComponentMapper<SoundComponent> soundMapper;


    public SoundSystem(GameContext context) {
        super(Aspect.all(SoundComponent.class));
        this.context = context;
    }

    @Override
    protected void process(int entityId) {
        SoundComponent soundComponent = soundMapper.get(entityId);
        context.getAudioManager().playSound(soundComponent.sound);
        soundMapper.remove(entityId);
    }
}

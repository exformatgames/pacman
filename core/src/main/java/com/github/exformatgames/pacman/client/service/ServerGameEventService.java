package com.github.exformatgames.pacman.client.service;

import com.badlogic.gdx.utils.Array;
import com.github.exformatgames.pacman.data.EntityData;

public class ServerGameEventService {

    private final Array<EntityCreatedListener> createdListeners = new Array<>();
    private final Array<EntityRemovedListener> removedListeners = new Array<>();
    private final Array<EntityTransformedListener> transformedListeners = new Array<>();

    public void onEntityCreated(EntityData data) {
        for (EntityCreatedListener l : createdListeners) {
            l.onEntityCreated(data);
        }
    }

    public void onEntityRemoved(EntityData data) {
        for (EntityRemovedListener l : removedListeners) {
            l.onEntityRemoved(data);
        }
    }

    public void onEntityTransformed(EntityData data) {
        for (EntityTransformedListener l : transformedListeners) {
            l.onEntityTransformed(data);
        }
    }

    public void addEntityCreatedListener(EntityCreatedListener listener) {
        createdListeners.add(listener);
    }

    public void addEntityRemovedListener(EntityRemovedListener listener) {
        removedListeners.add(listener);
    }

    public void addEntityTransformedListener(EntityTransformedListener listener) {
        transformedListeners.add(listener);
    }

    public interface EntityCreatedListener {
        void onEntityCreated(EntityData data);
    }

    public interface EntityRemovedListener {
        void onEntityRemoved(EntityData data);
    }

    public interface EntityTransformedListener {
        void onEntityTransformed(EntityData data);
    }
}

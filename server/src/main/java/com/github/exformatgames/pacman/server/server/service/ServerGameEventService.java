package com.github.exformatgames.pacman.server.server.service;


import com.github.exformatgames.pacman.server.data.EntityData;

import java.util.ArrayList;

public class ServerGameEventService {

    private final ArrayList<EntityCreatedListener> createdListeners = new ArrayList<>();
    private final ArrayList<EntityRemovedListener> removedListeners = new ArrayList<>();
    private final ArrayList<EntityTransformedListener> transformedListeners = new ArrayList<>();

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

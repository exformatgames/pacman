package com.github.exformatgames.pacman.managers;

import com.badlogic.gdx.utils.Array;
import com.github.exformatgames.pacman.data.EntityData;
import com.github.exformatgames.pacman.data.MapData;
import com.github.exformatgames.pacman.managers.net.InputAction;
import com.github.exformatgames.pacman.managers.net.client.NettyClient;
import com.github.exformatgames.pacman.managers.net.client.packet.ExitGamePacket;
import com.github.exformatgames.pacman.managers.net.client.packet.InputPacket;
import com.github.exformatgames.pacman.managers.net.client.packet.PacketRegistry;
import com.github.exformatgames.pacman.managers.net.client.packet.PacketType;
import com.github.exformatgames.pacman.managers.net.client.packet.RequestGameMapPacket;
import com.github.exformatgames.pacman.managers.net.client.packet.StartGamePacket;
import com.github.exformatgames.pacman.managers.net.client.packet.readers.EntityPacketReader;
import com.github.exformatgames.pacman.managers.net.client.packet.readers.MapPacketReader;
import com.github.exformatgames.pacman.managers.net.client.packet.readers.inputPacketReader;
import com.github.exformatgames.pacman.managers.net.client.packet.writers.EntityPacketWriter;
import com.github.exformatgames.pacman.managers.net.client.packet.writers.InputPacketWriter;
import com.github.exformatgames.pacman.managers.net.client.packet.writers.MapPacketWriter;
import com.github.exformatgames.pacman.managers.net.listeners.ConnectionListener;
import com.github.exformatgames.pacman.managers.net.listeners.DisconnectionListener;
import com.github.exformatgames.pacman.managers.net.listeners.EntityCreatedListener;
import com.github.exformatgames.pacman.managers.net.listeners.EntityPositionChangedListener;
import com.github.exformatgames.pacman.managers.net.listeners.EntityRemovedListener;
import com.github.exformatgames.pacman.managers.net.listeners.ResponseMapListener;
import com.badlogic.gdx.Gdx;

public class NetManager {

	private NettyClient client;

	private final String host = "localhost";
	private final int port = 8080;

    private boolean isConnect = false;


    private final Array<ConnectionListener> connectionListeners = new Array<ConnectionListener>();
    private final Array<DisconnectionListener> disconnectionListeners = new Array<DisconnectionListener>();
    private final Array<EntityCreatedListener> entityCreatedListeners = new Array<EntityCreatedListener>();
    private final Array<EntityRemovedListener> entityRemovedListeners = new Array<EntityRemovedListener>();
    private final Array<EntityPositionChangedListener> entityPositionChangedListeners = new Array<EntityPositionChangedListener>();

    private final Array<ResponseMapListener> mapResponseListeners = new Array<ResponseMapListener>();

	private PacketRegistry registry = new PacketRegistry();

	public NetManager () {
		registry.register(PacketType.INPUT, new inputPacketReader(), new InputPacketWriter());
		registry.register(PacketType.RESPONSE_GAME_MAP, new MapPacketReader(), new MapPacketWriter());
		registry.register(PacketType.ENTITY_CREATED, new EntityPacketReader(), new EntityPacketWriter());
		registry.register(PacketType.ENTITY_REMOVED, new EntityPacketReader(), new EntityPacketWriter());
		registry.register(PacketType.ENTITY_POSITION_CHANGED, new EntityPacketReader(), new EntityPacketWriter());
	}


	public void connect () {
		try {
			client = new NettyClient(registry, this);
			client.connect(host, port);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void connected () {
        isConnect = true;
        for (ConnectionListener listener : connectionListeners) {
            listener.connected();
        }
    }
	public void disconnected () {
        isConnect = false;
        for (DisconnectionListener listener : disconnectionListeners) {
            listener.disconnected();
        }
    }

	public void startGame () {
		client.send(new StartGamePacket());
	}

	public void exitGame () {
		client.send(new ExitGamePacket());
	}

	public void sendPressedButton (InputAction action) {
		InputPacket packet = new InputPacket();
		packet.action = action;
		packet.pressed = true;

		client.send(packet);
	}

    public void sendReleaseButton (InputAction action) {
		InputPacket packet = new InputPacket();
		packet.action = action;
		packet.pressed = false;

		client.send(packet);
	}

	public void requestGameMap () {
		client.send(new RequestGameMapPacket());
	}

    public void responseGameMap (final MapData map) {
		Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run () {
					for (ResponseMapListener listener : mapResponseListeners) {
						listener.responseMap(map);
					}
				}
			});
    }



    public void entityPositionChanged (final EntityData data) {
		Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run () {
					for (EntityPositionChangedListener listener : entityPositionChangedListeners) {
						listener.positionChanged(data);
					}
				}
			});
    }
	public void entityCreated (final EntityData data) {
		Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run () {
					for (EntityCreatedListener listener : entityCreatedListeners) {
						listener.createdEntity(data);
					}
				}
			});
    }

	public void entityRemoved (final EntityData data) {
        Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run () {
					for (EntityRemovedListener listener : entityRemovedListeners) {
						listener.removedEntity(data);
					}
				}
			});
    }


    public boolean isConnected () {
        return isConnect;
    }

    public void addConnectionListener (ConnectionListener listener) {
        connectionListeners.add(listener);
    }

    public void addDisconnectionListener (DisconnectionListener listener) {
        disconnectionListeners.add(listener);
    }

    public void addEntityCreatedListener (EntityCreatedListener listener) {
        entityCreatedListeners.add(listener);
    }

    public void addEntityRemovedListener (EntityRemovedListener listener) {
        entityRemovedListeners.add(listener);
    }

    public void addEntityPositionChangedListener (EntityPositionChangedListener listener) {
        entityPositionChangedListeners.add(listener);
    }

    public void addResponseMapListener (ResponseMapListener listener) {
        mapResponseListeners.add(listener);
    }
}

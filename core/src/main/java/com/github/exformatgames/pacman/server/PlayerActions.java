package com.github.exformatgames.pacman.server;

import com.badlogic.gdx.utils.IntMap;
import com.github.exformatgames.pacman.api.Token;
import com.github.exformatgames.pacman.data.PlayerData;
import java.util.HashMap;
import java.util.Map;

public class PlayerActions {

	private final IntMap<PlayerData> playerMap;
	private final IntMap<PlayerData> onlinePlayerMap;
	private final IntMap<PlayerData> offlinePlayerMap;

	//тут сделаем вид что это полноценная бд
	//key = username Hash, value = playerID.
	private final Map<String, Integer> userMap = new HashMap<String, Integer>();
	//key = username Hash, value = password Hash.
	private final Map<String, String> passwordMap = new HashMap<String, String>();

	private int lastPlayerID = 0;

	public PlayerActions (IntMap<PlayerData> playerMap, IntMap<PlayerData> onlinePlayerMap, IntMap<PlayerData> offlinePlayerMap) {
		this.playerMap = playerMap;
		this.onlinePlayerMap = onlinePlayerMap;
		this.offlinePlayerMap = offlinePlayerMap;
	}


	public boolean registration(String usernameHash, String passwordHash) {
		//это все типа есть)))
		//расшифровываем. 
		//валидация на длину знаки и тп
		//зашифровываем
		//сравниваем пришедшее и получившееся
		//сохраняем ключ пользлвателя.

		if (! passwordMap.containsKey(usernameHash)) {
			lastPlayerID += 1;

			passwordMap.put(usernameHash, passwordHash);
			userMap.put(usernameHash, lastPlayerID);
			return true;
		}

		return false;
	}

	public Token auth(String usernameHash, String passwordHash) {
		if (passwordMap.containsKey(usernameHash)) {
			if (passwordMap.get(usernameHash) == passwordHash) {
				return new Token();
			}
		}
		
		return null;
	}

	public void exit() {}
}

package com.github.exformatgames.pacman.server.server.service;

import com.github.exformatgames.pacman.server.data.InputData;

public interface ButtonEventService {
	void onButtonPressed(InputData inputData);
	void onButtonReleased(InputData inputData);
}

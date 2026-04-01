package com.github.exformatgames.pacman.client.service;

import com.github.exformatgames.pacman.data.InputData;

public interface ButtonEventService {
	void onButtonPressed(InputData inputData);
	void onButtonReleased(InputData inputData);
}

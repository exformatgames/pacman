package com.github.exformatgames.pacman.net.service;

import data.InputData;

public interface ButtonEventService {
	void onButtonPressed(InputData inputData);
	void onButtonReleased(InputData inputData);
}

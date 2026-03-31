package com.github.exformatgames.pacman.Assets;

import java.util.Arrays;

public class AnimationData {
	public String name;
	public float frameDuration;
	public String playMode;
	public int frameCount;
	public int frameWidth;
	public int frameHeight;
	public String[] frames;

    @Override
    public String toString() {
        return "AnimationData{" +
            "name='" + name + '\'' +
            ", frameDuration=" + frameDuration +
            ", playMode='" + playMode + '\'' +
            ", frameCount=" + frameCount +
            ", frameWidth=" + frameWidth +
            ", frameHeight=" + frameHeight +
            ", frames=" + Arrays.toString(frames) +
            '}';
    }
}

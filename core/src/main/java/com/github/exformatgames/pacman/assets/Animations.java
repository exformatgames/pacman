package com.github.exformatgames.pacman.assets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;
import java.util.Map;

public class Animations {

	private final Map<String, Animation<TextureRegion>> animationMap = new HashMap<>();

	private final Graphics graphics;

	public Animations (Graphics graphics) {
		this.graphics = graphics;
	}


	public Animation<TextureRegion> get(String name) {
		return animationMap.get(name);
	}

	public void addAnimation (Animation<TextureRegion> animation, String name) {
		animationMap.put(name, animation);
	}

	public Animation<TextureRegion> build (AnimationData data) {
		Array<TextureRegion> frameList = new Array<>();

		for (String frameName : data.frames) {
			TextureRegion frame = graphics.getTextureRegion(frameName);
			frameList.add(frame);
		}


		switch (data.playMode) {
			case "NORMAL" :
                return new Animation<>(data.frameDuration, frameList, Animation.PlayMode.NORMAL);
			case "REVERSED" :
                return new Animation<>(data.frameDuration, frameList, Animation.PlayMode.REVERSED);
			case "LOOP" :
                return new Animation<>(data.frameDuration, frameList, Animation.PlayMode.LOOP);
			case "LOOP_REVERSED" :
                return new Animation<>(data.frameDuration, frameList, Animation.PlayMode.LOOP_REVERSED);
			case "LOOP_PINGPONG" :
                return new Animation<>(data.frameDuration, frameList, Animation.PlayMode.LOOP_PINGPONG);
			case "LOOP_RANDOM" :
                return new Animation<>(data.frameDuration, frameList, Animation.PlayMode.LOOP_RANDOM);
		}

        return new Animation<>(data.frameDuration, frameList, Animation.PlayMode.NORMAL);
	}
}

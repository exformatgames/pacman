package com.github.exformatgames.pacman.Assets;

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


	public Animation<TextureRegion> getAnimation (String name) {
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

		Animation.PlayMode mode = Animation.PlayMode.NORMAL;

		switch (data.playMode) {
			case "NORMAL" : {
					mode = Animation.PlayMode.NORMAL;
					break;
				}
			case "REVERSED" : {
					mode = Animation.PlayMode.REVERSED;
					break;
				}
			case "LOOP" : {
					mode = Animation.PlayMode.LOOP;
					break;
				}
			case "LOOP_REVERSED" : {
					mode = Animation.PlayMode.LOOP_REVERSED;
					break;
				}
			case "LOOP_PINGPONG" : {
					mode = Animation.PlayMode.LOOP_PINGPONG;
					break;
				}
			case "LOOP_RANDOM" : {
					mode = Animation.PlayMode.LOOP_RANDOM;
					break;
				}
		}

		Animation<TextureRegion> animation = new Animation<TextureRegion>(data.frameDuration, frameList, mode);

		return animation;
	}
}

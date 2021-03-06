package com.deanc.ninjarun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.deanc.ninjarun.Screens.MenuScreen;
import com.deanc.ninjarun.Screens.PlayScreen;

public class NinjaRun extends Game {
	//constants
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	//Filter initializations
	public static final short GROUND_BIT = 1;
	public static final short RYU_BIT = 2;
	public static final short PLATFORM_BIT = 4;
	public static final short FINISH_BIT = 8;
	public static final short BARRIER_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short ITEM_BIT = 64;
	public static final short RYU_HEAD_BIT = 128;
	public static final short ATTACK_BIT = 256;
	public static final short MONEY_BIT = 512;


	//variables
	public SpriteBatch batch;

	/* WARING Using AssetManager in a static way can cause issues, especially on Android.
	Instead you may want to pass around AssetManager to those classes that need it.
	 We will use it in the static context to save time for now */

	public static AssetManager manager;


	@Override
	public void create () {
		batch = new SpriteBatch();

		//Audio Loading
		manager = new AssetManager();
		manager.load("audio/music/yoitrax-warrior.mp3", Music.class);
		manager.load("audio/music/yoitrax-ronin.mp3", Music.class);
		manager.load("audio/music/mixkit-piano-horror-671.mp3", Music.class);
		manager.load("audio/sounds/Mission Accomplished Fanfare 1.mp3", Sound.class);
		manager.load("audio/sounds/coin.mp3", Sound.class);
		manager.load("audio/sounds/fireworks.mp3", Sound.class);
		manager.load("audio/sounds/block.mp3", Sound.class);
		manager.load("audio/sounds/powerup_spawn.wav", Sound.class);
		manager.load("audio/sounds/stomp.wav", Sound.class);
		manager.load("audio/sounds/mariodie.wav",Sound.class);
		manager.load("audio/sounds/sexynakedbunny-ouch.mp3",Sound.class);
		manager.load("audio/sounds/soundnimja-jump.wav",Sound.class);
		manager.load("audio/sounds/getting-hit.wav",Sound.class);
		manager.load("audio/sounds/mixkit-fast-sword-whoosh-2792.wav",Sound.class);
		manager.finishLoading();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}

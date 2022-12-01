package com.deanc.ninjarun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deanc.ninjarun.Scenes.Hud;
import com.deanc.ninjarun.Screens.LogoScreen;

public class NinjaRun extends Game {
	//constants
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 150;
	public static final float MAX_VOL = 100;
	public static final float MIN_VOL = 0;

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
	public static final short SKY_BIT = 1024;


	//variables
	public SpriteBatch batch;
	public float volume = 0.5f;
	public float soundVolume = 0.5f;
	public boolean mutedM = false;
	public boolean mutedS = false;
	public Music music;
	public Sound sound;
	private int coins = 0;
	private Hud hud;
	public int jumpCounter = 0;
	public int justTouched = 0;
	public boolean doubleJumped = false;
	public float statetimer = 0;

	public Hud getHud() {
		return hud;
	}

	public void setHud(Hud hud) {
		this.hud = hud;
	}

	/* WARING Using AssetManager in a static way can cause issues, especially on Android.
	Instead you may want to pass around AssetManager to those classes that need it.
	 We will use it in the static context to save time for now */

	public static AssetManager manager;


	@Override
	public void create() {
		batch = new SpriteBatch();

		//Audio Loading
		manager = new AssetManager();
		manager.load("audio/sounds/coin.mp3", Sound.class);
		manager.load("audio/sounds/getting-hit.wav", Sound.class);
		manager.load("audio/sounds/healthDrink.wav", Sound.class);
		manager.load("audio/sounds/Mission Accomplished Fanfare 1.mp3", Sound.class);
		manager.load("audio/sounds/mixkit-fast-sword-whoosh-2792.wav", Sound.class);
		manager.load("audio/sounds/mixkit-gear-metallic-lock-sound-2858.wav", Sound.class);
		manager.load("audio/sounds/sexynakedbunny-ouch.mp3", Sound.class);
		manager.load("audio/sounds/soundnimja-jump.wav", Sound.class);
		manager.load("audio/music/yoitrax - Fuji.mp3", Music.class);
		manager.load("audio/music/yoitrax - Jade Dragon.mp3", Music.class);
		manager.load("red_moon_shinobi_by_cobaltplasma_davlugw.png", Texture.class);
		manager.load("background.png", Texture.class);
		manager.load("samurai.png", Texture.class);
		manager.load("wave-scaled.jpg", Texture.class);
		manager.finishLoading();
		setScreen(new LogoScreen(this));
	}

	public static AssetManager getManager() {
		return manager;
	}

	public void loadMusic(String path) {
		music = Gdx.audio.newMusic(Gdx.files.internal(path));
	}

	public void loadSound(String path) {
		sound = Gdx.audio.newSound(Gdx.files.internal(path));
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public float getSoundVolume() {
		return soundVolume;
	}

	public void setSoundVolume(float soundVolume) {
		this.soundVolume = soundVolume;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void setStatetimer(float statetimer) {
		this.statetimer = statetimer;
	}

	public void setMutedM(boolean muted){
		mutedM = muted;
	}

	public void setMutedS(boolean mute){
		mutedS = mute;
	}

}

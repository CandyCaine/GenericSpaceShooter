package me.rich.gss.sound;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundManager {

	private static float GLOBAL_MUSIC_VOLUME = 1.0F;
	private static float GLOBAL_SFX_VOLUME = 1.0F;
	
	private static Sound death;
	private static Sound shoot;
	
	public static Music rocket;
	public static Music beautyfi;
	
	private static boolean loaded = false;
	
	public static Music currentMusic;
	
	public static void init() {
		try {
			death = new Sound("resource/asset/sound/effects/death.ogg");
			shoot = new Sound("resource/asset/sound/effects/so.ogg");
			rocket = new Music("resource/asset/sound/music/Rocket.ogg");
			beautyfi = new Music("resource/asset/sound/music/beautyfi.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		loaded = true;
	}
	
	public static boolean isLoaded() {
		return loaded;
	}
	
	public static void loopMusic(Music music) {
		music.loop(1.0F, GLOBAL_MUSIC_VOLUME);
		currentMusic = music;
	}
	
	public static Music getCurrentMusic() {
		return currentMusic;
	}
	
	public static void playDeath() {
		death.play(1.0F, GLOBAL_SFX_VOLUME);
	}
	
	public static void playShot(float power) {
		shoot.play(power, GLOBAL_SFX_VOLUME);
	}
	
	public static float getGlobalMusicVolume() {
		return GLOBAL_MUSIC_VOLUME;
	}
	
	public static void setGlobalMusicVolume(float volume) {
		GLOBAL_MUSIC_VOLUME = volume;
	}
	
	public static float getGlobalSoundEffectsVolume() {
		return GLOBAL_SFX_VOLUME;
	}
	
	public static void setGlobalSoundEffectVolume(float volume) {
		GLOBAL_SFX_VOLUME = volume;
	}
}

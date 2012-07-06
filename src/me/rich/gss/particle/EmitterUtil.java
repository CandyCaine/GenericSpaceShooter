package me.rich.gss.particle;

import java.util.HashMap;
import java.util.Map;
public class EmitterUtil {

	public static enum Emitter {
		PlayerBullet("resource/asset/emitter/projectileEmitter/playerBulletEmitter.xml"), BasicEnemyBullet("resource/asset/emitter/projectileEmitter/basicEnemyBulletEmitter.xml");

		private String path;

		Emitter(String path) {
			this.path = path;
		}

		public String getFilePath() {
			return path;
		}
	}
	
	private static Map<String, EmitterInfo> emitters = new HashMap<String, EmitterInfo>();
	
	public static void init() {
		String projectileFolder = "resource/asset/emitter/projectileEmitter/";
		
		String genericFolder = "resource/asset/emitter/genericEmitter/";

		emitters.put("fall", new EmitterInfo("fall", projectileFolder + "falloutEmitter.xml", projectileFolder + "playerBulletParticle.png"));
		emitters.put("explode", new EmitterInfo("explode", projectileFolder + "fireSpark2.xml", projectileFolder + "spark.png"));
		emitters.put("playerThrust", new EmitterInfo("playerThrust", genericFolder + "playerThruster.xml", genericFolder + "thrustParticle.png"));
		emitters.put("playerBullet", new EmitterInfo("playerBullet", projectileFolder + "playerBulletEmitter.xml", projectileFolder + "playerBulletParticle.png"));
		emitters.put("basicEnemyBullet", new EmitterInfo("basicEnemyBullet", projectileFolder + "basicEnemyBulletEmitter.xml", projectileFolder + "basicEnemyParticle.png"));
	}
	
	public static EmitterInfo getEmitterInfo(String name) {
		if (emitters.containsKey(name)) {
			return emitters.get(name);
		} else {
			return null;
		}
	}
}

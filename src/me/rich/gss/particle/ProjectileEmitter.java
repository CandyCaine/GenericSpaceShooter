package me.rich.gss.particle;

import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ConfigurableEmitterFactory;

public class ProjectileEmitter extends ConfigurableEmitter implements ConfigurableEmitterFactory {

	public ProjectileEmitter(String name) {
		super(name);
	}

	@Override
	public ConfigurableEmitter createEmitter(String name) {
		return new ProjectileEmitter(name);
	}

}

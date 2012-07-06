package me.rich.gss.entity.projectile;

import java.io.IOException;

import me.rich.gss.particle.EmitterUtil;

import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class Bullet extends Projectile {

	public float moveSpeed;

	public ParticleSystem system;
	public ConfigurableEmitter emitter;

	public Bullet(float x, float y, boolean playerFired) {
		super(x, y, playerFired);
		init();
	}

	public void init() {
		moveSpeed = 0.5F;
		addParticleSystem();
	}

	@Override
	public void addParticleSystem() {
		try {
			system = new ParticleSystem(EmitterUtil.getEmitterInfo("playerBullet").getImage());
			emitter = ParticleIO.loadEmitter(EmitterUtil.getEmitterInfo("playerBullet").getXML());
		} catch (IOException e) {
			e.printStackTrace();
		}
		emitter.setPosition(0, 0);
		system.addEmitter(emitter);
		system.setPosition(x, y);
	}

	@Override
	public void update(int delta) {
		if (!(system == null)) {
			system.update(delta);
		}

		y -= (moveSpeed * delta);
		system.setPosition(x, y);
		if (y < -400) {
			alive = false;
		}
	}

	@Override
	public void render() {
		system.render();
	}
}

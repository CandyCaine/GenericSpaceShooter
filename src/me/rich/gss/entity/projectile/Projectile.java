package me.rich.gss.entity.projectile;

import me.rich.gss.entity.Entity;

public abstract class Projectile extends Entity {

	public boolean playerFired;
	public boolean alive;
	
	public Projectile(float x, float y, boolean playerFired) {
		super(x, y);
		alive = true;
		this.playerFired = playerFired;
	}
	
	public abstract void addParticleSystem();
	
	public abstract void update(int delta);

	public abstract void render();

	public boolean isPlayerFired() {
		return playerFired;
	}
	
	public boolean isAlive() {
		return alive;
	}
}

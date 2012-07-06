package me.rich.gss.entity;

public abstract class Entity {

	public float x;
	public float y;
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public abstract void update(int delta);
	
	public abstract void render();
}

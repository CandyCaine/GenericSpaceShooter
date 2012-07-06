package me.rich.gss.particle;

import me.rich.gss.Game;

import org.newdawn.slick.Graphics;

public class Star {

	private float x;
	private float y;
	private float speed;
	
	private boolean alive;
	
	public Star(float x, float y, float speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		alive = true;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public boolean isAlive() {
		return alive;
	}

	public void update(int delta) {
		y += (speed * delta);
		if (y > Game.height + 100) {
			alive = false;
		}
	}
	
	public void render(Graphics g) {
		g.fillRect(x, y, 1, 5);
	}
}

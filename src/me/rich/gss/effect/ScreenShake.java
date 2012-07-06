package me.rich.gss.effect;

import org.newdawn.slick.Graphics;

public class ScreenShake {
	
	private boolean active = false;
	
	private float x;
	private float currentX;
	private float y;
	private float currentY;
	private long startTime;
	
	public ScreenShake(float x, float y) {
		this.x = x;
		this.y = y;
		startTime = System.currentTimeMillis();
	}
	
	public void update(int delta) {
		if (currentX < x) {
			x += 5 * delta;
		} else if (currentX > x) {
			currentX -= 5 * delta;
		}
	}
	
	public void render(Graphics g) {
		g.translate(currentX, 0.0f);
	}
	
	public void end(Graphics g) {
		g.translate(0, 0);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public long getStartTime() {
		return startTime;
	}
}

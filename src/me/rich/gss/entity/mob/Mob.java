package me.rich.gss.entity.mob;

import me.rich.gss.Game;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Mob {

	public float x;
	public float y;
	public float speed;

	public boolean alive;
	public int health;

	public Image image;

	public long lastDamage;
	public long interval = 80;
	
	public boolean canFire;
	public int fireChance;
	
	public int money;

	public Mob(float x, float y, boolean canFire) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Image getImage() {
		return image;
	}

	public boolean isAlive() {
		return alive;
	}

	public void takeDamage(long time, int damage) {
		if (time > lastDamage + interval) {
			lastDamage = time;
			health -= damage;
			if (health <= 0) {
				alive = false;
			}
		}
	}

	public void update(int delta) {
		y += (speed * delta);
		if (y > Game.height + 200) {
			alive = false;
		}
	}

	public void render(Graphics g) {
		g.drawImage(image, x, y);
	}
	
	public int getFireChance() {
		return fireChance;
	}
	
	public void fire() {
		
	}
	
	public int getMoney() {
		return money;
	}
}

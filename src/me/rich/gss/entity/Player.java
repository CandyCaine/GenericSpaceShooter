package me.rich.gss.entity;

import java.io.IOException;

import me.rich.gss.Game;
import me.rich.gss.graphic.Art;
import me.rich.gss.particle.EmitterUtil;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class Player extends Entity {

	private Image image;
	private float xOffset, yOffset;
	private ParticleSystem system;
	private ConfigurableEmitter emitterLeft, emitterRight;
	
	private int damage;
	private int bullets;
	private int health;
	private int money;
	private boolean alive;
	
	private float width;
	private float height;
	
	public Player(float x, float y) {
		super(x, y);
		image = Art.getImage("player64");
		xOffset = (image.getWidth() / 2);
		yOffset = (image.getHeight() / 2);
		initParticles();
		damage = 1;
		bullets = 1;
		health = 3;
		alive = true;
		width = 48;
		height = 48;
	}
	
	private void initParticles() {
		system = new ParticleSystem(EmitterUtil.getEmitterInfo("playerThrust").getImage());
		try {
			emitterLeft = ParticleIO.loadEmitter(EmitterUtil.getEmitterInfo("playerThrust").getXML());
			emitterRight = ParticleIO.loadEmitter(EmitterUtil.getEmitterInfo("playerThrust").getXML());
		} catch (IOException e) {
			e.printStackTrace();
		}
		emitterLeft.setPosition(-26, 16);
		emitterRight.setPosition(26, 16);
		system.addEmitter(emitterLeft);
		system.addEmitter(emitterRight);
		system.setPosition(x, y);
	}

	@Override
	public void update(int delta) {
		x = Mouse.getX();
		y = Game.height - Mouse.getY();
		system.update(delta);
		system.setPosition(x, y);
	}

	@Override
	public void render() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(image, x - xOffset, y - yOffset);
		system.render();
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getBullets() {
		return bullets;
	}
	
	public void setBullets(int bullets) {
		this.bullets = bullets;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void addHealth(int amount) {
		this.health += amount;
	}
	
	public void takeDamage() {
		health--;
		if (health <= 0) {
			alive = false;
		}
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void addMoney(int money) {
		this.money += money;
	}
	
	public void takeMoney(int money) {
		this.money -= money;
	}
}

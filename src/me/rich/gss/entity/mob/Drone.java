package me.rich.gss.entity.mob;

import me.rich.gss.graphic.Art;

public class Drone extends Mob {

	public Drone(float x, float y, boolean canFire) {
		super(x, y, canFire);
		image = Art.getImage("drone");
		alive = true;
		speed = 0.1F;
		health = 1;
		fireChance = 5;
		money = 10;
	}

}

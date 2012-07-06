package me.rich.gss.entity.mob;

import me.rich.gss.graphic.Art;

public class Fighter extends Mob {

	public Fighter(float x, float y, boolean canFire) {
		super(x, y, canFire);
		image = Art.getImage("fighter");
		alive = true;
		speed = 0.15F;
		health = 2;
		fireChance = 8;
		money = 20;
	}
}

package me.rich.gss.stage;

import me.rich.gss.entity.mob.MobType;

public class SpawnQueue {

	private int id;
	private MobType mob;
	private int amount;
	
	public SpawnQueue(int id, MobType mob, int amount) {
		setId(id);
		setMobType(mob);
		setAmount(amount);
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public MobType getMobType() {
		return mob;
	}

	private void setMobType(MobType mob) {
		this.mob = mob;
	}

	public int getAmount() {
		return amount;
	}

	private void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}

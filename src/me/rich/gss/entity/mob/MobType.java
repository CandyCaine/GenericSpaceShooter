package me.rich.gss.entity.mob;

public enum MobType {

	Drone	("drone"),
	Fighter	("fighter");
	
	private String name;
	
	MobType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static MobType determineType(String type) {
		MobType[] all = MobType.values();
		for (int i = 0; i < all.length; i++) {
			if (all[i].getName().equals(type.toString())) {
				return all[i];
			}
		}
		
		return null;
	}
}

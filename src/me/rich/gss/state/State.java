package me.rich.gss.state;

public enum State {

	MAIN_MENU_STATE		(0, "MainMenu"),
	INFO_STATE			(1, "Info"),
	SETTINGS_STATE		(2, "Settings"),
	GAME_STATE			(3, "Game"),
	UPGRADE_STATE		(4, "Upgrade");
	
	private int stateID;
	private String name;
	
	State(int stateID, String name) {
		this.stateID = stateID;
		this.name = name;
	}
	
	public int getStateID() {
		return stateID;
	}
	
	public String getName() {
		return name;
	}
}

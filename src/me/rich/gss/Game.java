package me.rich.gss;

import me.rich.gss.graphic.Art;
import me.rich.gss.particle.EmitterUtil;
import me.rich.gss.sound.SoundManager;
import me.rich.gss.state.GameState;
import me.rich.gss.state.InfoState;
import me.rich.gss.state.MainMenuState;
import me.rich.gss.state.SettingsState;
import me.rich.gss.state.State;
import me.rich.gss.state.UpgradeState;

import org.lwjgl.openal.AL;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
	
	public static final int width = 1280;
	public static final int height = 1024;
	public static final String title = "GenericSpaceShooter";
	
	private static AppGameContainer app;
	private static Game game;
	
	public Game(String name) {
		super(name);
		createGameStates();
		game = this;
	}

	public static void main(String[] args) throws SlickException {
		app = new AppGameContainer(new Game(title));
		app.setDisplayMode(width, height, true);
		app.setTargetFrameRate(100);
		app.setMaximumLogicUpdateInterval(50);
		//app.setShowFPS(false);
		if (app.supportsMultiSample()) {
			app.setMultiSample(2);
		}
		app.setVSync(true);
		app.start();
		
	}
	
	
	private void createGameStates() {
		this.addState(new MainMenuState(State.MAIN_MENU_STATE.getStateID()));
		this.addState(new InfoState(State.INFO_STATE.getStateID()));
		this.addState(new SettingsState(State.SETTINGS_STATE.getStateID()));
		this.addState(new GameState(State.GAME_STATE.getStateID()));
		this.addState(new UpgradeState(State.UPGRADE_STATE.getStateID()));
		
		this.enterState(State.MAIN_MENU_STATE.getStateID());

	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		EmitterUtil.init();
		Art.init();
		SoundManager.init();
	}
	
	public static void exitGame() {
		AL.destroy();
		app.destroy();
	}
	
	public static BasicGameState getState(State state) {
		if (state == State.GAME_STATE) {
			return (GameState) game.getState(State.GAME_STATE.getStateID());
		} else if (state == State.UPGRADE_STATE) {
			return (UpgradeState) game.getState(State.UPGRADE_STATE.getStateID());
		}
		
		return null;
	}
	
	public static void changeState(State state) {
		if (state == State.GAME_STATE) {
			game.enterState(State.GAME_STATE.getStateID());
		} else if (state == State.UPGRADE_STATE) {
			game.enterState(State.UPGRADE_STATE.getStateID());
		}
	}

}

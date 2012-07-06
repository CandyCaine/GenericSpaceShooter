package me.rich.gss.state;

import me.rich.gss.Game;
import me.rich.gss.graphic.Art;
import me.rich.gss.graphic.MenuButton;
import me.rich.gss.sound.SoundManager;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class UpgradeState extends BasicGameState {
	private int stateID;

	private boolean playing = false;

	private int gunPower;
	private int bullets;
	private int health;
	private int special;

	private String gunPowerText = "Increase the damage of your weapon";
	private String bulletsText = "Increase the amount of bullets you can fire";
	private String healthText = "Increase your current health";
	private String specialText = "";

	private Font largeFont;
	private Font smallFont;
	private MenuButton resume;

	public UpgradeState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		largeFont = Art.getFont(72);
		smallFont = Art.getFont(24);
		resume = new MenuButton("resume", 500, Game.height - 150, "button", "Continue");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (!playing) {

			SoundManager.loopMusic(new Music("resource/asset/sound/music/menu.ogg"));
			playing = true;
		}

		if (Mouse.isGrabbed()) {
			Mouse.setGrabbed(false);
		}

		if (Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if (resume.clicked()) {
				resumeGame(sbg);
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("CurrentState: " + State.UPGRADE_STATE.getName(), 20, Game.height - 20);

		resume.render(g);

		g.setFont(largeFont);
		g.drawString("Upgrade", 200, 100);
		g.setFont(smallFont);
		g.drawString("no upgrades currently available", 400, 500);
		g.drawString("Current Money: " + GameState.getPlayer().getMoney(), 400, 550);

		g.drawImage(Art.getImage("powerIcon"), 100, 400);
	}

	@Override
	public int getID() {
		return stateID;
	}

	public void resumeGame(StateBasedGame sbg) {
		GameState gs = (GameState) Game.getState(State.GAME_STATE);
		gs.next();
		Mouse.setGrabbed(true);
		sbg.enterState(State.GAME_STATE.getStateID(), new FadeOutTransition(Color.black), new FadeInTransition(Color.black, 1000));
	}
}

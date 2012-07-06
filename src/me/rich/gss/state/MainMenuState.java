package me.rich.gss.state;

import java.util.ArrayList;

import me.rich.gss.Game;
import me.rich.gss.graphic.Art;
import me.rich.gss.graphic.MenuButton;
import me.rich.gss.sound.SoundManager;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainMenuState extends BasicGameState {
	private int stateID;	
	
	private ArrayList<MenuButton> buttons;
	private Font largeFont;
	private boolean play = false;
	
	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		largeFont = Art.getFont(72);
		buttons = new ArrayList<MenuButton>();
		buttons.add(new MenuButton("start", (float)(Game.width - (Art.getImage("button").getWidth()))/ 2, 400F, "button", "Start Game"));
		buttons.add(new MenuButton("info", (float)(Game.width - (Art.getImage("button").getWidth()))/ 2, 500F, "button", "Instructions"));
		buttons.add(new MenuButton("settings", (float)(Game.width - (Art.getImage("button").getWidth()))/ 2, 600F, "button", "Settings"));
		buttons.add(new MenuButton("quit", (float)(Game.width - (Art.getImage("button").getWidth()))/ 2, 700F, "button", "Quit Game"));
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (!play) {
			SoundManager.loopMusic(SoundManager.rocket);
			play = true;
		}
		if (Mouse.isGrabbed()) {
			Mouse.setGrabbed(false);
		}
		
		if (Mouse.isButtonDown(0)) {
			for (int i = 0; i < buttons.size(); i++) {
				if (buttons.get(i).clicked()) {
					if (buttons.get(i).getName().equals("start")) {
						Mouse.setCursorPosition(Game.width / 2, Game.height - 200);
						Mouse.setGrabbed(true);
						sbg.enterState(State.GAME_STATE.getStateID(), new FadeOutTransition(Color.black), new FadeInTransition(Color.black, 1000));
					} else if (buttons.get(i).getName().equals("info")) {
						sbg.enterState(State.INFO_STATE.getStateID());
					} else if (buttons.get(i).getName().equals("settings")) {
						sbg.enterState(State.SETTINGS_STATE.getStateID());
					} else if (buttons.get(i).getName().equals("quit")) {
						gc.exit();
					}
				}
			}
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("CurrentState: " + State.MAIN_MENU_STATE.getName(), 20, Game.height - 20);
		
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).render(g);
		}
		
		g.setFont(largeFont);
		g.drawString("Generic", 500, 100);
		g.drawString("SpaceShooter", 380, 180);
	}

	@Override
	public int getID() {
		return stateID;
	}
}

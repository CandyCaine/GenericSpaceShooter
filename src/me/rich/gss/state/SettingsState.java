package me.rich.gss.state;

import java.util.ArrayList;

import me.rich.gss.Game;
import me.rich.gss.graphic.MenuButton;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class SettingsState extends BasicGameState {

	private int stateID;

	private ArrayList<MenuButton> buttons;

	public SettingsState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		buttons = new ArrayList<MenuButton>();
		buttons.add(new MenuButton("back", 100F, Game.height - 100, "backButton", "Back"));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

		if (Mouse.isButtonDown(0)) {
			for (int i = 0; i < buttons.size(); i++) {
				if (buttons.get(i).clicked()) {
					if (buttons.get(i).getName().equals("back")) {
						sbg.enterState(State.MAIN_MENU_STATE.getStateID(), new FadeOutTransition(Color.black), new FadeInTransition(Color.black, 1000));
					}
				}
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).render(g);
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}

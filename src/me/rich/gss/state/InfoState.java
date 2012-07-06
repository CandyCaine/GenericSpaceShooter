package me.rich.gss.state;

import me.rich.gss.Game;
import me.rich.gss.graphic.Art;
import me.rich.gss.graphic.MenuButton;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class InfoState extends BasicGameState {
	private int stateID;	
	
	private String[] text = {
	
		"Instructions",
		"Destroy the aliens and collect coins.",
		"Controls :",
		"- Move around using the mouse",
		"- Press the left mouse button to fire"	
	};
	
	private MenuButton backButton;
	
	private Font largeFont, smallFont;
	
	public InfoState(int stateID) {
		this.stateID = stateID;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		largeFont = Art.getFont(72);
		smallFont = Art.getFont(36);
		backButton = new MenuButton("back", 50, Game.height - 100, "backButton", "Back");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (Mouse.isGrabbed()) {
			Mouse.setGrabbed(false);
		}
		
		if (Mouse.isButtonDown(0)) {
			if (backButton.clicked()) {
				sbg.enterState(State.MAIN_MENU_STATE.getStateID());
			}
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawString("CurrentState: " + State.INFO_STATE.getName(), 20, Game.height - 20);
		
		backButton.render(g);
		
		g.setFont(largeFont);
		g.drawString(text[0], 350, 100);
		
		g.setFont(smallFont);
		g.drawString(text[1], 250, 300);
		g.drawString(text[2], 250, 450);
		g.drawString(text[3], 250, 500);
		g.drawString(text[4], 250, 550);
		

	}

	@Override
	public int getID() {
		return stateID;
	}
}

package me.rich.gss.graphic;

import me.rich.gss.Game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class MenuButton {

	private float x;
	private float y;
	private float width;
	private float height;

	private String name;
	private Image image;
	private String text;

	public MenuButton(String name,float x, float y, String imageName, String text) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.image = Art.getImage(imageName);
		this.text = text;

		this.width = image.getWidth();
		this.height = image.getHeight();
	}

	public String getName() {
		return name;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public Image getImage() {
		return image;
	}

	public boolean clicked() {
		float mouseX = Mouse.getX();
		float mouseY = Game.height - Mouse.getY();

		if (mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height) {
			return true;
		}

		return false;
	}
	
	public void render(Graphics g) {
		g.drawImage(image, x, y);
		g.setColor(Color.white);
		g.drawString(text, x + image.getWidth() / 3, y + image.getHeight() / 3);
	}
}

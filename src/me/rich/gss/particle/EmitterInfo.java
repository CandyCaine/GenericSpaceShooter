package me.rich.gss.particle;

import java.io.File;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EmitterInfo {

	private String name;
	private File xml;
	private Image image;
	
	public EmitterInfo(String name, String xmlPath, String imagePath) {
		this.name = name;
		xml = new File(xmlPath);
		try {
			image = new Image(imagePath);
		} catch (SlickException e) {
			System.err.println("An error occurred during image I/O");
		} catch (RuntimeException e) {
		    System.err.println("The file was not found");
		}
	}
	
	public String getName() {
		return name;
	}
	
	public File getXML() {
		return xml;
	}
	
	public Image getImage() {
		return image;
	}
	
}

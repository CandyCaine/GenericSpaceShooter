package me.rich.gss.graphic;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class Art {

	private static Map<String, Image> imageMap = new HashMap<String, Image>();
	private static Image[] goldCoinFrames;
	private static Image[] silverCoinFrames;
	private static Image[] bronzeCoinFrames;
	
	public static void init() throws SlickException {
		imageMap.put("player", new Image("resource/asset/image/player.png"));
		imageMap.put("player128", new Image("resource/asset/image/player128.png"));
		imageMap.put("player64", new Image("resource/asset/image/player64.png"));
		
		imageMap.put("button", new Image("resource/asset/image/button.png"));
		imageMap.put("backButton", new Image("resource/asset/image/backButton.png"));
		
		imageMap.put("drone", new Image("resource/asset/image/drone.png"));
		imageMap.put("fighter", new Image("resource/asset/image/fighter.png"));
		
		
		goldCoinFrames = new Image[4];
		goldCoinFrames[0] = new Image("resource/asset/image/coinAnim/gold1.png");
		goldCoinFrames[1] = new Image("resource/asset/image/coinAnim/gold2.png");
		goldCoinFrames[2] = new Image("resource/asset/image/coinAnim/gold3.png");
		goldCoinFrames[3] = new Image("resource/asset/image/coinAnim/gold4.png");
		
		silverCoinFrames = new Image[4];
		silverCoinFrames[0] = new Image("resource/asset/image/coinAnim/silver1.png");
		silverCoinFrames[1] = new Image("resource/asset/image/coinAnim/silver2.png");
		silverCoinFrames[2] = new Image("resource/asset/image/coinAnim/silver3.png");
		silverCoinFrames[3] = new Image("resource/asset/image/coinAnim/silver4.png");
		
		bronzeCoinFrames = new Image[4];
		bronzeCoinFrames[0] = new Image("resource/asset/image/coinAnim/bronze1.png");
		bronzeCoinFrames[1] = new Image("resource/asset/image/coinAnim/bronze2.png");
		bronzeCoinFrames[2] = new Image("resource/asset/image/coinAnim/bronze3.png");
		bronzeCoinFrames[3] = new Image("resource/asset/image/coinAnim/bronze4.png");
		
		imageMap.put("powerIcon", new Image("resource/asset/image/gui/powerIcon2.png"));
	}
	
	public static Image getImage(String name) {
		if (imageMap.containsKey(name)) {
			return imageMap.get(name);
		} else {
			return null;
		}
	}
	
	public static Font getFont(int size) throws SlickException {
		String fontPath;
		UnicodeFont uFont;
		fontPath = "resource/asset/image/ABDUCTIO.TTF";
		uFont = new UnicodeFont(fontPath , size, false, false);
		uFont.addAsciiGlyphs();
		uFont.addGlyphs(400, 600);
		uFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		uFont.loadGlyphs();
		
		return uFont;
	}
	
	public static Image[] getCoinFrames(String type) {
		if (type.equals("gold")) {
			return goldCoinFrames;
		} else if (type.equals("silver")) {
			return silverCoinFrames;
		} else if (type.equals("bronze")) {
			return bronzeCoinFrames;
		}
		
		return null;
	}
}

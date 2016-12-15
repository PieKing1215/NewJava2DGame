package me.pieking.game.graphics;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import me.pieking.game.Game;
import me.pieking.game.Logger;
import me.pieking.game.Utils;

public class Sprite {

	public static List<Sprite> loadedSprites = new ArrayList<Sprite>();
	
	public String path;
	private Image image;
	private Shape clip;
	
	public boolean error = false;
	public boolean relativeToTexures = true;
	
	public Sprite(String path){
		this(path, true);
	}
	
	public Sprite(String path, boolean relativeToTexures){
		this.path = path;
		this.relativeToTexures = relativeToTexures;
		reload();
		loadedSprites.add(this);
	}
	
	public Image getImage(){
		return image;
	}
	
	@SuppressWarnings("deprecation")
	public void reload(){
		if(relativeToTexures){
			image = Images.getImage(path);
		}else{
			image = Images.getImagePath(path);
		}
	}
	
	public static void reloadAll(){
		for(Sprite s : loadedSprites){
			s.reload();
		}
		Logger.info("Reloaded textures.");
	}
	
	public Shape getShape(){
		if(clip == null){
			clip = Utils.createArea(Images.toBufferedImage(getImage()), 1);
		}
		
		return clip;
	}
	
	public Image getImageAlpha(float alpha){
		
		if(alpha > 1){
			alpha = 1;
		}else if(alpha < 0){
			alpha = 0;
		}
		
		BufferedImage orig = Images.toBufferedImage(getImage());
		
		BufferedImage resizedImage = new BufferedImage(orig.getWidth(), orig.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.drawImage(orig, 0, 0, orig.getWidth(), orig.getHeight(), null);
		g.dispose();
		
		return resizedImage;
	}

	public int getWidth() {
		return getImage().getWidth(null);
	}
	
	public int getHeight() {
		return getImage().getHeight(null);
	}
	
}

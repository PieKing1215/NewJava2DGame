package me.pieking.game.graphics;

import java.awt.Image;

import me.pieking.game.Game;
import me.pieking.game.Logger;

public class AnimatedImage {

	public Sprite[] frames;
	
	public AnimatedImage(Sprite[] frames){
		this.frames = frames;
	}
	
	public AnimatedImage(String[] frames){
		
		Sprite[] newFrames = new Sprite[frames.length];
		
		for(int i = 0; i < frames.length; i++){
			newFrames[i] = Images.getSprite(frames[i]);
			if(newFrames[i].getImage() == Images.error){
				Logger.warn("Frame not found: " + frames[i]);
			}
		}
		
		this.frames = newFrames;
	}
	
	/**
	 * @param base
	 * @param frames Number of frames in the animation. (Up to but not including- Ex. if you put 12, it loads 0-11 for a total of 12 frames. In other words, the number of image files)
	 */
	public AnimatedImage(String base, int frames){
		
		Sprite[] newFrames = new Sprite[frames];
		
		for(int i = 0; i < frames; i++){
			newFrames[i] = Images.getSprite(base + i + ".png");
			if(newFrames[i].getImage() == Images.error){
				Logger.warn("Frame not found: " + base + frames + ".png");
			}
		}
		
		this.frames = newFrames;
	}
	
	public AnimatedImage(String base, int frames, String extension){
		
		Sprite[] newFrames = new Sprite[frames];
		
		for(int i = 0; i < frames; i++){
			newFrames[i] = Images.getSprite(base + i + extension);
			if(newFrames[i].getImage() == Images.error){
				Logger.warn("Frame not found: " + base + frames + extension);
			}
		}
		
		this.frames = newFrames;
	}
	
	/**
	 * Out of frames.length.
	 */
	public Image getFrame(float f){
		int index = Math.round(f);
		Image frame = Images.error;
		
		try{
			frame = frames[index].getImage();
		}catch(NullPointerException | ArrayIndexOutOfBoundsException e){
			//Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Frame not found: " + f + " " + e);
		}
		
		if(frame == null){
			frame = Images.error;
		}
		
		return frame;
	}
	
	/**
	 * Out of 1.
	 */
	public Image getFrameAbsolute(float f){
		return getFrame(f * (frames.length - 1));
	}
	
	/**
	 * Out of frames.length.
	 */
	public Sprite getFrameSprite(float f){
		int index = Math.round(f);
		Sprite frame = Images.errorS;
		
		try{
			frame = frames[index];
		}catch(NullPointerException | ArrayIndexOutOfBoundsException e){
			//Game.debug(DebugLevel.WARNING, DebugPriority.NORMAL, "Frame not found: " + f + " " + e);
		}
		
		if(frame == null){
			frame = Images.errorS;
		}
		
		return frame;
	}
	
	/**
	 * Out of 1.
	 */
	public Sprite getFrameAbsoluteSprite(float f){
		return getFrameSprite(f * (frames.length - 1));
	}
}

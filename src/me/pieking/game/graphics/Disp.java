package me.pieking.game.graphics;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import me.pieking.game.Logger;

public class Disp extends Canvas{
	
	private static final long serialVersionUID = 1L;

	public int width;
	public int height;
	public int rwidth;
	public int rheight;
	
	public BufferedImage image;
	public BufferedImage screenshotable;
    private int[] pixels;
    
    private static BufferStrategy bs;
    
    public int xOffset;
    public int yOffset;
    
    public Disp(int width, int height, int rwidth, int rheight){
    	this.width=width;
    	this.height=height;
    	this.rwidth=rwidth+1;
    	this.rheight=rheight+1;
    	image = new BufferedImage(width+1, height+1, BufferedImage.TYPE_INT_RGB);
    	pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    }
    
    public void init(){
		bs=this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(2);
		}
	}
    
	@Override
	public void paint(Graphics g){
		try{
			g.drawImage(image, 0 + xOffset, 0 + yOffset, rwidth, rheight, null);
		}catch(NullPointerException e){
			Logger.warn("Could not draw to Canvas: " + e.getClass().getName(), Logger.VB_DEV_ONLY);
		}
	}

	public void setPixel(int x, int y, int color){
		try{
			pixels[x+y*image.getWidth()]=color;
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
	}
	
	public void fill(int x, int y, int width, int height, int color){
		for(int w=0; w<width; w++){
			for(int h=0; h<height; h++){
				setPixel(x+w, y+h, color);
			}
		}
	}
	
	public int getPixel(int x, int y){
		try{
			return pixels[x+y*image.getWidth()];
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		return 0;
	}
	
	public static int getIntFromColor(int Red, int Green, int Blue/*, int Alpha*/){
	    Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
	    Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
	    Blue = Blue & 0x000000FF; //Mask out anything not blue.
	    //Alpha = Alpha & 0xFF000000;
	    
	    return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
	}
	
	public Point getMousePositionScaled(){
		
		try{
			if(getMousePosition() == null) return null;
			
			int x = getMousePosition().x;
			int y = getMousePosition().y;
			
			float xs = ((float)width / (float)rwidth);
			float ys = ((float)height / (float)rheight);
			
			x *= xs;
			y *= ys;
			
			//System.out.println(xs + " " + ys + " " + x + " " + y);
			
			return new Point(x, y);
		}catch(Exception e){
			return null;
		}
	}
	
	public BufferedImage getScreenShot() {
		try{
			Component component = this;
			BufferedImage image = new BufferedImage(
			  component.getWidth(),
			  component.getHeight(),
			  BufferedImage.TYPE_INT_RGB
			  );
			// call the Component's paint method, using
			// the Graphics object of the image.
			component.paint(image.getGraphics()); // alternately use .printAll(..)
			return image;
		}catch(Exception e){
			return null;
		}
	}
	
}


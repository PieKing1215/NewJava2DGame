package me.pieking.game.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import me.pieking.game.Game;
import me.pieking.game.Utils;

public class Render {

	private static Sprite testSprite = Images.getSprite("test.png");
	
	public static void render(Disp d){
		Graphics2D g = (Graphics2D) d.image.getGraphics();
		
		g.clearRect(0, 0, d.width, d.height);
		
		g.setColor(Utils.rainbowColor(0.1f, 0));
		g.fillRect(10, 10, 20, 20);
		
		g.drawRect(0, 0, d.width-1, d.height-1);
		g.setFont(new Font("Comic Sans MS", 0, 40));
		g.drawString("TEST", (int) (200 + Math.cos(Game.getTime() / 30f) * 20), (int) (200 + Math.sin(Game.getTime() / 30f) * 20));
		
		g.drawImage(testSprite.getImage(), 400, 100, null);
		
		g.dispose();
	}
	
}

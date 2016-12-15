package me.pieking.game;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import me.pieking.game.Vars.ExitState;
import me.pieking.game.events.KeyHandler;
import me.pieking.game.events.WindowHandler;
import me.pieking.game.graphics.Disp;
import me.pieking.game.graphics.Render;

public class Game {

	private static JFrame frame;
	private static JPanel jp;
	private static boolean running = false;
	
	private static double nsPerTick = 1e9 / 60d; //60 tps
	private static double delta;
	
	private static int time = 0;
	
	private static boolean skipTick = false;
	
	private static int dbgTPS = 0;
	private static int dbgFPS = 0;
	
	private static String name = "New 2D Game";
	private static String version = "0.0.0-dev0"; //increase every time you push to github
	
	private static int width = 600;
	private static int height = 400;
	
	private static KeyHandler keyListener;
	private static WindowHandler windowListener;
	
	private static Disp disp;
	private static boolean fullScreen;
	
	private static boolean isApplet = false;
	private static boolean hideCursor = false;
	
	public static void main(String[] args) {
		run();
	}

	private static void run(){
		Logger.info("Running " + name + " v" + version + "...");
		
		init();
		
		running = true;
		
		int refresh = getMonitorRefreshRate();
    	boolean vsync = false;
		
		long lastTime = System.nanoTime();
        nsPerTick = 1000000000D / 60D;

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        delta = 0;
        
        while (running) {
        	
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;

            while (delta >= 1) {
                if(skipTick){
                	delta = 0;
                	skipTick = false;
                	break;
                }
            	
            	ticks++;
                tick();
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shouldRender) {
            	if(frames < refresh || !vsync){
            		frames++;
                	render();
            	}
            }
            
            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                dbgFPS = frames;
                dbgTPS = ticks;
                frames = 0;
                ticks = 0;
            }
        }
		
	}
	
	private static void init(){
		
		Logger.info("Initializing...");
		
		frame = new JFrame(name);
		jp = new JPanel();
		jp.setPreferredSize(new Dimension(width - 10, height - 10));
		frame.getContentPane().add(jp);
		frame.pack();
		jp.setVisible(false);
		
		disp = new Disp(width, height, width, height);
		
		frame.getContentPane().add(disp);
		
		windowListener = new WindowHandler();
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(windowListener);
		
		keyListener = new KeyHandler();
		disp.addKeyListener(keyListener);
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private static void tick(){
		time++;
	}
	
	private static void render(){
		Render.render(disp);
		disp.paint(disp.getGraphics());
		disp.screenshotable = disp.getScreenShot();
	}
	
	public static int getMonitorRefreshRate(){
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();

		DisplayMode dm = gs[0].getDisplayMode();

		return dm.getRefreshRate();
	}

	public static void stop(ExitState state) {
		System.exit(state.code);
	}
	
	public static String getName(){
		return name;
	}
	
	public static void toggleFullScreen(){
		
		if(isApplet) return;
		
		JFrame newFrame = new JFrame(name + " " + version);
		
		if(!fullScreen){
			newFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			newFrame.setUndecorated(true);
		}else{
			//newFrame.setExtendedState(JFrame.NORMAL);
			newFrame.setUndecorated(false);
		}
		
		jp = new JPanel();
		jp.setPreferredSize(new Dimension(width - 10, height - 10));
		newFrame.getContentPane().add(jp);
		newFrame.getContentPane().setBackground(Color.BLACK);
		newFrame.pack();
		jp.setVisible(false);
		newFrame.setVisible(true);
		newFrame.setLocationRelativeTo(null);
		newFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		newFrame.setTitle(name);
		newFrame.add(disp);
		newFrame.setLayout(null);
		newFrame.addWindowListener(windowListener);
		newFrame.setResizable(false);
		
//		Image file = Images.getSprite("battle/icons/heart.png").getImage();
//		ImageIcon iconI = new ImageIcon(file);
//		newFrame.setIconImage(iconI.getImage());
		
		if(!fullScreen) {
			int dheight = (int) (newFrame.getHeight());
			int dwidth = (int) (dheight * ((float)width / (float)height));
			
			//System.out.println(disp.rwidth + " " + dwidth );
			
			disp.setBounds((newFrame.getWidth() / 2) - (dwidth / 2), 0, dwidth, dheight);
			disp.rheight = dheight + 2;
			disp.rwidth = dwidth + 2;
			
		}else{
			disp.rheight = height + 1;
			disp.rwidth = width + 1;
			disp.setBounds(0, 0, width, height);
		}
		
		frame.dispose();
		
		frame = newFrame;
		
		setHideCursor(hideCursor);
		
		fullScreen = !fullScreen;
		
		Robot r;
		try {
			
			r = new Robot();
			
			Timer t = new Timer(100, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
					int y = (int) MouseInfo.getPointerInfo().getLocation().getY();
					r.mouseMove(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);
					r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
					r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					r.mouseMove(x, y);
				}
			});
			t.setRepeats(false);
			t.start();
			
		} catch (AWTException e1) {}
	}

	public static void setHideCursor(boolean hide){
	
		if(isApplet) return;
		
		hideCursor = hide;
		
		if(hide){
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
	
			frame.getContentPane().setCursor(blankCursor);
		}else{
			frame.getContentPane().setCursor(Cursor.getDefaultCursor());
		}
	}
	
	public static int getTime(){
		return time;
	}

}

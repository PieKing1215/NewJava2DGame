package me.pieking.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Utils {

	/**
	 * Opens a webpage on the user's browser.
	 * @param uri The URI of the webpage
	 * @throws Exception 
	 */
	public static void openWebpage(URI uri) throws Exception {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	    	desktop.browse(uri);
	    }
	}
	
	/**
	 * Opens a webpage on the user's browser.
	 * @param url The URL of the webpage
	 * @throws URISyntaxException 
	 */
	public static void openWebpage(URL url) throws Exception {
		openWebpage(url.toURI());
	}
	
	/**
	 * Gets a random element from the array.
	 * 
	 * @param strings List from which an element will be selected
	 */
	public static String randString(String[] strings){
    	int rand = Rand.range(0, strings.length - 1);
    	return strings[rand];
    }
	
	/**
     * Theta is in radians.
     */
    public static Point2D.Float polarToCartesian(float theta, float distance){

    	float x = (float) (Math.cos( theta ) * distance);
    	float y = (float) (Math.sin( theta ) * distance);
    	
    	return new Point2D.Float(x, y);
    }
    
    /**
     * Convert degrees to radians. Same as Math.toRadians()
     * 
     * @see Math#toRadians
     */
    public static double degToRad(double deg){
		return (deg*Math.PI)/180;
	}
	
    /**
     * Convert radians to degrees. Same as Math.toDegrees()
     * 
     * @see Math#toDegrees
     */
	public static double radToDeg(double rad){
		return (rad*180)/Math.PI;
	}
	
	/**
	 * Get a rainbow color from the time. (My favorite method ;P)
	 * 
	 * @param frequency The rate of change
	 * @param timeOffset If you want to offset the time
	 */
	public static Color rainbowColor(float frequency, int timeOffset){
		
		int i = Game.getTime() + timeOffset;
		
		float red   = (float) (Math.sin(frequency*i + 0) * 127 + 128);
		float green = (float) (Math.sin(frequency*i + 2) * 127 + 128);
		float blue  = (float) (Math.sin(frequency*i + 4) * 127 + 128);
		
		return new Color(red / 255f, green / 255f, blue / 255f);
	}
	
	public static Object getInstance(String name, String... params){
		name = "me.pieking.game." + name;
		Class<?> c = null;
		
		Class<?>[] paramTypes = null;
		
		if(params != null){
			paramTypes = new Class<?>[params.length];
			for(int i = 0; i < paramTypes.length; i++){
				paramTypes[i] = String.class;
			}
		}
		
		try {
			c = Class.forName(name);
		} catch (ClassNotFoundException e) {
			Logger.warn("Could not find class: |" + name + "|");
			e.printStackTrace();
		}
		try {
			if(paramTypes == null){
				return c.newInstance();
			}else{
				return c.getDeclaredConstructor(paramTypes).newInstance((Object[])params);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			Logger.warn("Could not create instance of class: |" + name + "|");
			e.printStackTrace();
		}
		return null;
	}
	
	public static float average(int[] data) {  
	    int sum = 0;

	    for (int d : data) sum += d;
	    
	    float average = 1.0f * sum / data.length;
	
	    return average;
	}
	
	public static float average(float[] data) {  
	    int sum = 0;

	    for (float d : data) sum += d;
	    
	    float average = 1.0f * sum / data.length;
	
	    return average;
	}
	
	/**
	 * Gets the outline of an image.
	 */
	public static Area createArea(BufferedImage image, int maxTransparency) {
        Area area = new Area();
        Rectangle rectangle = new Rectangle();
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                rgb = rgb >>> 24;
                if (rgb >= maxTransparency) {
                    rectangle.setBounds(x, y, 1, 1);
                    area.add(new Area(rectangle));
                }
            }
        }
        return area;
    }
	
	public static Point2D rotateAboutPoint(Point2D toRotate, Point2D center, double angle){
		double[] pt = {toRotate.getX(), toRotate.getY()};
		AffineTransform.getRotateInstance(Math.toRadians(angle), center.getX(), center.getY()).transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
		double newX = pt[0];
		double newY = pt[1];
		
		return new Point2D.Double(newX, newY);
	}
	
	public static List<String> wrapString(String str, int lineWidth, FontMetrics fm){
		List<String> wrapped = new ArrayList<String>();
		
		String currLine = "";
		for(String s : str.split(" ")){
			if(fm.stringWidth(currLine + s + " ") < lineWidth){
				currLine = currLine + s + " ";
			}else{
				wrapped.add(currLine);
				currLine = "" + s + " ";
			}
		}
		wrapped.add(currLine);
		
		return wrapped;
	}
	
	public static String getStackTrace(final Throwable throwable) {
	     final StringWriter sw = new StringWriter();
	     final PrintWriter pw = new PrintWriter(sw, true);
	     throwable.printStackTrace(pw);
	     return sw.getBuffer().toString();
	}
	
//	public static JSONObject getJSON(String path){
//		String json = "";
//		
//		BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getClassLoader().getResourceAsStream(path)));
//		
//		String line;
//		try {
//			while((line = br.readLine()) != null){
//				json = json + line;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(json);
//		
//		JSONObject jo = new JSONObject(json);
//		
//		return jo;
//	}
//	
//	public static JSONObject getJSON(URL path){
//		String json = "";
//		
//		BufferedReader br = null;
//		try {
//			br = new BufferedReader(new InputStreamReader(path.openStream()));
//		} catch (NullPointerException e1) {
//			Game.warn("json file not found at " + path);
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		
//		String line;
//		try {
//			while((line = br.readLine()) != null){
//				json = json + line;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		//System.out.println(json);
//		
//		JSONObject jo = new JSONObject(json);
//		
//		return jo;
//	}
//	
//	public static JSONObject getJSON(InputStream stream){
//		String json = "";
//		
//		BufferedReader br = null;
//		br = new BufferedReader(new InputStreamReader(stream));
//		
//		String line;
//		try {
//			while((line = br.readLine()) != null){
//				json = json + line;
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println(json);
//		
//		JSONObject jo = new JSONObject(json);
//		
//		return jo;
//	}
	
	public static void drawDialogueBox(Graphics g, int x, int y, int w, int h){
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, w, h);
		
		g.setColor(Color.WHITE);
		
		g2d.setStroke(new BasicStroke(6));
		
		g.drawRect(x, y, w, h);
		
		g2d.setStroke(new BasicStroke(1));
	}
	
	public static void drawDialogueBox(Graphics g, Rectangle r){
		Graphics2D g2d = (Graphics2D) g;
		
		int x = r.x;
		int y = r.y;
		int w = r.width;
		int h = r.height;
		
		g.setColor(Color.BLACK);
		g.fillRect(x, y, w, h);
		
		g.setColor(Color.WHITE);
		
		g2d.setStroke(new BasicStroke(6));
		
		g.drawRect(x, y, w, h);
		
		g2d.setStroke(new BasicStroke(1));
	}
	
	public static String toTitleCase(String input) {
	    StringBuilder titleCase = new StringBuilder();
	    boolean nextTitleCase = true;

	    for (char c : input.toCharArray()) {
	        if (Character.isSpaceChar(c)) {
	            nextTitleCase = true;
	        } else if (nextTitleCase) {
	            c = Character.toTitleCase(c);
	            nextTitleCase = false;
	        }

	        titleCase.append(c);
	    }

	    return titleCase.toString();
	}
	
}

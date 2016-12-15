package me.pieking.game.graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class Fonts {

	public static Font damage = new Font("Arial", 0, 20);
	public static Font combatHUD = new Font("Arial", 0, 20);
	public static Font determMono = new Font("Arial", 0, 20);
	public static Font determSans = new Font("Arial", 0, 20);
	public static Font dotumChe = new Font("DotumChe", Font.BOLD, 14);
	public static Font wingDings = new Font("WingDings", 0, 16);
	
	public static void init(){
		
		InputStream is = Fonts.class.getResourceAsStream("/fonts/damage.ttf");
		
		try {
			Font font2 = Font.createFont(Font.TRUETYPE_FONT, is);
			damage = font2.deriveFont(0, 24);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//===============
		
		InputStream is2 = Fonts.class.getResourceAsStream("/fonts/combatHUD.ttf");
		
		try {
			Font font2 = Font.createFont(Font.TRUETYPE_FONT, is2);
			combatHUD = font2.deriveFont(0, 24);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		try {
			is2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//===============
		
				InputStream is3 = Fonts.class.getResourceAsStream("/fonts/DTM-Mono.ttf");
				
				try {
					Font font2 = Font.createFont(Font.TRUETYPE_FONT, is3);
					determMono = font2.deriveFont(0, 26);
				} catch (FontFormatException | IOException e) {
					e.printStackTrace();
				}
				
				try {
					is3.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			//===============
			
			InputStream is4 = Fonts.class.getResourceAsStream("/fonts/DTM-Sans.ttf");
			
			try {
				Font font2 = Font.createFont(Font.TRUETYPE_FONT, is4);
				determSans = font2.deriveFont(0, 26);
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
			
			try {
				is4.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	public static String execFormatValue(String str, String f) {

	  // Idk what this means

	  // Gewisse Schriften können die alten Texte nicht anzeigen.
	  // Beispielsweise sollte "A" für WingDings ein Victory Zeichen zeigen.
	  // Dies funktioniert neuerdings nur, wenn man in der Private Use Area nachschaut.
	  // http://www4.carthage.edu/faculty/ewheeler/GrafiX/LessonsAdvanced/wingdings.pdf
	  // http://www.fileformat.info/info/unicode/char/270c/index.htm
	  // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6176474
		if (str != null && f != null) {
			Font font = new Font(f, Font.PLAIN, 1);
			boolean changed = false;
			char[] chars = str.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (!font.canDisplay(chars[i])) {
					if (chars[i] < 0xF000) {
						chars[i] += 0xF000;
						changed = true;
					}
				}
			}
			if (changed)
				str = new String(chars);
		}
		return str;
	}
	
	public static String execFormatValue(String str, Font f) {

		  // Idk what this means

		  // Gewisse Schriften können die alten Texte nicht anzeigen.
		  // Beispielsweise sollte "A" für WingDings ein Victory Zeichen zeigen.
		  // Dies funktioniert neuerdings nur, wenn man in der Private Use Area nachschaut.
		  // http://www4.carthage.edu/faculty/ewheeler/GrafiX/LessonsAdvanced/wingdings.pdf
		  // http://www.fileformat.info/info/unicode/char/270c/index.htm
		  // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6176474
			if (str != null && f != null) {
				Font font = f;
				boolean changed = false;
				char[] chars = str.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					if (!font.canDisplay(chars[i])) {
						if (chars[i] < 0xF000) {
							chars[i] += 0xF000;
							changed = true;
						}
					}
				}
				if (changed)
					str = new String(chars);
			}
			return str;
		}
	
}

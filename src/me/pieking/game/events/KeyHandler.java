package me.pieking.game.events;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import me.pieking.game.Game;

public class KeyHandler implements KeyListener{

	private List<Integer> pressed = new ArrayList<Integer>();
	
	public void keyJustPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_F4){
			Game.toggleFullScreen();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(!pressed.contains(e.getKeyCode())){
			pressed.add(e.getKeyCode());
			keyJustPressed(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(pressed.contains(e.getKeyCode())){
			pressed.remove((Object)e.getKeyCode()); //cast the code to Object so it uses remove(Object) instead of remove(int)
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	public List<Integer> getPressed(){
		List<Integer> ret = new ArrayList<Integer>();
		ret.addAll(pressed);
		return ret;
	}
	
	public boolean isPressed(int keyCode){
		return (pressed.contains(keyCode));
	}
	
	public boolean isPressed(char keyChar){
		return isPressed(KeyEvent.getExtendedKeyCodeForChar(keyChar));
	}
	
}

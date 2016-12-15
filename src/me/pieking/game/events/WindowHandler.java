package me.pieking.game.events;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import me.pieking.game.Game;
import me.pieking.game.Vars.ExitState;

public class WindowHandler implements WindowListener{

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) { //when the window has been closed
		
	}

	@Override
	public void windowClosing(WindowEvent e) { // when the user trys to close the window
		Game.stop(ExitState.OK);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

}

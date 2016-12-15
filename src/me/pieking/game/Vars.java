package me.pieking.game;

public class Vars {

	public static enum ExitState {
		UNKNOWN(-50),
		OK(0);
		
		public int code = 0;
		
		private ExitState(int code) {
			this.code = code;
		}
		
	}
	
}

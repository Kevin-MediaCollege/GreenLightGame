package ma.greenlightgame;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/** @since Nov 10, 2014 */
public class Main {
	private static final String TITLE = "GreenLight Game";
	
	private static final int WINDOW_WIDTH = 1280;
	private static final int WINDOW_HEIGHT = 720;
	
	private Game game;
	
	public Main() {
		game = new Game();
		
		init();
		loop();
		destroy();
	}
	
	private void init() {
		try {
			Display.setTitle(TITLE);
			Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
			
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void loop() {
		while(!Display.isCloseRequested()) {
			game.update();
			game.render();
			
			Display.update();
		}
	}
	
	private void destroy() {
		Mouse.destroy();
		Keyboard.destroy();
		Display.destroy();
		
		System.exit(0);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
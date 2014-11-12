package ma.greenlightgame;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;
import ma.greenlightgame.renderer.Renderer;

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
	
	private final Renderer renderer;
	
	private Game game;
	
	public Main() {
		renderer = new Renderer();
		
		init();
		initGL();
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
	
	private void initGL() {
		glViewport(0,  0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		glOrtho(0, WINDOW_WIDTH, 0, WINDOW_HEIGHT, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		glDisable(GL_DEPTH_TEST);
	}
	
	private void loop() {
		game = new Game();
		
		while(!Display.isCloseRequested()) {
			game.update();
			game.render(renderer);
			
			Display.update();
			Display.sync(60);
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
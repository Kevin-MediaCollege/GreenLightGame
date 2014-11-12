package ma.greenlightgame;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import ma.greenlightgame.input.Input;
import ma.greenlightgame.renderer.Renderer;
import ma.greenlightgame.utils.Utils;

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
	private final Input input;
	
	private Game game;
	
	public Main() {
		renderer = new Renderer();
		input = new Input();
		
		init();
		initGL();
		loop();
		destroy();
	}
	
	private void init() {
		try {
			Display.setTitle(TITLE);
			Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
			
			try {
				ByteBuffer[] icons = new ByteBuffer[2];
				icons[0] = Utils.loadIcon(ImageIO.read(new File("./res/icons/icon16.png")));
				icons[1] = Utils.loadIcon(ImageIO.read(new File("./res/icons/icon32.png")));
				
				Display.setIcon(icons);
			} catch(IOException e) {
				e.printStackTrace();
			}
			
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
			game.update(input);
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
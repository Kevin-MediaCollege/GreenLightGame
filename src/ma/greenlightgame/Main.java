package ma.greenlightgame;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import ma.greenlightgame.config.Config;
import ma.greenlightgame.input.Input;
import ma.greenlightgame.input.Input.KeyCode;
import ma.greenlightgame.renderer.Renderer;
import ma.greenlightgame.renderer.Window;
import ma.greenlightgame.utils.Utils;

import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.Display;

public class Main {
	private static Main instance;
	
	private final Renderer renderer;
	private final Window window;
	private final Input input;
	
	private Game game;
	
	private Main() {
		instance = this;
		
		Config.load();
		
		setIcons();
		
		window = new Window(
				Config.RENDER_WIDTH,
				Config.RENDER_HEIGHT,
				Config.getInt(Config.DISPLAY_WIDTH),
				Config.getInt(Config.DISPLAY_HEIGHT),
				Config.NAME,
				Config.getBool(Config.FULLSCREEN),
				Config.getBool(Config.VSYNC)
			);
		
		renderer = new Renderer();
		input = new Input();
		
		System.out.println("Done initializing");
		
		loop();
		destroy();
	}
	
	private void loop() {
		game = new Game();
		
		while(!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			
			if(input.isKeyDown(KeyCode.F)) {
				Config.flush();
				
				Window.setSize( Config.RENDER_WIDTH,
								Config.RENDER_HEIGHT,
								Config.getInt(Config.DISPLAY_WIDTH),
								Config.getInt(Config.DISPLAY_HEIGHT));
			}
			
			game.update(input, (float)(1f / 60f));
			game.render(renderer);
			
			Display.update();
			Display.sync(Config.FRAMERATE);
		}
	}
	
	private void destroy() {
		window.destroy();
		
		System.exit(0);
	}
	
	private void setIcons() {
		ByteBuffer[] icons = null;
		
		try {
			switch(LWJGLUtil.getPlatform()) {
			case LWJGLUtil.PLATFORM_WINDOWS:
				icons = new ByteBuffer[] {
						Utils.loadIcon(ImageIO.read(new File("./res/icons/icon16.png"))),
						Utils.loadIcon(ImageIO.read(new File("./res/icons/icon32.png")))
					};
				break;
			case LWJGLUtil.PLATFORM_MACOSX:
				icons = new ByteBuffer[] {
						Utils.loadIcon(ImageIO.read(new File("./res/icons/icon128.png"))), // TODO: Get a 128x128 icon
					};
				break;
			case LWJGLUtil.PLATFORM_LINUX:
				icons = new ByteBuffer[] {
						Utils.loadIcon(ImageIO.read(new File("./res/icons/icon32.png"))),
					};
				break;
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		Window.setIcons(icons);
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
	public static void stop() {
		instance.destroy();
	}
}
package ma.greenlightgame.common;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Window;
import ma.greenlightgame.common.config.Config;
import ma.greenlightgame.common.utils.Utils;

import org.lwjgl.LWJGLUtil;

public class Main {
	private static Main instance;
	
	private final Renderer renderer;
	private final Window window;
	private final Input input;
	
	private Game game;
	
	private boolean isRunning;
	
	private Main() {
		instance = this;
		
		Config.load();
		setIcons();
		
		window = new Window(
				Config.RENDER_WIDTH,
				Config.RENDER_HEIGHT,
				Config.getInt(Config.DISPLAY_WIDTH),
				Config.getInt(Config.DISPLAY_HEIGHT),
				Config.NAME + " - 00FPS",
				Config.getBool(Config.FULLSCREEN),
				Config.getBool(Config.VSYNC)
			);
		
		renderer = new Renderer();
		input = new Input();
		isRunning = true;
		
		System.out.println("Done initializing");
		
		loop();
		destroy();
	}
	
	private void loop() {
		game = new Game();
		
		double unprocessedTime = 0;
		double secondsPerFrame = 1.0 / (double)Config.FRAMERATE;
		double frameCounterTime = 0;
		
		long previousTime = System.nanoTime();
		
		int frames = 0;
		
		while(isRunning) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			
			// TODO: float deltaTime = (float)((currentTime / 1000000) - (previousTime / 1000000));
			
			boolean render = false;
			
			previousTime = currentTime;
			unprocessedTime += passedTime / 1000000000.0;
			frameCounterTime += passedTime / 1000000000.0;
			
			if(frameCounterTime >= 1.0) {
				Window.setTitle(Config.NAME + " - " + frames + "FPS");
				
				frames = 0;
				frameCounterTime = 0;
			}
			
			while(unprocessedTime > secondsPerFrame) {
				render = true;
				
				if(Window.isCloseRequested())
					stop();
				
				game.update(input, 1); // TODO: Dynamic delta time
				input.poll();
				
				unprocessedTime -= secondsPerFrame;
			}
			
			if(render) {
				glClear(GL_COLOR_BUFFER_BIT);
				frames++;
				
				game.render(renderer);
				window.update();
			} else {
				try {
					Thread.sleep(1);
				} catch(InterruptedException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
	}
	
	private void destroy() {
		isRunning = false;
		
		game.destroy();
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
						Utils.loadIcon(ImageIO.read(new File("./res/icons/icon32.png"))), // TODO: Get a 128x128 icon
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
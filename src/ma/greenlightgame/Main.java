package ma.greenlightgame;

import static org.lwjgl.system.glfw.GLFW.GLFW_AUTO_ICONIFY;
import ma.greenlightgame.engine.window.GLFWException;
import ma.greenlightgame.engine.window.GLFWWindow;


/** @since Nov 10, 2014 */
public class Main {
	private GLFWWindow mainWindow;
	private GLFWWindow secWindow;
	
	public Main() {
		try {
			GLFWWindow.create();
			GLFWWindow.setSwapInterval(1);
			
			GLFWWindow.hint(GLFW_AUTO_ICONIFY, 1);
			
			mainWindow = new GLFWWindow(1280, 720, "MULTI", GLFWWindow.WINDOWED);
			mainWindow.makeCurrent();
			mainWindow.setWindowCallBack(new WindowCallbackHandler());
			
			GLFWWindow.defaultHints();
			
			secWindow = new GLFWWindow(830, 480, "WINDOW!", GLFWWindow.WINDOWED);
			secWindow.setPosition(200, 200);
		} catch(GLFWException e) {
			e.printStackTrace();
		}
		
		loop();
	}
	
	private void loop() {
		while(!mainWindow.shouldClose()) {
			if(secWindow.shouldClose())
				secWindow.destroy();
			
			mainWindow.update();
			secWindow.update();
		}
		
		mainWindow.destroy();
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
package ma.greenlightgame.engine;

import ma.greenlightgame.engine.window.glfw.GLFWException;
import ma.greenlightgame.engine.window.glfw.GLFWWindow;

import org.lwjgl.system.glfw.WindowCallback;

public class Window extends WindowCallback {
	private GLFWWindow window;
	
	public Window() {
		try {
			GLFWWindow.create();
			
			window = new GLFWWindow(1280, 720, "Fap");
			
			window.makeCurrent();
		} catch(GLFWException e) {
			e.printStackTrace();
		}
		
		while(!window.shouldClose()) {
			window.update();
		}
	}

	@Override
	public void windowPos(long window, int xpos, int ypos) {
		System.out.println("Window " + window + " moved: " + xpos + " - " + ypos);
	}

	@Override
	public void windowSize(long window, int width, int height) {
		System.out.println("Window " + window + " resized: " + width + " - " + height);
	}

	@Override
	public void windowClose(long window) {
		System.out.println("Window " + window + " closed");
	}

	@Override
	public void windowRefresh(long window) {
		System.out.println("Window " + window + " refreshed");
	}

	@Override
	public void windowFocus(long window, int focused) {
		System.out.println("Window " + window + " focus change: " + focused);
	}

	@Override
	public void windowIconify(long window, int iconified) {}

	@Override
	public void framebufferSize(long window, int width, int height) {}

	@Override
	public void key(long window, int key, int scancode, int action, int mods) {}

	@Override
	public void character(long window, int codepoint) {}

	@Override
	public void charMods(long window, int codepoint, int mods) {}

	@Override
	public void mouseButton(long window, int button, int action, int mods) {}

	@Override
	public void cursorPos(long window, double xpos, double ypos) {}

	@Override
	public void cursorEnter(long window, int entered) {}

	@Override
	public void scroll(long window, double xoffset, double yoffset) {}

	@Override
	public void drop(long window, int count, long names) {}
}

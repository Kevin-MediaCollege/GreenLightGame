package ma.greenlightgame.engine.window;

import static org.lwjgl.system.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.system.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.system.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.system.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.system.glfw.GLFW.glfwGetWindowPos;
import static org.lwjgl.system.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.system.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.system.glfw.GLFW.glfwInit;
import static org.lwjgl.system.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.system.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.system.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.system.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.system.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.system.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.system.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.system.glfw.GLFW.glfwTerminate;
import static org.lwjgl.system.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.system.glfw.GLFW.glfwWindowShouldClose;

import java.nio.IntBuffer;

import org.lwjgl.system.glfw.WindowCallback;

public class GLFWWindow {
	public static final int WINDOWED = 0;
	public static final int FULLSCREEN = 1;
	
	private final long windowId;
	
	public GLFWWindow(int width, int height, String title) throws GLFWException {
		this(width, height, title, WINDOWED);
	}
	
	public GLFWWindow(int width, int height, String title, int type) throws GLFWException {
		long monitor = 0;
		
		if(type == FULLSCREEN)
			monitor = glfwGetPrimaryMonitor();
		
		this.windowId = glfwCreateWindow(width, height, title, monitor, 0);
		
		if(windowId == 0L) {
			glfwTerminate();
			throw new GLFWException("Failed to create a GLFW window");
		}
	}
	
	public void update() {
		glfwSwapBuffers(windowId);
		glfwPollEvents();
	}
	
	public void makeCurrent() {
		glfwMakeContextCurrent(windowId);
	}
	
	public void destroy() {
		glfwDestroyWindow(windowId);
	}
	
	public boolean shouldClose() {
		return glfwWindowShouldClose(windowId) == 1 ? true : false;
	}
	
	public void setWindowCallBack(WindowCallback callback) {
		WindowCallback.set(windowId, callback);
	}
	
	public void setPosition(int x, int y) {
		glfwSetWindowPos(windowId, x, y);
	}
	
	public void setSize(int width, int height) {
		glfwSetWindowSize(windowId, width, height);
	}
	
	public void setTitle(String title) {
		glfwSetWindowTitle(windowId, title);
	}
	
	public void getPosition(IntBuffer x, IntBuffer y) {
		glfwGetWindowPos(windowId, x, y);
	}
	
	public void getSize(IntBuffer width, IntBuffer height) {
		glfwGetWindowSize(windowId, width, height);
	}
	
	public static void create() throws GLFWException {
		if(glfwInit() == 0)
			throw new GLFWException("Failed to initialize GLFW");
	}
	
	public static void setSwapInterval(int interval) {
		glfwSwapInterval(interval);
	}
	
	public static void defaultHints() {
		glfwDefaultWindowHints();
	}
	
	public static void hint(int target, int hint) {
		glfwWindowHint(target, hint);
	}
}
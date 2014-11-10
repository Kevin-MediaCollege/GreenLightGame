package ma.greenlightgame.engine.window.glfw;

import static org.lwjgl.system.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.system.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.system.glfw.GLFW.glfwGetWindowPos;
import static org.lwjgl.system.glfw.GLFW.glfwInit;
import static org.lwjgl.system.glfw.GLFW.*;
import static org.lwjgl.system.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.system.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.system.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.system.glfw.GLFW.glfwTerminate;
import static org.lwjgl.system.glfw.GLFW.glfwWindowShouldClose;

import java.nio.ByteBuffer;

import org.lwjgl.system.glfw.WindowCallback;

public class GLFWWindow {
	private final long windowId;
	
	private String title;
	
	private int width, height;
	
	public static void create() throws GLFWException {
		if(glfwInit() == 0)
			throw new GLFWException("Failed to initialize GLFW");
	}
	
	public GLFWWindow(int width, int height, String title) throws GLFWException {
		this(width, height, title, 0);
	}
	
	public GLFWWindow(int width, int height, String title, long monitor) throws GLFWException {
		this.windowId = glfwCreateWindow(width, height, title, 0, 0);
		
		if(windowId == 0L) {
			glfwTerminate();
			throw new GLFWException("Failed to create a GLFW window");
		}
		
		this.title = title;
		this.width = width;
		this.height = height;
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
		// TODO: Set window callback
	}
	
	public void setPosition(int x, int y) {
		glfwSetWindowPos(windowId, x, y);
	}
	
	public void setTitle(String title) {
		glfwSetWindowTitle(windowId, title);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getTitle() {
		return title;
	}
}

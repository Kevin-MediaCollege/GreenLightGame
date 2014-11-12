package ma.greenlightgame.renderer;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glViewport;

import java.nio.ByteBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/** @author Kevin Krol
 * @since Nov 12, 2014 */
public class Window {
	
	public Window(int renderWidth, int renderHeight, int displayWidth, int displayHeight, String title, boolean fullscreen, boolean vSync) {
		try {
			Display.setTitle(title);
			setFullscreen(displayWidth, displayHeight, fullscreen);
			
			if(vSync)
				setVSyncEnabled(vSync);
			
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		initGL(renderWidth, renderHeight);
		
	}
	
	public void update() {
		Display.update();
	}
	
	public void destroy() {
		Mouse.destroy();
		Keyboard.destroy();
		Display.destroy();
	}
	
	private static void initGL(int renderWidth, int renderHeight) {
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		glOrtho(0, renderWidth, 0, renderHeight, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		
		glDisable(GL_DEPTH_TEST);
		
		System.out.println("OpenGL (re)initialized");
	}
	
	public static void setFullscreen(int width, int height, boolean fullscreen) {
		final DisplayMode cdm = Display.getDisplayMode();
		
		if(cdm.getWidth() == width && cdm.getHeight() == height && Display.isFullscreen() == fullscreen)
			return;
		
		try {
			DisplayMode tdm = null;
			
			if(fullscreen) {
				DisplayMode[] dms = Display.getAvailableDisplayModes();
				int freq = 0;
				
				for(DisplayMode dm : dms) {					
					if(dm.getWidth() == width && dm.getHeight() == height) {
						DisplayMode ddm = Display.getDesktopDisplayMode();
						
						if(tdm == null || dm.getFrequency() >= freq) {
							if(tdm == null || dm.getBitsPerPixel() > tdm.getBitsPerPixel()) {
								tdm = dm;
								freq = tdm.getFrequency();
							}
						}
						
						if(dm.getBitsPerPixel() == ddm.getBitsPerPixel() && dm.getFrequency() == ddm.getFrequency()) {
							tdm = dm;
							break;
						}
					}
				}					
			} else {
				tdm = new DisplayMode(width, height);
			}
			
			if(tdm == null) {
				System.err.println("Failed to find a compatible display mode: " + width + "x" + height + " fullscreen=" + fullscreen);
				return;
			}
			
			Display.setDisplayMode(tdm);
			Display.setFullscreen(fullscreen);
			
			System.out.println("Display size set to: " + width + "x" + height + " fullscreen=" + fullscreen);
		} catch(LWJGLException e) {
			System.err.println("Failed to setup display mode: " + width + "x" + height + " fullscreen=" + fullscreen);
		}
	}
	
	public static void setSize(int renderWidth, int renderHeight, int displayWidth, int displayHeight) {
		try {
			Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
		} catch(LWJGLException e) {
			e.printStackTrace();
		}
		
		initGL(renderWidth, renderHeight);
		
		System.out.println("Display size set to: " + displayWidth + "x" + displayHeight + " | Render size set to: " + renderWidth + "x" + renderHeight);
	}
	
	public static void setVSyncEnabled(boolean vSync) {
		Display.setVSyncEnabled(vSync);
		
		System.out.println("VSync set to: " + vSync);
	}
	
	public static void setIcons(ByteBuffer[] icons) {
		Display.setIcon(icons);
	}
	
	public static int getWidth() {
		return Display.getWidth();
	}
	
	public static int getHeight() {
		return Display.getHeight();
	}
}

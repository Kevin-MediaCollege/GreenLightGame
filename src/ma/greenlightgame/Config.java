package ma.greenlightgame;

import java.util.HashMap;
import java.util.Map;

/** @author Kevin Krol
 * @since Nov 12, 2014 */
public class Config {
	private static Map<String, Integer> intValues;
	private static Map<String, Boolean> boolValues;
	
	public static final String NAME = "GreenLight Game";
	
	public static final int FRAMERATE = 60;
	
	// Keys
	public static final String RENDER_WIDTH = "renderWidth";
	public static final String RENDER_HEIGHT = "renderHeight";
	public static final String DISPLAY_WIDTH = "displayWidth";
	public static final String DISPLAY_HEIGHT = "displayHeight";
	public static final String FULLSCREEN = "fullscreen";
	public static final String VSYNC = "vsync";
	
	// Default values
	private static int DEFAUL_RENDER_WIDTH = 1280;
	private static int DEFAUL_RENDER_HEIGHT = 720;
	
	private static int DEFAUL_DISPLAY_WIDTH = 1280;
	private static int DEFAUL_DISPLAY_HEIGHT = 720;
	
	private static boolean DEFAUL_FULLSCREEN = false;
	private static boolean DEFAULT_VSYNC = false;
	
	public static void loadOrInit() {
		intValues = new HashMap<String, Integer>();
		boolValues = new HashMap<String, Boolean>();
		
		initDefaultValues();		
	}
	
	private static void initDefaultValues() {
		// Render size
		setInt(RENDER_WIDTH, DEFAUL_RENDER_WIDTH);
		setInt(RENDER_HEIGHT, DEFAUL_RENDER_HEIGHT);
		
		// Display size
		setInt(DISPLAY_WIDTH, DEFAUL_DISPLAY_WIDTH);
		setInt(DISPLAY_HEIGHT, DEFAUL_DISPLAY_HEIGHT);
		
		// Fullscreen
		setBool(FULLSCREEN, DEFAUL_FULLSCREEN);
		setBool(VSYNC, DEFAULT_VSYNC);
	}
	
	public static void setInt(String key, Integer value) {
		intValues.put(key, value);
	}
	
	public static void setBool(String key, Boolean value) {
		boolValues.put(key, value);
	}
	
	public static int getInt(String key) {
		return intValues.get(key);
	}
	
	public static boolean getBool(String key) {
		return boolValues.get(key);
	}
}

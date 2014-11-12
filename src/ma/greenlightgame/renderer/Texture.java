package ma.greenlightgame.renderer;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture {
	private static final String TEXTURE_FOLDER = "./res/textures/";
	
	private int id;
	private int width;
	private int height;
	
	private boolean hasAlpha;
	
	public Texture(String fileName) {
		this(fileName, GL_LINEAR);
	}
	
	public Texture(String fileName, int filter) {
		this(fileName, filter, GL_RGBA);
	}
	
	public Texture(String fileName, int filter, int format) {
		this(fileName, filter, format, GL_RGBA);
	}
	
	public Texture(String fileName, int filter, int format, int internalFormat) {
		try {
			BufferedImage image = ImageIO.read(new File(TEXTURE_FOLDER + fileName));
			int[] flippedPixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			int[] pixels = new int[flippedPixels.length];
			
			// Flip the image vertically
			for(int i = 0; i < image.getWidth(); i++)
				for(int j = 0; j < image.getHeight(); j++)
					pixels[i + j * image.getWidth()] = flippedPixels[i + (image.getHeight() - j - 1) * image.getWidth()];
			
			ByteBuffer data = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * 4);
			
			hasAlpha = image.getColorModel().hasAlpha();
			
			// Put each pixel in a Byte Buffer
			for(int y = 0; y < image.getHeight(); y++) {
				for(int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * image.getWidth() + x];
					
					byte alphaByte = hasAlpha ? (byte)((pixel >> 24) & 0xFF) : (byte)(0xFF);
					
					data.put((byte)((pixel >> 16) & 0xFF));
					data.put((byte)((pixel >> 8) & 0xFF));
					data.put((byte)((pixel) & 0xFF));
					data.put(alphaByte);
				}
			}
			
			data.flip();
			
			id = glGenTextures();
			width = image.getWidth();
			height = image.getHeight();
			
			glBindTexture(GL_TEXTURE_2D, id);
			
			if(filter != GL_NONE) {
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
			}
			
			glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, image.getWidth(), image.getHeight(), 0, format, GL_UNSIGNED_BYTE,
					data);
			
			glBindTexture(GL_TEXTURE_2D, 0);
		} catch(IOException e) {
			System.err.println("Error loading texture: The texture " + fileName + " doesn't exist.");
		}
	}
	
	public int getId() {
		return id;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean hasAlpha() {
		return hasAlpha;
	}
}
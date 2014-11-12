package ma.greenlightgame.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Utils {
	public static ByteBuffer loadIcon(BufferedImage image) throws IOException {
		final int width = image.getWidth();
		final int height = image.getHeight();
		
		byte[] imageBytes = new byte[width * height * 4];
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int pixel = image.getRGB(j, i);
				
				for(int k = 0; k < 3; k++)
					imageBytes[(i * 16 + j) * 4 + k] = (byte)(((pixel >> (2 - k) * 8)) & 255);
				
				imageBytes[(i * 16 + j) * 4 + 3] = (byte)(((pixel >> (3) * 8)) & 255);
			}
		}
		
		return ByteBuffer.wrap(imageBytes);
	}
}

package ma.greenlightgame.common.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
	
	public static float angleTo(int x1, int y1, int x2, int y2) {
		float angle = (float)Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
		
		if(angle < 0)
			angle += 360;
		
		return angle;
	}
	
	public static String bytesToString(byte[] bytes) {
		String msg = null;
		
		try {
			msg = new String(bytes, "UTF-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return msg;
	}
}
package ma.greenlightgame.physics;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Rectangle;

public class Collider {
	private int x;
	private int y;
	
	public Collider(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Rectangle getBounds(int width, int height){
		return new Rectangle((int)x, (int)y, width, height);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public static void renderBounds(Rectangle bounds) {
		final int x = bounds.x;
		final int y = bounds.y;
		final int w = bounds.width;
		final int h = bounds.height;
		
		glPushMatrix(); {
			glColor3f(0, 1, 0);
			
			glBegin(GL_LINES); {
				glVertex2f(x, 		y);
				glVertex2f(x + w, 	y);
			} glEnd();
			
			glBegin(GL_LINES); {
				glVertex2f(x, 		y);
				glVertex2f(x, 		y + h);
			} glEnd();
			
			glBegin(GL_LINES); {
				glVertex2f(x + w, 	y);
				glVertex2f(x + w, 	y + h);
			} glEnd();
			
			glBegin(GL_LINES); {
				glVertex2f(x,		y + h);
				glVertex2f(x + w,	y + h);
			} glEnd();
			
			glColor3f(1, 1, 1);
		} glPopMatrix();
	}
}

package ma.greenlightgame.physics;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Color;
import java.awt.Rectangle;

import ma.greenlightgame.entity.Entity;

public class Physics {
	public static boolean intersecs(Entity entityA, Entity entityB) {
		final Rectangle boundsA = entityA.getBounds();
		final Rectangle boundsB = entityB.getBounds();
		
		if(boundsA.intersects(boundsB))
			return true;
		
		return false;
	}
	
	public static void drawBounds(Rectangle bounds, Color color) {
		final int x = bounds.x;
		final int y = bounds.y;
		final int w = bounds.width;
		final int h = bounds.height;
		
		glPushMatrix(); {
			glColor3f(color.getRed(), color.getGreen(), color.getBlue());
			
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

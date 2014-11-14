package ma.greenlightgame.client.utils;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Color;
import java.awt.Rectangle;

public class DebugDraw {
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
				glVertex2f(x + w, 	y + h);
			} glEnd();
		} glPopMatrix();
	}
	
	public static void drawPivotPoint(int x, int y) {
		glPushMatrix(); {
			glLoadIdentity();
			
			glColor3f(0, 0, 1);
			
			glPointSize(3);
			
			glBegin(GL_POINTS); {
				glVertex2f(x, y);
			} glEnd();
		} glPopMatrix();
	}
	
	public static void drawLine(int x1, int y1, int x2, int y2) {
		glPushMatrix(); {
			glLoadIdentity();
			
			glColor3f(1, 1, 0);
			
			glBegin(GL_LINES); {
				glVertex2f(x1, y1);
				glVertex2f(x2, y2);
			} glEnd();
		} glPopMatrix();
	}
}

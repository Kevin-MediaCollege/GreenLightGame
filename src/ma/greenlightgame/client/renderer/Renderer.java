package ma.greenlightgame.client.renderer;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class Renderer {
	public static void drawTexture(int textureId, int x, int y, int width, int height) {
		drawTexture(textureId, x, y, width, height, 0);
	}
	
	public static void drawTexture(int textureId, int x, int y, int width, int height,
			float rotation) {
		drawTexture(textureId, x, y, width, height, rotation, true);
	}
	
	public static void drawTexture(int textureId, int x, int y, int width, int height,
			float rotation, boolean center) {
		drawTexture(textureId, x, y, width, height, rotation, center, 1);
	}
	
	public static void drawTexture(int textureId, int x, int y, int width, int height,
			float rotation, boolean center, float alpha) {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		
		glBindTexture(GL_TEXTURE_2D, textureId);
		
		glPushMatrix();
		{
			glLoadIdentity();
			
			glColor4f(alpha, alpha, alpha, alpha);
			
			glTranslatef(x, y, 0);
			glRotatef(rotation, 0, 0, 1);
			glScalef(width, height, 1);
			
			glBegin(GL_QUADS);
			{
				if(center) {
					glTexCoord2f(0, 1);
					glVertex2f(-0.5f, 0.5f);
					glTexCoord2f(1, 1);
					glVertex2f(0.5f, 0.5f);
					glTexCoord2f(1, 0);
					glVertex2f(0.5f, -0.5f);
					glTexCoord2f(0, 0);
					glVertex2f(-0.5f, -0.5f);
				} else {
					glTexCoord2f(0, 1);
					glVertex2f(0, 1);
					glTexCoord2f(1, 1);
					glVertex2f(1, 1);
					glTexCoord2f(1, 0);
					glVertex2f(1, 0);
					glTexCoord2f(0, 0);
					glVertex2f(0, 0);
				}
			}
			glEnd();
		}
		glPopMatrix();
		
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}
	
	public static void drawSprite(int textureId, float u1, float v1, float u2, float v2, float u3,
			float v3, float u4, float v4, int x, int y, int width, int height) {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		glBindTexture(GL_TEXTURE_2D, textureId);
		
		glPushMatrix(); {
			glLoadIdentity();
			
			glColor3f(1, 1, 1);
			
			glTranslatef(x, y, 0);
			glScalef(width, height, 1);
			
			glBegin(GL_QUADS); {
				glTexCoord2f(u1, v1); glVertex2f(-0.5f,  0.5f);
				glTexCoord2f(u2, v2); glVertex2f( 0.5f,  0.5f);
				glTexCoord2f(u3, v3); glVertex2f( 0.5f, -0.5f);
				glTexCoord2f(u4, v4); glVertex2f(-0.5f, -0.5f);
			} glEnd();
		} glPopMatrix();
		
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}
}

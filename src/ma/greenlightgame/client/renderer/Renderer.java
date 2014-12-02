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
	public void drawTexture(int textureId, int x, int y, int width, int height) {
		drawTexture(textureId, x, y, width, height, 0);
	}
	
	public void drawTexture(int textureId, int x, int y, int width, int height, float angle) {
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
		
		glBindTexture(GL_TEXTURE_2D, textureId);
		
		glPushMatrix(); {
			glLoadIdentity();
			
			glColor3f(1, 1, 1);
			
			glTranslatef(x, y, 0);
			glRotatef(angle, 0, 0, 1);
			glScalef(width, height, 1);
			
			glBegin(GL_QUADS); {
				glTexCoord2f(0, 1); glVertex2f(-0.5f,  0.5f);
				glTexCoord2f(1, 1); glVertex2f( 0.5f,  0.5f);
				glTexCoord2f(1, 0); glVertex2f( 0.5f, -0.5f);
				glTexCoord2f(0, 0); glVertex2f(-0.5f, -0.5f);
			} glEnd();
		} glPopMatrix();
		
		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
	}
	
	public void drawSprite(Texture texture, float spriteX, float spriteY, float spriteWidth, float spriteHeight, int x, int y, int width, int height) {
		drawSprite(texture, spriteX, spriteY, spriteWidth, spriteHeight, x, y, width, height, 0);
	}
	
	public void drawSprite(Texture texture, float spriteX, float spriteY, float spriteWidth, float spriteHeight, int x, int y, int width, int height, float angle) {
		final float sX = (float)spriteX / (float)texture.getWidth();
		final float sY = (float)spriteY / (float)texture.getHeight();
		
		final float sW = (float)spriteWidth / (float)texture.getWidth();
		final float sH = (float)spriteHeight / (float)texture.getHeight();
		
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, texture.getId());
		
		glPushMatrix(); {
			glLoadIdentity();
			
			glColor3f(1, 1, 1);
			
			glTranslatef(x, y, 0);
			glRotatef(angle, 0, 0, 1);
			glScalef(width, height, 1);
			
			glBegin(GL_QUADS); {
				glTexCoord2f(sX, 	  sY - sH);	glVertex2f(-0.5f,  0.5f);
				glTexCoord2f(sX + sW, sY - sH);	glVertex2f( 0.5f,  0.5f);
				glTexCoord2f(sX + sW, sY);		glVertex2f( 0.5f, -0.5f);
				glTexCoord2f(sX,      sY);		glVertex2f(-0.5f, -0.5f);
			} glEnd();
		} glPopMatrix();
		
		glDisable(GL_TEXTURE_2D);
	}
}

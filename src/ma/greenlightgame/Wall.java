package ma.greenlightgame;

import java.awt.Rectangle;

import ma.greenlightgame.entityclasses.EntityC;
import ma.greenlightgame.renderer.Texture;
import ma.greenlightgame.renderer.Renderer;

import org.lwjgl.opengl.GL11;

public class Wall implements EntityC{

	private Texture wall;
	double x;
	double y;
	
	public String[] wallArr = {
		"HWall.jpg",
		"VWall.jpg",
	};
	
	
	public Wall(double x, double y, Game game){
		this.x = x;
		this.y = y;
		
		wall = new Texture("Tiles/" + wallArr[0] ,GL11.GL_NEAREST);
	}

	public void render(Renderer r){
		r.drawTexture(wall, (int)x, (int)y, 100, 50);
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 100, 50);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}


	
}

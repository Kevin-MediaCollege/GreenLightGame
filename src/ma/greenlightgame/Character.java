package ma.greenlightgame;

import org.lwjgl.opengl.GL11;

import ma.greenlightgame.renderer.Renderer;
import ma.greenlightgame.renderer.Texture;



public class Character {
	private Texture T;
	public Character(){
		T = new Texture("test.png",GL11.GL_NEAREST);
	}
	
	public void render(Renderer r){
	r.drawTexture(T, 100, 200, 100, 100);	
		
		
	}
}

package ma.greenlightgame;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import ma.greenlightgame.input.Input;
import ma.greenlightgame.renderer.Renderer;
import ma.greenlightgame.renderer.Texture;

public class Game {
	private Character Char;
	public Game() {
		Char = new Character();
	}
	
	public void update(Input input){
		if(input.getKey(KeyCode.D)){
			Char.x += 1;
		}else if(input.getKey(KeyCode.A)){
			Char.x -= 1;
		}
		if(input.getKey(KeyCode.W)){
			Char.y += 1;
		}else if(input.getKey(KeyCode.S)){
			Char.y -= 1;
		}
		
	}
	
	
	public void render(final Renderer renderer) {
		Char.render(renderer);
		//renderer.drawTexture(t, 512, 128, 256, 256);
		//renderer.drawSprite(t, 0, 0, 51, 51, 100, 100, 256, 256);
	}
}

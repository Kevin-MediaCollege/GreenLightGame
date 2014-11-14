package ma.greenlightgame;

import ma.greenlightgame.input.Input;
import ma.greenlightgame.input.Input.KeyCode;
import ma.greenlightgame.renderer.Renderer;
import ma.greenlightgame.renderer.Texture;

public class Game {
	private Character Char;
	public Game() {
		Char = new Character();
	}
	
	public void update(Input input){
		if(input.getKey(KeyCode.D)){
			Char.x += 5;
		}else if(input.getKey(KeyCode.A)){
			Char.x -= 5;
		}
		if(input.getKey(KeyCode.W)){
			Char.y += 5;
		}else if(input.getKey(KeyCode.S)){
			Char.y -= 5;
		}	
	}
	
	
	public void render(final Renderer renderer) {
		Char.render(renderer);
		//renderer.drawTexture(t, 512, 128, 256, 256);
		//renderer.drawSprite(t, 0, 0, 51, 51, 100, 100, 256, 256);
	}
}

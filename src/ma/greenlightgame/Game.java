package ma.greenlightgame;

import ma.greenlightgame.input.Input;
import ma.greenlightgame.input.Input.KeyCode;
import ma.greenlightgame.renderer.Renderer;

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
	
	
	public void render(Renderer renderer) {
		Char.render(renderer);
	}
}

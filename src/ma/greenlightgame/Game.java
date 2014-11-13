package ma.greenlightgame;

import java.util.LinkedList;

import ma.greenlightgame.entityclasses.EntityA;
import ma.greenlightgame.entityclasses.EntityB;
import ma.greenlightgame.entityclasses.EntityC;
import ma.greenlightgame.input.Input;
import ma.greenlightgame.input.Input.KeyCode;
import ma.greenlightgame.renderer.Renderer;

public class Game {
	private Character Char;
	private Controller cont;

	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;
	public LinkedList<EntityC> ec;
	
	public Game() {
		Char = new Character(this);
		cont = new Controller(this);
		
		ea = cont.getEntityA();
		eb = cont.getEntityB();
		ec = cont.getEntityC();
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
		Char.Update();
	}
	
	
	public void render(Renderer renderer) {
		Char.render(renderer);
		cont.render(renderer);
	}
}

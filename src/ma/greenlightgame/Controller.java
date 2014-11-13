package ma.greenlightgame;

import java.util.LinkedList;

import ma.greenlightgame.entityclasses.EntityA;
import ma.greenlightgame.entityclasses.EntityB;
import ma.greenlightgame.entityclasses.EntityC;

import ma.greenlightgame.renderer.Renderer;

public class Controller {
	private LinkedList<EntityA> ea = new LinkedList<EntityA>();
	private LinkedList<EntityB> eb = new LinkedList<EntityB>();
	private LinkedList<EntityC> ec = new LinkedList<EntityC>();
	
	EntityA enta;
	EntityB entb;
	EntityC entc;
	
	Game game;
	
	public Controller(Game game){
		this.game = game;
		addEntity(new Wall(200,200, game));
	}

	public void Update(){
		
	}
	
	public void render(Renderer r){
		for(int i = 0; i < ec.size(); i ++){
			entc = ec.get(i);
			
			entc.render(r);
		}
		
	}
	
	//A CLASS
	public void addEntity(EntityA block){
		ea.add(block);
	}
	public void removeEntity(EntityA block){
		ea.remove(block);
	}
	
	//B CLASS
	public void addEntity(EntityB block){
		eb.add(block);
	}
	public void removeEntity(EntityB block){
		eb.remove(block);
	}
	
	//C CLASS
	public void addEntity(EntityC block){
		ec.add(block);
	}
	public void removeEntity(EntityC block){
		ec.remove(block);
	}
	
	//LINKEDLISTS
	public LinkedList<EntityA> getEntityA(){
		return ea;
	}
	public LinkedList<EntityB> getEntityB(){
		return eb;
	}
	public LinkedList<EntityC> getEntityC(){
		return ec;
	}
	
}

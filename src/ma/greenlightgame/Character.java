package ma.greenlightgame;

import java.awt.Rectangle;

import ma.greenlightgame.entityclasses.EntityA;
import ma.greenlightgame.physics.Physics;
import ma.greenlightgame.renderer.Renderer;
import ma.greenlightgame.renderer.Texture;

import org.lwjgl.opengl.GL11;



public class Character implements EntityA{
	
	private Game game;
	private Controller cont;
	
	private float gravityVel;
	private boolean isJumping = false;
	
	private Texture head;
	private Texture body;
	private Texture legs;
	
	public int x = 200,y = 400;
	
	public String[] headArr = 
			{
			"H1.jpg",
			"H2.jpg",
            "H3.jpg",
			};
	public String[] bodyArr = 
			{
			"H1.jpg",
			"H2.jpg",
	        "H3.jpg",
			};
	public String[] legsArr = 
			{
			"H1.jpg",
			"H2.jpg",
	        "H3.jpg",
			};
	public Character(Game game){
		this.game = game;
		this.gravityVel = -9;
		//The Chosen Head will show you the body of the character
		head = new Texture("Character/head/" 	+ headArr[0]	,GL11.GL_NEAREST);
		body = new Texture("Character/body/"	+ bodyArr[1]	,GL11.GL_NEAREST);
		legs = new Texture("Character/legs/"	+ legsArr[2]	,GL11.GL_NEAREST);
	}
	
	public void Update(){
		
		y += gravityVel;
		
		if(gravityVel > -9){
			gravityVel -= 0.6;
		}
		
		if(Physics.Collision(this, game.ec)){
			gravityVel = 0; 
			isJumping = false;
		}
	}
	
	public void render(Renderer r){
		//This will render all three parts of the character the head,body and the legs
		r.drawTexture(head, (int)x, (int)y + 50 , 50, 50);
		r.drawTexture(body, (int)x, (int)y		, 50, 50);	
		r.drawTexture(legs, (int)x, (int)y - 50 , 50, 50);	
		
		
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y - 50, 50, 150);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	public void setGravityVel(float newVelocity){
		gravityVel += newVelocity;
		isJumping = true;
		return;
	}
	public boolean getIsJumping(){
		return isJumping;
	}
}

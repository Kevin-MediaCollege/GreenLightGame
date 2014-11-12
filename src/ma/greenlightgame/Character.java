package ma.greenlightgame;

import org.lwjgl.opengl.GL11;


import ma.greenlightgame.input.Input;
import ma.greenlightgame.renderer.Renderer;
import ma.greenlightgame.renderer.Texture;



public class Character {
	private Texture head;
	private Texture body;
	private Texture legs;
	public int x = 100,y = 100;
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
	public Character(){
		//The Chosen Head will show you the body of the character
		head = new Texture("Character/head/" 	+ headArr[0]	,GL11.GL_NEAREST);
		body = new Texture("Character/body/"	+ bodyArr[1]	,GL11.GL_NEAREST);
		legs = new Texture("Character/legs/"	+ legsArr[2]	,GL11.GL_NEAREST);
	}
	
	public void render(Renderer r){
		//This will render all three parts of the character the head,body and the legs
		r.drawTexture(head, x, y, 50, 50);
		r.drawTexture(body, x, y - 50, 50, 50);	
		r.drawTexture(legs, x, y - 100, 50, 50);	
		
		
	}
}

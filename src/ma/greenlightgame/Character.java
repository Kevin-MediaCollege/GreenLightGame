package ma.greenlightgame;

import org.lwjgl.opengl.GL11;

import java.io.File;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;



public class Character {
	private Texture head;
	private Texture body;
	private Texture legs;
	private File headFile = new File("./res/textures/Character/head");
	public int x = 100,y = 100;
	public String[] headArr;
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
		//headArr = push(headArr,headFile.listFiles());
		System.out.println("List of shit = " +  headArr);
		//The Chosen Head will show you the body of the character
		head = new Texture("Character/head/" 	+ bodyArr[1]	,GL11.GL_NEAREST);
		body = new Texture("Character/body/"	+ bodyArr[1]	,GL11.GL_NEAREST);
		legs = new Texture("Character/legs/"	+ legsArr[2]	,GL11.GL_NEAREST);
	}
	


	public void render(Renderer r){
		//This will render all three parts of the character the head,body and the legs
		//r.drawTexture(head, x, y, 50, 50);
		r.drawTexture(body, x, y - 50, 50, 50);	
		r.drawTexture(legs, x, y - 100, 50, 50);	
		
		
	}
}

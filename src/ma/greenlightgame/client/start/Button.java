package ma.greenlightgame.client.start;

import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;

public class Button {
	public String purpose;
	public int x;
	public int y;
	public Texture img;
	
	public Button(String Purpose,int X,int Y,Texture Img){
		this.purpose = Purpose;
		this.x = X;
		this.y = Y;
		this.img = Img;
	}
	
	
	public int GetX(){
		return x;
	}
	
	public int GetY(){
		return y;
	}
	public String GetPurpose(){
		return purpose;
	}
	
	

}

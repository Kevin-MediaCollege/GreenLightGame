package ma.greenlightgame.client.start;

import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;

public class UserInterface {
	private int mouseX;
	private int mouseY;
	public 	static Texture images; 
	private static String active;
	private static Button[] btn;
	public static boolean  pause = false,start = true,host = false, join = false,play = false;
	
	public void UserInterFace(){
	}
	
	public void update(float delta){
		mouseX = Input.getMouseX();
		mouseY = Input.getMouseY();
		if(Input.getMouse(0)){
			for(int j = 0;j < btn.length;j++){
				buttonPress(mouseX,mouseY,btn[0]);
			}
		}
		
	}
	public void buttonPress(int MouseX,int MouseY,Button btn){
		if(MouseX > btn.x){
			if(MouseX < btn.x + btn.img.getWidth()){
				if(btn.purpose == "Host"){
					join = false;
					host = true;
				}else if(btn.purpose == "Join"){
					host = false;
					join = true;
				}else if(btn.purpose == "Play"){
					host = false;
					play = true;
				}
				
			}
		}
	}
	
	public static void render(Renderer renderer) {
		for(int i = 0 ; i < btn.length;i++){
			renderer.drawTexture(btn[i].img, btn[i].x, btn[i].y, btn[i].img.getWidth(),btn[i].img.getHeight());	
		}
	}
	public static void load() {
		images = new Texture("character/head/H1.jpg");
		btn = new Button[]{
				new Button("Host",200,300,images),
				new Button("Join",300,300,images),
				new Button("Play",400,300,images),
		};
	}

	
	
}

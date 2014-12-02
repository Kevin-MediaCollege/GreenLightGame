package ma.greenlightgame.client.screen;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.renderer.Texture;
import ma.greenlightgame.client.screen.Button.ButtonActionHandler;

public class ScreenMainMenu implements Screen {
	private static final int BTN_HOST = new Texture("Character/head/H1.jpg").getId();
	private static final int BTN_HOST_HOVER = new Texture("Character/body/H2.jpg").getId();
	private static final int BTN_HOVER_PRESS = new Texture("Character/legs/H3.png").getId();
	
	private static final int BTN_JOIN = new Texture("Character/head/H1.jpg").getId();
	private static final int BTN_JOIN_HOVER = new Texture("Character/body/H2.jpg").getId();
	private static final int BTN_JOIN_PRESS = new Texture("Character/legs/H3.png").getId();
	
	private Button[] buttons;
	
	public ScreenMainMenu() {
		buttons = new Button[] {
			// Host
			new Button(100, 200, 100, 100, new ButtonActionHandler() {
				@Override
				public void onCreate(Button button) {
					button.setTexture(BTN_HOST);
				}
				
				@Override
				public void onHover(Button button, boolean hovering) {
					if(hovering) {
						button.setTexture(BTN_HOST_HOVER);
					} else {
						button.setTexture(BTN_HOST);
					}
				}
				
				@Override
				public void onMouse(Button button, boolean down) {
					if(down) {
						button.setTexture(BTN_HOVER_PRESS);
					} else {
						button.setTexture(BTN_HOST_HOVER);
					}
				}
				
				@Override
				public void onClick(Button button) {
					System.out.println("Pressed button!");
					
					Client.setActiveScreen(new ScreenHostMenu());
				}
			}),
			
			// Join
			new Button(250, 200, 100, 100, new ButtonActionHandler() {
				@Override
				public void onCreate(Button button) {
					button.setTexture(BTN_JOIN);
				}
				
				@Override
				public void onHover(Button button, boolean hovering) {
					if(hovering) {
						button.setTexture(BTN_JOIN_HOVER);
					} else {
						button.setTexture(BTN_JOIN);
					}
				}
				
				@Override
				public void onMouse(Button button, boolean down) {
					if(down) {
						button.setTexture(BTN_JOIN_PRESS);
					} else {
						button.setTexture(BTN_JOIN_HOVER);
					}
				}
				
				@Override
				public void onClick(Button button) {
					System.out.println("Pressed button!");
					
					button.setActive(false);
				}
			})
		};
	}
	
	@Override
	public void update() {
		for(Button button : buttons)
			button.update();
	}
	
	@Override
	public void render(Renderer renderer) {
		for(Button button : buttons)
			button.render(renderer);
	}
	
	@Override
	public void drawDebug() {
		for(Button button : buttons)
			button.drawDebug();
	}
}

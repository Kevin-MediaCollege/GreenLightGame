package ma.greenlightgame.common.screen;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.renderer.Texture;
import ma.greenlightgame.common.Game;
import ma.greenlightgame.common.config.Config;
import ma.greenlightgame.common.screen.components.Button;
import ma.greenlightgame.common.screen.components.Button.ButtonActionHandler;

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
					
					Game.startServer();
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
					
					try {
						Client.connect(InetAddress.getByName(Config.getString(Config.LAST_SERVER_IP)), Config.getInt(Config.LAST_SERVER_PORT));
					} catch(UnknownHostException e) {
						e.printStackTrace();
					}
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
	public void render() {
		for(Button button : buttons)
			button.render();
	}
	
	@Override
	public void drawDebug() {
		for(Button button : buttons)
			button.drawDebug();
	}
}

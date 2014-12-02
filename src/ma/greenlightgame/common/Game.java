package ma.greenlightgame.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.start.UserInterface;
import ma.greenlightgame.common.config.Config;
import ma.greenlightgame.server.Server;

public class Game {
	private Client client;
	private Server server;
	
	public Game() {
		client = new Client();
	}
	
	public void update(Input input, float delta) {
		if(input.isKeyDown(KeyCode.NUM_0))
			Config.DRAW_DEBUG = !Config.DRAW_DEBUG;
		
		if(server == null) {
			if(input.isKeyDown(KeyCode.G) || UserInterface.host) {
				try {
					server = new Server();
					Client.connect(InetAddress.getLocalHost(), Config.getInt(Config.LAST_SERVER_PORT));
				} catch(UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(server != null)
			server.update(input, delta);
		
		client.update(input, delta);
	}
	
	public void render(Renderer renderer) {
		client.render(renderer);
	}
	
	public void destroy() {
		client.destroy();
		
		if(server != null)
			server.destroy();
	}
}

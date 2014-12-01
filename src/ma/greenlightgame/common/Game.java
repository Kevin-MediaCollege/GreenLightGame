package ma.greenlightgame.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.common.config.Config;
import ma.greenlightgame.server.Server;

public class Game {
	private Client client;
	private Server server;
	
	public Game() {
		client = new Client();
	}
	
	public void update(float delta) {
		if(Input.isKeyDown(KeyCode.NUM_0))
			Config.DRAW_DEBUG = !Config.DRAW_DEBUG;
		
		if(server == null) {
			if(Input.isKeyDown(KeyCode.G)) {
				try {
					server = new Server();
					Client.connect(InetAddress.getLocalHost(), Config.getInt(Config.LAST_SERVER_PORT));
				} catch(UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(server != null)
			server.update(delta);
		
		client.update(delta);
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

package ma.greenlightgame.common;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.common.config.Config;
import ma.greenlightgame.server.Server;

public class Game {
	private static Server server;
	
	private Client client;
	
	public Game() {
		client = new Client();
	}
	
	public void update(float delta) {
		if(Input.isKeyDown(KeyCode.NUM_0))
			Config.DRAW_DEBUG = !Config.DRAW_DEBUG;
		
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
	
	public static void startServer() {
		server = new Server();
	}
}

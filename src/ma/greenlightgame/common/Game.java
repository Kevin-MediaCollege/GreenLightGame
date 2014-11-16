package ma.greenlightgame.common;

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
	
	public void update(Input input, float delta) {
		if(input.isKeyDown(KeyCode.F11))
			Config.DRAW_DEBUG = !Config.DRAW_DEBUG;
		
		if(server == null) {
			if(input.isKeyDown(KeyCode.G)) {
				server = new Server();
				client.connectToLocal();
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

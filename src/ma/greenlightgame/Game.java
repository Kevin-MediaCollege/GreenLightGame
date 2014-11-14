package ma.greenlightgame;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ma.greenlightgame.config.Config;
import ma.greenlightgame.entity.EntityManager;
import ma.greenlightgame.entity.EntityPlayer;
import ma.greenlightgame.entity.wall.EntityWall;
import ma.greenlightgame.input.Input;
import ma.greenlightgame.input.Input.KeyCode;
import ma.greenlightgame.network.UDPClient;
import ma.greenlightgame.network.UDPServer;
import ma.greenlightgame.renderer.Renderer;

public class Game {
	private final EntityManager entityManager;
	
	private UDPServer server;
	private UDPClient client;
	
	public Game() {
		EntityPlayer.load();
		EntityWall.load();
		
		entityManager = new EntityManager(this);
		
		entityManager.addWall(new EntityWall(200, 200, 0));
	}
	
	public void update(Input input, float delta) {
		if(input.isKeyDown(KeyCode.GRAVE))
			Config.DRAW_DEBUG = !Config.DRAW_DEBUG;
		
		if(server == null)
			if(input.isKeyDown(KeyCode.G))
				server = new UDPServer(1337, new UDPServerHandler());
		
		if(input.isKeyDown(KeyCode.H)) {
			if(client == null) {
				try {
					client = new UDPClient(InetAddress.getByName("localhost"), 1337, new UDPClientHandler());
				} catch(UnknownHostException e) {
					e.printStackTrace();
				}
			} else {
				try {
					client.send("Wat the kanker");
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		entityManager.update(input, delta);
	}
	
	public void render(Renderer renderer) {
		entityManager.render(renderer);
	}
	
	public void destroy() {
		if(server != null)
			server.close();
		
		if(client != null)
			client.close();
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}

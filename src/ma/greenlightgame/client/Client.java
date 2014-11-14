package ma.greenlightgame.client;

import ma.greenlightgame.client.entity.EntityManager;
import ma.greenlightgame.client.entity.EntityPlayer;
import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.renderer.Renderer;

public class Client {
	private final EntityManager entityManager;
	
	public Client() {
		EntityPlayer.load();
		EntityWall.load();
		
		entityManager = new EntityManager(this);
		
		entityManager.addWall(new EntityWall(200, 200, 0));
	}
	
	public void update(Input input, float delta) {
		entityManager.update(input, delta);
	}
	
	public void render(Renderer renderer) {
		entityManager.render(renderer);
	}
	
	public void destroy() {
		
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}

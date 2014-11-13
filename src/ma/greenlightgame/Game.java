package ma.greenlightgame;

import ma.greenlightgame.config.Config;
import ma.greenlightgame.entity.EntityManager;
import ma.greenlightgame.entity.EntityPlayer;
import ma.greenlightgame.entity.wall.EntityWall;
import ma.greenlightgame.input.Input;
import ma.greenlightgame.input.Input.KeyCode;
import ma.greenlightgame.renderer.Renderer;

public class Game {
	private final EntityManager entityManager;
	
	public Game() {
		EntityPlayer.load();
		EntityWall.load();
		
		entityManager = new EntityManager(this);
		
		entityManager.addWall(new EntityWall(200, 200, 0));
	}
	
	public void update(Input input, float delta) {
		if(input.isKeyDown(KeyCode.GRAVE))
			Config.DRAW_DEBUG = !Config.DRAW_DEBUG;
		
		entityManager.update(input, delta);
	}
	
	public void render(Renderer renderer) {
		entityManager.render(renderer);
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
}

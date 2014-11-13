package ma.greenlightgame.entity;

import java.util.LinkedList;
import java.util.List;

import ma.greenlightgame.Game;
import ma.greenlightgame.config.Config;
import ma.greenlightgame.entity.enemy.EntityEnemy;
import ma.greenlightgame.entity.wall.EntityWall;
import ma.greenlightgame.input.Input;
import ma.greenlightgame.renderer.Renderer;

public class EntityManager {
	private List<EntityEnemy> enemies;
	private List<EntityWall> walls;
	
	private EntityPlayer player;
	
	private final Game game;
	
	public EntityManager(Game game) {
		enemies = new LinkedList<EntityEnemy>();
		walls = new LinkedList<EntityWall>();
		
		player = new EntityPlayer();
		
		this.game = game;
	}
	
	public void update(Input input, float delta) {
		player.update(input, delta);
		player.checkCollision(game);
		
		for(EntityEnemy enemy : enemies)
			enemy.update(input, delta);
		
		for(EntityWall wall : walls)
			wall.update(input, delta);
	}
	
	public void render(Renderer renderer) {
		player.render(renderer);
		
		if(Config.RENDER_BOUNDS)
			player.drawBounds();
		
		for(EntityEnemy enemy : enemies) {
			enemy.render(renderer);
			
			if(Config.RENDER_BOUNDS)
				enemy.drawBounds();
		}
		
		for(EntityWall wall : walls) {
			wall.render(renderer);
			
			if(Config.RENDER_BOUNDS)
				wall.drawBounds();
		}
	}
	
	public void addEnemy(EntityEnemy enemy) {
		enemies.add(enemy);
	}
	
	public void removeEnemy(EntityEnemy enemy) {
		enemies.remove(enemy);
	}
	
	public void removeEnemyAt(int index) {
		enemies.remove(index);
	}
	
	public void addWall(EntityWall wall) {
		walls.add(wall);
	}
	
	public void removeWall(EntityWall wall) {
		walls.remove(wall);
	}
	
	public void removeWallAt(int index) {
		walls.remove(index);
	}
	
	public EntityEnemy[] getEnemies() {
		return enemies.toArray(new EntityEnemy[enemies.size()]);
	}
	
	public EntityWall[] getWalls() {
		return walls.toArray(new EntityWall[walls.size()]);
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public EntityEnemy getEnemy(int index) {
		return enemies.get(index);
	}
	
	public EntityWall getWall(int index) {
		return walls.get(index);
	}
}

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
	private List<EntityPlayer> players;
	private List<EntityEnemy> enemies;
	private List<EntityWall> walls;
	
	private final Game game;
	
	public EntityManager(Game game) {
		players = new LinkedList<EntityPlayer>();
		enemies = new LinkedList<EntityEnemy>();
		walls = new LinkedList<EntityWall>();
		
		addPlayer(new EntityPlayer(true));
		addPlayer(new EntityPlayer(false));
		
		this.game = game;
	}
	
	public void update(Input input, float delta) {
		for(EntityPlayer player : players) {
			player.update(input, delta);
			
			if(player.isOwn())
				player.checkCollision(game);
		}
		
		for(EntityEnemy enemy : enemies)
			enemy.update(input, delta);
		
		for(EntityWall wall : walls)
			wall.update(input, delta);
	}
	
	public void render(Renderer renderer) {
		for(EntityPlayer player : players) {
			player.render(renderer);
			
			if(Config.DRAW_DEBUG)
				player.drawDebug();
		}
		
		for(EntityEnemy enemy : enemies) {
			enemy.render(renderer);
			
			if(Config.DRAW_DEBUG)
				enemy.drawDebug();
		}
		
		for(EntityWall wall : walls) {
			wall.render(renderer);
			
			if(Config.DRAW_DEBUG)
				wall.drawDebug();
		}
	}
	
	public void addPlayer(EntityPlayer player) {
		players.add(player);
	}
	
	public void removePlayer(EntityPlayer player) {
		players.remove(player);
	}
	
	public void removePlayer(int index) {
		players.remove(index);
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
	
	public EntityPlayer[] getPlayers() {
		return players.toArray(new EntityPlayer[players.size()]);
	}
	
	public EntityEnemy[] getEnemies() {
		return enemies.toArray(new EntityEnemy[enemies.size()]);
	}
	
	public EntityWall[] getWalls() {
		return walls.toArray(new EntityWall[walls.size()]);
	}
	
	public EntityPlayer getOwnPlayer() {
		return getPlayer(0);
	}
	
	public EntityPlayer getPlayer(int index) {
		return players.get(index);
	}
	
	public EntityEnemy getEnemy(int index) {
		return enemies.get(index);
	}
	
	public EntityWall getWall(int index) {
		return walls.get(index);
	}
}

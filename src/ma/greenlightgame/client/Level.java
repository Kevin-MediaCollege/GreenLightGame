package ma.greenlightgame.client;

import java.util.ArrayList;
import java.util.List;

import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.renderer.Background;
import ma.greenlightgame.client.renderer.SpriteSheet;
import ma.greenlightgame.client.renderer.Texture;

public class Level {
	private List<EntityWall> walls;
	
	private static SpriteSheet spriteSheet;
	
	private static Background background;
	
	public Level(int levelId) {
		walls = new ArrayList<EntityWall>();
		
		int width = 300;
		int height = 75;
		
		walls.add(new EntityWall(200, 200, width, height, spriteSheet));
		walls.add(new EntityWall(525, 200, width, height, spriteSheet));
		walls.add(new EntityWall(1395, 200, width, height, spriteSheet));
		walls.add(new EntityWall(1720, 200, width, height, spriteSheet));
		
		walls.add(new EntityWall(815, 475, width, height, spriteSheet));
		walls.add(new EntityWall(1135, 475, width, height, spriteSheet));
		
		walls.add(new EntityWall(200, 750, width, height, spriteSheet));
		walls.add(new EntityWall(525, 750, width, height, spriteSheet));
		walls.add(new EntityWall(1395, 750, width, height, spriteSheet));
		walls.add(new EntityWall(1720, 750, width, height, spriteSheet));
	}
	
	public void update(float delta) {
		for(EntityWall wall : walls) {
			wall.update(delta);
		}
	}
	
	public void render() {
		background.render();
		
		for(EntityWall wall : walls) {
			wall.render();
		}
	}
	
	public void drawDebug() {
		for(EntityWall wall : walls) {
			wall.drawDebug();
		}
	}
	
	public EntityWall[] getWalls() {
		return walls.toArray(new EntityWall[walls.size()]);
	}
	
	public EntityWall getWallAt(int x, int y) {
		for(EntityWall wall : walls)
			if(wall.getX() == x && wall.getY() == y)
				return wall;
		
		return null;
	}
	
	public static void load() {
		background = new Background(new Texture("backgrounds/background_level1.jpg"));
		
		spriteSheet = new SpriteSheet(new Texture("tiles/platform_animated.png"));
	}
}

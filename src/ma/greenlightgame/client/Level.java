package ma.greenlightgame.client;

import java.util.ArrayList;
import java.util.List;

import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.renderer.Renderer;

/** @author Kevin Krol
 * @since Nov 14, 2014 */
public class Level {
	private List<EntityWall> walls;
	
	public Level(int levelId) {
		walls = new ArrayList<EntityWall>();
		
		walls.add(new EntityWall(200, 200, 0));
		walls.add(new EntityWall(400, 200, 0));
		walls.add(new EntityWall(600, 200, 0));
		walls.add(new EntityWall(800, 200, 0));
		//walls.add(new EntityWall(400, 600, 0));
		walls.add(new EntityWall(1200, 200, 0));
		walls.add(new EntityWall(1400, 200, 0));
		walls.add(new EntityWall(1600, 200, 0));
		walls.add(new EntityWall(1800, 200, 0));
	}
	
	public void update(float delta) {
		for(EntityWall wall : walls)
			wall.update(delta);
	}
	
	public void render(Renderer renderer) {
		for(EntityWall wall : walls)
			wall.render(renderer);
	}
	
	public void drawDebug() {
		for(EntityWall wall : walls)
			wall.drawDebug();
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
}

package ma.greenlightgame.entityclasses;

import ma.greenlightgame.renderer.Renderer;
import java.awt.Rectangle;

public interface EntityC {///////////////////////this entity is for WALLS
	
	public Rectangle getBounds();
	public void render(Renderer r);

	public double getX();
	public double getY();
}

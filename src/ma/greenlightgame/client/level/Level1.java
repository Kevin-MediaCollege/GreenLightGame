package ma.greenlightgame.client.level;

import ma.greenlightgame.client.entity.platform.EntityPlatformStatic;
import ma.greenlightgame.client.renderer.Background;
import ma.greenlightgame.client.renderer.Texture;

public class Level1 extends Level {
	public Level1() {
		super();
		
		loaded = false;
	}
	
	@Override
	public void load() {
		EntityPlatformStatic.load();
		
		addEntity(new EntityPlatformStatic(200, 200));
		addEntity(new EntityPlatformStatic(525, 200));
		addEntity(new EntityPlatformStatic(1395, 200));
		addEntity(new EntityPlatformStatic(1720, 200));
		
		addEntity(new EntityPlatformStatic(815, 475));
		addEntity(new EntityPlatformStatic(1135, 475));
		
		addEntity(new EntityPlatformStatic(200, 750));
		addEntity(new EntityPlatformStatic(525, 750));
		addEntity(new EntityPlatformStatic(1395, 750));
		addEntity(new EntityPlatformStatic(1720, 750));
		
		setBackground(new Background(new Texture("backgrounds/background_level_1.jpg")));
		
		loaded = true;
	}
}

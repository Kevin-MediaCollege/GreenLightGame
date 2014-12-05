package ma.greenlightgame.client.renderer;

public class Background {
	private Texture background;
	
	public Background(Texture background) {
		this.background = background;
	}
	
	public void render() {
		Renderer.drawTexture(background.getId(), 0, 0, background.getWidth(),
				background.getHeight(), 0, false);
	}
}

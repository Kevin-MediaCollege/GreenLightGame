package ma.greenlightgame.client.renderer;

/** @author Kevin Krol
 * @since Dec 4, 2014 */
public class Background {
	private Texture background;
	
	public Background(Texture background) {
		this.background = background;
	}
	
	public void render(Renderer renderer) {
		renderer.drawTexture(background.getId(), 0, 0, background.getWidth(), background.getHeight(), 0, false);
	}
}

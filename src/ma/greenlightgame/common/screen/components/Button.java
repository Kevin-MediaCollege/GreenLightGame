package ma.greenlightgame.common.screen.components;

import java.awt.Color;
import java.awt.Rectangle;

import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.MouseButton;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.client.utils.DebugDraw;

public class Button {
	private ButtonActionHandler actionHandler;
	
	private int textureId;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private boolean wasHovering;
	private boolean wasMouseDown;
	
	private boolean active;
	
	public Button(int x, int y, int width, int height, ButtonActionHandler actionHandler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.actionHandler = actionHandler;
		
		active = true;
		
		actionHandler.onCreate(this);
	}
	
	public void update() {
		if(!active)
			return;
		
		if(isHovering()) {
			if(!wasHovering) {
				actionHandler.onHover(this, true);
				wasHovering = true;
			}
			
			if(Input.isMouseDown(MouseButton.LEFT)) {
				actionHandler.onMouse(this, true);
				wasMouseDown = true;
			} else if(Input.isMouseUp(MouseButton.LEFT)) {
				actionHandler.onMouse(this, false);
				
				if(wasMouseDown) {
					actionHandler.onClick(this);
					wasMouseDown = false;
				}
			}
		} else {
			if(wasHovering) {
				actionHandler.onHover(this, false);
				wasHovering = false;
			}
		}
	}
	
	public void render() {
		if(!active)
			return;
		
		Renderer.drawTexture(textureId, x, y, width, height);
	}
	
	public void drawDebug() {
		if(!active)
			return;
		
		DebugDraw.drawBounds(new Rectangle((x - (width / 2)), (y - (height / 2)), width, height), new Color(0, 1, 1));
	}
	
	private boolean isHovering() {
		if(Input.getMouseX() >= (x - (width / 2)) && Input.getMouseX() <= ((x - (width / 2)) + width)) {
			if(Input.getMouseY() >= (y - (height / 2)) && Input.getMouseY() <= ((y - (height / 2)) + height)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void setTexture(int textureId) {
		this.textureId = textureId;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public interface ButtonActionHandler {
		void onCreate(Button button);
		
		void onHover(Button button, boolean hovering);
		
		void onMouse(Button button, boolean down);
		
		void onClick(Button button);
	}
}

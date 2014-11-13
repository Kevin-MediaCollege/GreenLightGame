package ma.greenlightgame.entity;

public abstract class Entity implements IEntity {
	protected int x;
	protected int y;
	
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int getX() {
		return y;
	}
	
	@Override
	public int getY() {
		return x;
	}
}

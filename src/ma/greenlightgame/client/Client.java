package ma.greenlightgame.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ma.greenlightgame.client.entity.EntityPlayer;
import ma.greenlightgame.client.entity.Arm.EntityArm;
import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.client.network.UDPClientHandler;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.common.config.Config;

public class Client {
	private static Client instance;
	
	private Map<Integer, EntityPlayer> players;
	
	private UDPClientHandler udpClientHandler;
	
	private Level level;
	
	private int ownClientId;
	
	private boolean started;
	
	public Client() {
		instance = this;
		
		EntityPlayer.load();
		EntityWall.load();
		EntityArm.load();
		
		players = new HashMap<Integer, EntityPlayer>();
		
		started = false;
	}
	
	public void update(Input input, float delta) {
		if(udpClientHandler == null)
			if(input.isKeyDown(KeyCode.H))
				udpClientHandler = new UDPClientHandler(this);
		
		if(started) {
			final Collection<EntityPlayer> playerList = players.values();
			final EntityWall[] walls = level.getWalls();
			for(EntityPlayer player : playerList) {
				if(player != null) {
					player.update(input, delta);
					
					if(player.isOwn()){	
						for(EntityPlayer p : playerList){
							if(player != p){
								player.atkCollider(p);
							}
						}
						player.checkCollision(walls);
						
					}	
					
				}
			}
			
			if(level != null)
				level.update(input, delta);
		}
	}
	
	public void render(Renderer renderer) {
		final Collection<EntityPlayer> playerList = players.values();
		
		for(EntityPlayer player : playerList)
			if(player != null)
				player.render(renderer);
		
		if(level != null)
			level.render(renderer);
		
		
		if(Config.DRAW_DEBUG) {
			if(level != null)
				level.drawDebug();
			
			for(EntityPlayer player : playerList)
				if(player != null)
					player.drawDebug();
		}
	}
	
	public void destroy() {
		if(udpClientHandler != null)
			udpClientHandler.destroy();
	}
	
	public void connectToLocal() {
		udpClientHandler = new UDPClientHandler(this, "127.0.0.1", Config.getInt(Config.LAST_SERVER_PORT));
	}
	
	public void disconnect(){ 
		udpClientHandler.destroy();
	}
	
	public void addPlayer(int id, EntityPlayer player) {
		players.put(id, player);
	}
	
	public void removePlayer(int id) {
		players.remove(id);
	}
	
	public void loadLevel(int levelId) {
		level = new Level(levelId);
		started = true;
	}
	
	public boolean playerExists(int id) {
		return players.containsKey(id);
	}
	
	public EntityPlayer getPlayer(int id) {
		return players.get(id);
	}
	
	public Level getLevel() {
		return level;
	}
	
	public static void sendMessage(int type, Object... message) {
		if(instance.udpClientHandler == null)
			return;
		
		instance.udpClientHandler.sendMessage(type, message);
	}
	
	public static void setOwnId(int id) {
		instance.ownClientId = id;
	}
	
	public static int getOwnId() {
		return instance.ownClientId;
	}
}
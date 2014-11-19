package ma.greenlightgame.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import ma.greenlightgame.client.entity.EntityPlayer;
import ma.greenlightgame.client.entity.Arm.EntityArm;
import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.input.Input;
import ma.greenlightgame.client.input.Input.KeyCode;
import ma.greenlightgame.client.network.UDPClient;
import ma.greenlightgame.client.network.UDPClientHandler;
import ma.greenlightgame.client.renderer.Renderer;
import ma.greenlightgame.common.config.Config;
import ma.greenlightgame.common.network.NetworkData;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;

public class Client {
	private static UDPClientHandler udpClientHandler;
	private static UDPClient udpClient;
	
	private static boolean started;
	
	private Level level;
	
	public Client() {
		EntityPlayer.load();
		EntityWall.load();
		EntityArm.load();
		
		udpClientHandler = new UDPClientHandler(this);
		
		started = false;
	}
	
	public void update(Input input, float delta) {
		if(udpClient == null) {
			if(input.isKeyDown(KeyCode.H)) {
				try {
					connect(InetAddress.getByName(Config.getString(Config.LAST_SERVER_IP)), Config.getInt(Config.LAST_SERVER_PORT));
				} catch(UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(started) {
			final EntityPlayer[] players = udpClientHandler.getPlayers();
			final EntityWall[] walls = level.getWalls();
			
			for(EntityPlayer player : players) {
				if(player != null) {
					player.update(input, delta);
					
					if(player.isOwn()) {	
						player.checkAttackCollision(players);
						player.checkCollision(walls);
					}
				}
			}
			
			if(level != null)
				level.update(input, delta);
		}
	}
	
	public void render(Renderer renderer) {
		if(started) {
			final EntityPlayer[] players = udpClientHandler.getPlayers();
			
			for(EntityPlayer player : players)
				if(player != null)
					player.render(renderer);
			
			if(level != null)
				level.render(renderer);
			
			if(Config.DRAW_DEBUG) {
				if(level != null)
					level.drawDebug();
				
				for(EntityPlayer player : players)
					if(player != null)
						player.drawDebug();
			}
		}
	}
	
	public void destroy() {
		disconnect();
	}
	
	public void loadLevel(int levelId) {
		level = new Level(levelId);
		started = true;
	}
	
	public Level getLevel() {
		return level;
	}
	
	public static void sendUDP(int type, Object... message) {
		String msg = Integer.toString(type) + NetworkData.SEPERATOR;
		
		for(int i = 0; i < message.length; i++)
			msg += (message[i] + NetworkData.SEPERATOR);
		
		try {
			udpClient.send(msg.getBytes("UTF-8"));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void connect(InetAddress address, int port) {
		disconnect();
		
		try {
			udpClient = new UDPClient(address, port, udpClientHandler);
			Client.sendUDP(NetworkMessage.CLIENT_REQUEST_CONNECT);
		} catch(SocketException e) {
			e.printStackTrace();
		}
	}
	
	public static void disconnect() {
		if(udpClient != null)
			udpClient.close();
		
		udpClientHandler.disconnect();
	}
	
	public static UDPClientHandler getUDPHandler() {
		return udpClientHandler;
	}
	
	public static boolean isStarted() {
		return started;
	}
}
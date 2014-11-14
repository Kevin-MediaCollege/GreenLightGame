package ma.greenlightgame.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ma.greenlightgame.client.entity.EntityManager;
import ma.greenlightgame.client.entity.EntityPlayer;
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
	private static Client instance;
	
	private static int clientId;
	
	private final EntityManager entityManager;
	
	private UDPClient udpConnection;
	
	public Client() {
		instance = this;
		
		EntityPlayer.load();
		EntityWall.load();
		
		entityManager = new EntityManager(this);
		
		entityManager.addWall(new EntityWall(200, 200, 0));
	}
	
	public void update(Input input, float delta) {
		if(input.isKeyDown(KeyCode.H)) {
			if(udpConnection == null) {
				try {
					udpConnection = new UDPClient(InetAddress.getByName(Config.getString(Config.LAST_SERVER_IP)), Config.getInt(Config.LAST_SERVER_PORT), new UDPClientHandler());
					sendMessage(NetworkMessage.CLIENT_REQUEST_CONNECT);
				} catch(UnknownHostException e) {
					e.printStackTrace();
				}
			} else {
				try {
					udpConnection.send("Je fucking moeder!");
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		entityManager.update(input, delta);
	}
	
	public void render(Renderer renderer) {
		entityManager.render(renderer);
	}
	
	public void destroy() {
		if(udpConnection != null)
			udpConnection.close();
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public static  void sendMessage(int type, Object... message) {
		if(instance.udpConnection == null)
			return;
		
		String msg = Integer.toString(type) + NetworkData.SEPERATOR;
		
		for(int i = 0; i < message.length; i++)
			msg += (message[i] + NetworkData.SEPERATOR);
		
		try {
			instance.udpConnection.send(msg);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setClientId(int clientId) {
		Client.clientId = clientId;
	}
	
	public static int getClientId() {
		return clientId;
	}
}

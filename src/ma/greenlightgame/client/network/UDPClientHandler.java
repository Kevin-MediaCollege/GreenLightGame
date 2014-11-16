package ma.greenlightgame.client.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.entity.EntityPlayer;
import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.network.UDPClient.IUDPClientHandler;
import ma.greenlightgame.common.config.Config;
import ma.greenlightgame.common.network.NetworkData;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.common.utils.Utils;

public class UDPClientHandler implements IUDPClientHandler {	
	private UDPClient udpClient;
	
	private Client client;
	
	public UDPClientHandler(Client client) {
		this(client, Config.getString(Config.LAST_SERVER_IP), Config.getInt(Config.LAST_SERVER_PORT));
	}
	
	public UDPClientHandler(Client client, String ip, int port) {
		this.client = client;
		
		try {
			udpClient = new UDPClient(InetAddress.getByName(ip), port, this);
			sendMessage(NetworkMessage.CLIENT_REQUEST_CONNECT);
		} catch(UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onMessageReceived(byte[] message) {
		String[] msg = Utils.bytesToString(message).split(NetworkData.SEPERATOR);
		
		int type = toInt(msg[0]);
		
		switch(type) {
		case NetworkMessage.CLIENT_ACCEPTED:
			onAccepted(toInt(msg[1]), toInt(msg[2]), toInt(msg[3]));
			break;
		case NetworkMessage.CLIENT_REJECTED:
			int reason = toInt(msg[1]);
			System.out.println("REJECTED! Reason: " + reason);
			client.disconnect();
			break;
		case NetworkMessage.CLIENT_JOINED:
			System.out.println("Client joined: " + toInt(msg[1]));
			client.addPlayer(toInt(msg[1]), new EntityPlayer(false));
			break;
		case NetworkMessage.PLAYER_INFO:
			onPlayerInfoReceived(toInt(msg[1]), toInt(msg[2]), toInt(msg[3]), toFloat(msg[4]));
			break;
		case NetworkMessage.PLAYER_COLLISION:
			onPlayerCollision(toInt(msg[1]), toInt(msg[2]), toInt(msg[3]), toBool(msg[4]));
			break;
		case NetworkMessage.GAME_START:
			System.out.println("GAME STARTED");
			client.loadLevel(0);
			break;
		default:
			System.err.println("Client received an unsupported message type: " + msg[0]);
		}
	}
	
	@Override
	public void onUnableToConnect() {
		System.out.println("Unable to connect");
	}
	
	public void destroy() {
		udpClient.close();
	}
	
	public void sendMessage(int type, Object... message) {
		String msg = Integer.toString(type) + NetworkData.SEPERATOR;
		
		for(int i = 0; i < message.length; i++)
			msg += (message[i] + NetworkData.SEPERATOR);
		
		try {
			udpClient.send(msg);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void onAccepted(int id, int x, int y) {
		EntityPlayer player = new EntityPlayer(true);
		player.setX(x);
		player.setY(y);
		Client.setOwnId(id);
		
		client.addPlayer(id, player);
	}
	
	private void onPlayerInfoReceived(int id, int x, int y, float rotation) {
		if(id == Client.getOwnId())
			return;
		
		EntityPlayer player = client.getPlayer(id);
		player.setX(x);
		player.setY(y);
		player.setRotation(rotation);
	}
	
	private void onPlayerCollision(int id, int objectX, int objectY, boolean colliding) {
		if(id == Client.getOwnId())
			return;
		
		EntityPlayer player = client.getPlayer(id);
		EntityWall wall = client.getLevel().getWallAt(objectX, objectY);
		
		if(colliding) {
			player.onCollisionEnter(wall);
		} else {
			player.onCollisionExit(wall);
		}
	}
	
	private float toFloat(String string) {
		return Float.parseFloat(string);
	}
	
	private int toInt(String string) {
		return Integer.parseInt(string);
	}
	
	private boolean toBool(String string) {
		return Boolean.parseBoolean(string);
	}
}

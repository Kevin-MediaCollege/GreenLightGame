package ma.greenlightgame.client.network;

import java.util.HashMap;
import java.util.Map;

import ma.greenlightgame.client.Client;
import ma.greenlightgame.client.entity.EntityPlayer;
import ma.greenlightgame.client.entity.wall.EntityWall;
import ma.greenlightgame.client.network.UDPClient.IUDPClientHandler;
import ma.greenlightgame.common.network.NetworkData;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.common.utils.Utils;

public class UDPClientHandler implements IUDPClientHandler {	
	private static Map<Integer, EntityPlayer> players;
	
	private static int playerId;
	
	private Client client;
	
	public UDPClientHandler(Client client) {
		players = new HashMap<Integer, EntityPlayer>();
		
		this.client = client;
	}
	
	@Override
	public void onMessageReceived(UDPClient client, byte[] message) {
		String[] msg = Utils.bytesToString(message).split(NetworkData.SEPERATOR);
		
		int type = toInt(msg[0]);
		
		switch(type) {
		case NetworkMessage.CLIENT_ACCEPTED:
			onClientJoined(toInt(msg[1]), true);
			break;
		case NetworkMessage.CLIENT_JOINED:
			onClientJoined(toInt(msg[1]), false);
			break;
		case NetworkMessage.CLIENT_REJECTED:
			onRejected(toInt(msg[1]));
			break;
		case NetworkMessage.PLAYER_INFO:
			onPlayerInfoReceived(toInt(msg[1]), toInt(msg[2]), toInt(msg[3]), toFloat(msg[4]));
			break;
		case NetworkMessage.PLAYER_COLLISION:
			onPlayerCollision(toInt(msg[1]), toInt(msg[2]), toInt(msg[3]), toBool(msg[4]));
			break;
		case NetworkMessage.GAME_START:
			onGameStart();
			break;
		default:
			System.err.println("Client received an unsupported message type: " + msg[0]);
		}
	}
	
	@Override
	public void onUnableToConnect(UDPClient client) {
		System.out.println("Unable to connect");
	}
	
	public void disconnect() {
		players.clear();
		playerId = 0;
	}
	
	private void onClientJoined(int id, boolean isOwn) {
		EntityPlayer player = new EntityPlayer(isOwn);
		
		if(isOwn)
			playerId = id;
		
		players.put(id, player);
	}
	
	private void onRejected(int reason) {
		System.err.println("Rejected by server, reason: " + reason);
		
		Client.disconnect();
	}
	
	private void onPlayerInfoReceived(int id, int x, int y, float rotation) {
		if(id == playerId && Client.isStarted())
			return;
		
		EntityPlayer player = players.get(id);
		player.setX(x);
		player.setY(y);
		player.setRotation(rotation);
	}
	
	private void onPlayerCollision(int id, int objectX, int objectY, boolean colliding) {
		if(id == playerId)
			return;
		
		final EntityPlayer player = players.get(id);
		final EntityWall wall = client.getLevel().getWallAt(objectX, objectY);
		
		if(colliding) {
			player.onCollisionEnter(wall);
		} else {
			player.onCollisionExit(wall);
		}
	}
	
	private void onGameStart() {
		client.loadLevel(0);
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
	
	public EntityPlayer[] getPlayers() {
		return players.values().toArray(new EntityPlayer[players.size()]);
	}
	
	public EntityPlayer getPlayer(int id) {
		return players.get(id);
	}
	
	public static int getId() {
		return playerId;
	}
}

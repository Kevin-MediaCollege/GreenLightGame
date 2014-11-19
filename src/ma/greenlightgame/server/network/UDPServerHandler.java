package ma.greenlightgame.server.network;

import java.net.InetAddress;

import ma.greenlightgame.common.network.NetworkData;
import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.common.utils.Utils;
import ma.greenlightgame.server.Server;
import ma.greenlightgame.server.client.ClientHandler;
import ma.greenlightgame.server.client.ServerClientData;
import ma.greenlightgame.server.network.UDPServer.IUDPServerHandler;

public class UDPServerHandler implements IUDPServerHandler {
	private ClientHandler clientHandler;
	
	public UDPServerHandler() {
		clientHandler = new ClientHandler();
	}
	
	@Override
	public void onMesssageReceived(UDPServer server, InetAddress address, int port, byte[] message) {
		String[] msg = Utils.bytesToString(message).split(NetworkData.SEPERATOR);
		
		int type = toInt(msg[0]);
		
		switch(type) {
		case NetworkMessage.CLIENT_REQUEST_CONNECT:
			onClientRequestedJoin(address, port);
			break;
		case NetworkMessage.PLAYER_INFO:
			onPlayerInfoReceived(toInt(msg[1]), toInt(msg[2]), toInt(msg[3]), toFloat(msg[4]));
			break;
		case NetworkMessage.PLAYER_COLLISION:
			onPlayerCollision(toInt(msg[1]), toInt(msg[2]), toInt(msg[3]), toBool(msg[4]));
			break;
		case NetworkMessage.PLAYER_ATTACK:
			onPlayerAttack(toInt(msg[1]), toInt(msg[2]), toBool(msg[3]));
			break;
		case NetworkMessage.PLAYER_HIT:
			onPlayerHit(toInt(msg[1]), toInt(msg[2]));
			break;
		default:
			System.err.println("Server received an unsupported message type: " + type);
			break;
		}
	}
	
	public void destroy() {
		clientHandler.destroy();
	}
	
	public void startGame(int levelId) {
		clientHandler.generatePlayerPositions(levelId);
		
		broadcastUDP(NetworkMessage.GAME_START, levelId);
	}
	
	public void broadcastUDP(int type, Object... message) {
		final ServerClientData[] clients = clientHandler.getClients();
		
		for(ServerClientData client : clients)
			if(client != null)
				Server.sendUDP(client.getAddress(), client.getPort(), type, message);
	}
	
	// TODO: This method should be TCP
	private void onClientRequestedJoin(InetAddress address, int port) {
		// Check if the server is currently in-game
		if(Server.isStarted()) {
			Server.sendUDP(address, port, NetworkMessage.CLIENT_REJECTED, 0);
			return;
		}
		
		// Check if the server is full
		if(clientHandler.getNumClients() >= clientHandler.getMaxClients()) {
			Server.sendUDP(address, port, NetworkMessage.CLIENT_REJECTED, 1);
			return;
		}
		
		// Check if client with same IP and port is already ingame
		final ServerClientData[] clients = clientHandler.getClients();
		for(ServerClientData client : clients) {
			if(client != null) {
				String cAddress = client.getAddress().getCanonicalHostName();
				
				if(cAddress.equals(address.getCanonicalHostName()) && client.getPort() == port) {
					Server.sendUDP(address, port, NetworkMessage.CLIENT_REJECTED, 2);
					return;
				}
			}
		}
		
		onClientJoin(address, port);
	}
	
	// TODO: This method should be TCP
	private void onClientJoin(InetAddress address, int port) {
		final int clientId = clientHandler.getFreeClientID();
		
		ServerClientData client = new ServerClientData(clientId, address, port);
		
		Server.sendUDP(client.getAddress(), client.getPort(), NetworkMessage.CLIENT_ACCEPTED, clientId);
		broadcastUDP(NetworkMessage.CLIENT_JOINED, clientId);
		
		final ServerClientData[] clients = clientHandler.getClients();
		for(ServerClientData c: clients) {
			if(c != null) {
				Server.sendUDP(c.getAddress(), c.getPort(), NetworkMessage.PLAYER_INFO, client.getID(), client.getX(), client.getY(), client.getRotation());
				Server.sendUDP(client.getAddress(), client.getPort(), NetworkMessage.CLIENT_JOINED, c.getID());
				Server.sendUDP(client.getAddress(), client.getPort(), NetworkMessage.PLAYER_INFO, c.getID(), c.getX(), c.getY(), c.getRotation());
			}
		}
		
		clientHandler.addClient(clientId, client);
	}
	
	private void onPlayerInfoReceived(int id, int x, int y, float rotation) {
		final ServerClientData client = clientHandler.getClient(id);
		
		if(client == null)
			return;
		
		client.setRotation(rotation);
		client.setX(x);
		client.setY(y);
		
		broadcastUDP(NetworkMessage.PLAYER_INFO, id, x, y, rotation);
	}
	
	private void onPlayerCollision(int id, int x, int y, boolean colliding) {
		broadcastUDP(NetworkMessage.PLAYER_COLLISION, id, x, y, colliding);
	}
	
	private void onPlayerAttack(int id, int side, boolean attacking) {
		broadcastUDP(NetworkMessage.PLAYER_ATTACK, id, side, attacking);
	}
	
	private void onPlayerHit(int id, int fromId) {
		ServerClientData client = clientHandler.getClient(id);
		
		Server.sendUDP(client.getAddress(), client.getPort(), NetworkMessage.PLAYER_HIT, fromId);
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

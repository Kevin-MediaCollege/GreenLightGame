package ma.greenlightgame.server.client;

import java.net.InetAddress;

import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.server.Server;

public class ClientHandler {
	private ServerClientData[] clients;
	
	private Server server;
	
	private int numClients;
	
	public ClientHandler(Server server) {
		clients = new ServerClientData[4];
		
		numClients = 0;
		
		this.server = server;
	}
	
	public void broadcast(int type, Object... message) {
		for(ServerClientData client : clients)
			if(client != null)
				sendTo(client, type, message);
	}
	
	public void sendTo(ServerClientData client, int type, Object... message) {
		server.sendUDP(client.getAddress(), client.getPort(), type, message);
	}
	
	public void onJoinRequested(InetAddress address, int port) {
		// Check if the server is currently in-game
		if(server.getIsIngame()) {
			server.sendUDP(address, port, NetworkMessage.CLIENT_REJECTED, 0);
			return;
		}
				
		// Check if the server is full
		if(numClients >= clients.length) {
			server.sendUDP(address, port, NetworkMessage.CLIENT_REJECTED, 1);
			return;
		}
		
		// Check if client with same IP and port is already ingame		
		for(ServerClientData client : clients) {
			if(client != null) {
				String cAddress = client.getAddress().getCanonicalHostName();
				
				if(cAddress.equals(address.getCanonicalHostName()) && client.getPort() == port) {
					server.sendUDP(address, port, NetworkMessage.CLIENT_REJECTED, 2);
					return;
				}
			}
		}
		
		onClientJoin(address, port);
	}
	
	public void onPlayerUpdate(int clientId, int x, int y, float rotation) {
		ServerClientData client = getClient(clientId);
		
		if(client == null)
			return;
		
		client.setRotation(rotation);
		client.setX(x);
		client.setY(y);
		
		broadcast(NetworkMessage.PLAYER_INFO, clientId, x, y, rotation);
	}
	
	private void onClientJoin(InetAddress address, int port) {
		final int clientId = getFreeClientID();
		
		ServerClientData client = new ServerClientData(clientId, address, port);
		client.setX(200 + (clientId * 200));
		client.setY(600);
		
		sendTo(client, NetworkMessage.CLIENT_ACCEPTED, clientId, client.getX(), client.getY());
		broadcast(NetworkMessage.CLIENT_JOINED, clientId);
		
		for(ServerClientData c: clients) {
			if(c != null) {
				sendTo(c, NetworkMessage.PLAYER_INFO, client.getID(), client.getX(), client.getY(), client.getRotation());
				sendTo(client, NetworkMessage.CLIENT_JOINED, c.getID());
				sendTo(client, NetworkMessage.PLAYER_INFO, c.getID(), c.getX(), c.getY(), c.getRotation());
			}
		}
		
		clients[clientId] = client;
		numClients++;
	}
	
	private int getFreeClientID() {
		final int l = clients.length;
		
		if(l == 0)
			return -1;
		
		for(int id = 0; id < l; id++) {
			for(int p = 0; p < l; p++) {
				if(clients[p] != null)
					if(id == clients[p].getID())
						break;
				
				if(p == l - 1)
					return id;
			}
		}
		
		return -1;
	}
	
	public ServerClientData getClient(int id) {
		for(ServerClientData client : clients)
			if(client != null)
				if(client.getID() == id)
					return client;
		
		return null;
	}
	
	public int getNumClients() {
		return numClients;
	}
}

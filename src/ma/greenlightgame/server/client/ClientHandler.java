package ma.greenlightgame.server.client;

import java.net.InetAddress;

import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.server.Server;

public class ClientHandler {
	private ClientData[] clients;
	
	private int numClients;
	
	public ClientHandler() {
		clients = new ClientData[4];
		
		numClients = 0;
	}
	
	public void onJoinRequested(InetAddress address, int port) {
		/*// TODO: Check if currently in-game
				
		// Check if the server is full
		if(numClients >= clients.length) {
			Server.sendMessage(address, port, NetworkMessage.CLIENT_REJECTED, 1);
			return;
		}
		
		// Check if client with same IP and port is already ingame
		
		
		for(ClientData data : clients) {
			if(data != null) {
				String dataAddress = data.getAddress().getCanonicalHostName();
				
				if(dataAddress.equals(address.getCanonicalHostName()) && data.getPort() == port) {
					ServerConnection.sendUDP(address, port, ENetworkMessages.SERVER_REJECT_CLIENT_EXISTS);
					ServerLog.log("Rejected client: " + message[1] + " " + ENetworkMessages.SERVER_REJECT_CLIENT_EXISTS);
					return;
				}
			}
		}
		
		// Store client info
		ClientServerData client = new ClientServerData(getNextClientId(), message[1], address, port);
		
		// Welcome the client to the server and assign an ID
		ServerConnection.sendUDP(address, port, ENetworkMessages.SERVER_WELCOME_CLIENT, client.getId());
		
		// Announce that a new client has joined
		ServerConnection.sendUDP(ENetworkMessages.SERVER_CLIENT_JOINED, client.getId(), client.getName());
		
		ServerLog.log(client.getName() + " joined the game!");
		
		// Send the new client info about the other players in the game
		for(int i = 0; i < clients.length; i++) {
			if(clients[i] != null) {
				ClientServerData c = clients[i];
				
				ServerConnection.sendUDP(address, port, ENetworkMessages.SERVER_CLIENT_INFO,
						String.valueOf(c.getId()),
						c.getName(),
						String.valueOf(c.getColor().x),
						String.valueOf(c.getColor().y),
						String.valueOf(c.getColor().z)
					);
			}
		}
		
		clients[client.getId()] = client;
		
		numClients++;*/
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
	
	public ClientData getClient(int id) {
		for(ClientData client : clients)
			if(client != null)
				if(client.getID() == id)
					return client;
		
		return null;
	}
}

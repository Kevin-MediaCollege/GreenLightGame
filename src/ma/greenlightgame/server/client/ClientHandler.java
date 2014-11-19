package ma.greenlightgame.server.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ma.greenlightgame.common.network.NetworkData.NetworkMessage;
import ma.greenlightgame.common.utils.Coord;
import ma.greenlightgame.server.Server;


public class ClientHandler {
	private ServerClientData[] clients;
	
	private int numClients;
	
	public ClientHandler() {
		clients = new ServerClientData[4];
		
		numClients = 0;
	}
	
	public int getFreeClientID() {
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
	
	public void destroy() {
		for(int i = 0; i < clients.length; i++)
			clients[i] = null;
	}
	
	public void generatePlayerPositions(int levelId) {
		// TODO: Load available coords from level file
		
		List<Coord> availableCoords = new ArrayList<Coord>();
		availableCoords.add(new Coord(200, 600));
		availableCoords.add(new Coord(300, 600));
		availableCoords.add(new Coord(400, 600));
		availableCoords.add(new Coord(500, 600));
		availableCoords.add(new Coord(600, 600));
		availableCoords.add(new Coord(700, 600));
		
		Random random = new Random();		
		for(ServerClientData client : clients) {
			if(client != null) {
				Coord coord = availableCoords.get(random.nextInt(availableCoords.size()));
				availableCoords.remove(coord);
				
				client.setX(coord.getX());
				client.setY(coord.getY());
				client.setRotation(0);
				
				for(ServerClientData client2 : clients)
					if(client2 != null)
						Server.sendUDP(client2.getAddress(), client2.getPort(), NetworkMessage.PLAYER_INFO, client.getID(), client.getX(), client.getY(), client.getRotation());
			}
		}
	}
	
	public void addClient(int id, ServerClientData client) {
		clients[id] = client;
		numClients++;
	}
	
	public ServerClientData[] getClients() {
		return clients;
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
	
	public int getMaxClients() {
		return clients.length;
	}
}

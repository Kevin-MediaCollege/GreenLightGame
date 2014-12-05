package ma.greenlightgame.server.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import ma.greenlightgame.common.network.NetworkData;

public class UDPServer implements Runnable {
	private IUDPServerHandler handler;
	
	private DatagramSocket socket;
	
	private Thread thread;
	
	public UDPServer(int port, IUDPServerHandler handler) throws IOException {
		if(port <= 0 || port > NetworkData.MAX_PORT)
			throw new IndexOutOfBoundsException("The port " + port + " is out of bounds (0-"
					+ NetworkData.MAX_PORT + ")");
		
		this.handler = handler;
		
		System.out.println("Starting server on port: " + port);
		
		socket = new DatagramSocket(port);
		socket.setSoTimeout(NetworkData.SO_TIMEOUT);
		socket.setReceiveBufferSize(NetworkData.BUFFER_SIZE);
		socket.setSendBufferSize(NetworkData.BUFFER_SIZE);
		
		if(socket == null || socket.isClosed())
			return;
		
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		System.out.println("Server started");
		
		while(!socket.isClosed()) {
			byte[] buffer = new byte[NetworkData.BUFFER_SIZE];
			
			try {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				
				handler.onMesssageReceived(this, packet.getAddress(), packet.getPort(),
						packet.getData());
			} catch(IOException e) {
				if(!(e instanceof SocketTimeoutException)) {
					if(!socket.isClosed()) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void send(InetAddress address, int port, byte[] message) throws IOException {
		if(socket.isClosed())
			return;
		
		if(message.length >= NetworkData.BUFFER_SIZE)
			return;
		
		DatagramPacket packet = new DatagramPacket(message, message.length, address, port);
		socket.send(packet);
	}
	
	public void close() {
		if(socket.isClosed())
			return;
		
		System.out.println("Stopping server...");
		
		socket.close();
		thread.interrupt();
		
		System.out.println("Server stopped");
	}
	
	public interface IUDPServerHandler {
		void onMesssageReceived(UDPServer server, InetAddress address, int port, byte[] message);
	}
}

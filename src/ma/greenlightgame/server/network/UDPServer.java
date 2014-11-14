package ma.greenlightgame.server.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import ma.greenlightgame.common.network.NetworkData;

public class UDPServer implements Runnable {
	private final Thread thread;
	
	private IUDPServerHandler handler;
	
	private DatagramSocket socket;
	
	public UDPServer(int port, IUDPServerHandler handler) {
		if(port <= 0 || port > NetworkData.MAX_PORT)
			throw new IndexOutOfBoundsException("The port " + port + " is out of bounds (0-" + NetworkData.MAX_PORT + ")");
		
		System.out.println("Starting server on port: " + port);
		
		try {
			socket = new DatagramSocket(port);
			socket.setSoTimeout(NetworkData.SO_TIMEOUT);
			socket.setReceiveBufferSize(NetworkData.BUFFER_SIZE);
			socket.setSendBufferSize(NetworkData.BUFFER_SIZE);
		} catch(SocketException e) {
			e.printStackTrace();
			
			if(socket != null)
				socket.close();
		}
		
		this.handler = handler;
		
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
				
				handler.onMesssageReceived(packet.getAddress(), packet.getPort(), packet.getData());
			} catch(IOException e) {
				if(!socket.isClosed())
					e.printStackTrace();
			}
		}
	}
	
	public void send(InetAddress address, int port, String message) throws IOException {
		try {
			send(address, port, message.getBytes("UTF-8"));
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
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
		void onMesssageReceived(InetAddress address, int port, byte[] message);
	}
}

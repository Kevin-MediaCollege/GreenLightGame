package ma.greenlightgame.client.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import ma.greenlightgame.common.network.NetworkData;

public class UDPClient implements Runnable {
	private IUDPClientHandler handler;
	
	private DatagramSocket socket;
	
	private Thread thread;
	
	public UDPClient(InetAddress address, int port, IUDPClientHandler handler)
			throws SocketException {
		if(port <= 0 || port > NetworkData.MAX_PORT)
			throw new IndexOutOfBoundsException("The port " + port + " is out of bounds (0-"
					+ NetworkData.MAX_PORT + ")");
		
		this.handler = handler;
		
		System.out.println("Connecting to server: " + address.getCanonicalHostName() + ":" + port);
		
		socket = new DatagramSocket();
		socket.setSoTimeout(NetworkData.SO_TIMEOUT);
		socket.setReceiveBufferSize(NetworkData.BUFFER_SIZE);
		socket.setSendBufferSize(NetworkData.BUFFER_SIZE);
		socket.connect(address, port);
		
		if(socket == null || socket.isClosed())
			return;
		
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		System.out.println("Connected to server");
		
		while(!socket.isClosed() && socket.isConnected()) {
			byte[] buffer = new byte[NetworkData.BUFFER_SIZE];
			
			try {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				
				handler.onMessageReceived(this, packet.getData());
			} catch(IOException e) {
				if(!(e instanceof SocketTimeoutException)) {
					if(e instanceof PortUnreachableException) {
						handler.onUnableToConnect(this);
						close();
					} else {
						if(!socket.isClosed()) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public void send(byte[] message) throws IOException {
		if(socket.isClosed())
			return;
		
		if(message.length >= NetworkData.BUFFER_SIZE)
			return;
		
		DatagramPacket packet = new DatagramPacket(message, message.length);
		socket.send(packet);
	}
	
	public void close() {
		if(socket.isClosed())
			return;
		
		System.out.println("Disconnecting...");
		
		socket.close();
		thread.interrupt();
		
		System.out.println("Disconnected");
	}
	
	public interface IUDPClientHandler {
		void onMessageReceived(UDPClient client, byte[] message);
		
		void onUnableToConnect(UDPClient client);
	}
}

package ma.greenlightgame.client.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;

import ma.greenlightgame.common.network.NetworkData;

public class UDPClient implements Runnable {
	private final Thread thread;
	
	private IUDPClientHandler handler;
	
	private DatagramSocket socket;
	
	public UDPClient(InetAddress address, int port, IUDPClientHandler handler) {
		if(port <= 0 || port > NetworkData.MAX_PORT)
			throw new IndexOutOfBoundsException("The port " + port + " is out of bounds (0-" + NetworkData.MAX_PORT + ")");
		
		System.out.println("Connecting to server: " + address.getCanonicalHostName() + ":" + port);
		
		try {
			socket = new DatagramSocket();
			socket.setSoTimeout(NetworkData.SO_TIMEOUT);
			socket.setReceiveBufferSize(NetworkData.BUFFER_SIZE);
			socket.setSendBufferSize(NetworkData.BUFFER_SIZE);
			socket.connect(address, port);
		} catch(SocketException e) {
			e.printStackTrace();
		}
		
		this.handler = handler;
		
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
				
				handler.onMessageReceived(packet.getData());
			} catch(IOException e) {
				if(e instanceof PortUnreachableException) {
					handler.onUnableToConnect();
					close();
				} else {
					if(!socket.isClosed())
						e.printStackTrace();
				}
			}
		}
	}
	
	public void send(String message) throws IOException {
		try {
			send(message.getBytes("UTF-8"));
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
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
		void onMessageReceived(byte[] message);
		
		void onUnableToConnect();
	}
}

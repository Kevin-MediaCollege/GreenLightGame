package ma.greenlightgame;

import java.net.InetAddress;

import ma.greenlightgame.network.UDPServer.IUDPServerHandler;

public class UDPServerHandler implements IUDPServerHandler {
	@Override
	public void onMesssageReceived(InetAddress address, int port, byte[] message) {
		System.out.println("MESSAGE RECEIVED!");
	}
}

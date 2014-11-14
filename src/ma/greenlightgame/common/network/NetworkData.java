package ma.greenlightgame.common.network;

public class NetworkData {
	public class NetworkMessage {
		public static final int CLIENT_REQUEST_CONNECT = 0;
		
		public static final int CLIENT_ACCEPTED        = 1;    // Format: ID
		public static final int CLIENT_REJECTED        = 2;    // Format: Reason[0=playing/1=full/2=clientExists]
		
		public static final int PLAYER_ROTATION        = 3;  // Format: ID, rotation
		public static final int PLAYER_POSITION        = 4;  // Format: ID, X, Y
	}
	
	public static final String SEPERATOR = "/";
	
	public static final String POSITION = "p";
	
	public static final int MAX_PORT = 0xFFFF;
	public static final int SO_TIMEOUT = 10000;
	public static final int BUFFER_SIZE = 1024;
}

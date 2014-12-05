package ma.greenlightgame.common.network;

public class NetworkData {
	public class NetworkMessage {
		public static final int CLIENT_REQUEST_CONNECT = 0;
		public static final int CLIENT_ACCEPTED = 1;    // Format: ID
		public static final int CLIENT_REJECTED = 2;    // Format:
														// Reason[0=playing/1=full/2=clientExists]
		public static final int CLIENT_JOINED = 3;    // Format: ID
		
		public static final int PLAYER_INFO = 4;    // Format: ID, X, Y, velocityX, velocityY, rotation
		public static final int PLAYER_COLLISION = 5;    // Format: ID, objectX, objectY, collides
		public static final int PLAYER_ATTACK = 6;    // Format: ID, side, attacking
		public static final int PLAYER_HIT = 7;    // Format: ID* fromID**
		
		public static final int GAME_START = 8;    // Format: LevelID
		
		// Legend:
		// * = Only for client->server
		// ** = Only for server->client
	}
	
	public static final String SEPERATOR = "/";
	
	public static final String POSITION = "p";
	
	public static final int MAX_PORT = 65535;
	public static final int SO_TIMEOUT = 10000;
	public static final int BUFFER_SIZE = 1024;
}

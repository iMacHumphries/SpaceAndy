package server.packets;

public class Packet05Chat extends Packet {
	
	private String username;
	private String message;
	
	public Packet05Chat(String username, String message) {
		super(05);
		this.username = username;
		this.message = message;
	}
	
	public Packet05Chat(byte[] data) {
		super(05);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.message = dataArray[1];
	}

	@Override
	public byte[] getData() {
		return ("05" + username + "," + message).getBytes();
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getMessage() {
		return message;
	}
	
}
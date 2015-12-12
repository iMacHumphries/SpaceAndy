package server.packets;

public class Packet06Kick extends Packet{
	
	private String username;
	private String reason;
	
	public Packet06Kick(String username, String reason) {
		super(06);
		this.username = username;
		this.reason = reason;
	}
	
	public Packet06Kick(byte[] data) {
		super(06);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.reason = dataArray[1];
	}

	@Override
	public byte[] getData() {
		return ("06" + username + "," + reason).getBytes();
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getReason() {
		return reason;
	}
	
}

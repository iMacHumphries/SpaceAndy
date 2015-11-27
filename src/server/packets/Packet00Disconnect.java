package server.packets;

public class Packet00Disconnect extends Packet {

	private String username;
	
	public Packet00Disconnect(String username) {
		super(00);
		this.username = username;
	}
	
	public Packet00Disconnect(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
	}

	@Override
	public byte[] getData() {
		return ("00" + this.username).getBytes();
	}
	
	public String getUsername() {
		return username;
	}
	
}

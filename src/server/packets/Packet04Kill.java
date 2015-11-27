package server.packets;

public class Packet04Kill extends Packet{
	
	private String username;
	
	public Packet04Kill(String username) {
		super(04);
		this.username = username;
	}
	
	public Packet04Kill(byte[] data) {
		super(04);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
	}

	@Override
	public byte[] getData() {
		return ("04" + username).getBytes();
	}
	
	public String getUsername() {
		return username;
	}
	
}

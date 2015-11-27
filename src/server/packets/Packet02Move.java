package server.packets;

public class Packet02Move extends Packet {
	private String username;
	private int x;
	private int y;
	private int rotZ;
	
	public Packet02Move(String username, int x, int y, int rotZ) {
		super(02);
		this.username = username;
		this.x = x;
		this.y = y;
		this.rotZ = rotZ;
	}
	
	public Packet02Move(byte[] data) {
		super(02);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
		this.rotZ = Integer.parseInt(dataArray[3]);
	}

	@Override
	public byte[] getData() {
		return ("02" + username + "," + x + "," + y + "," + rotZ).getBytes();
	}

	public String getUsername() {
		return username;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRotZ() {
		return rotZ;
	}
	
}

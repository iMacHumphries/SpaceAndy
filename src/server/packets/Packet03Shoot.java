package server.packets;

public class Packet03Shoot extends Packet {

	private String username;
	private float x;
	private float y;
	private float dirX;
	private float dirY;
	private float rotZ;
	
	public Packet03Shoot(String username, float x, float y, float dirX, float dirY, float rotZ) {
		super(03);
		this.username = username;
		this.x = x;
		this.y = y;
		this.dirX = dirX;
		this.dirY = dirY;
		this.rotZ = rotZ;
	}
	
	public Packet03Shoot(byte[] data) {
		super(03);
		String[] dataArray = readData(data).split(",");
		username = dataArray[0];
		x = Float.parseFloat(dataArray[1]);
		y = Float.parseFloat(dataArray[2]);
		dirX = Float.parseFloat(dataArray[3]);
		dirY = Float.parseFloat(dataArray[4]);
		rotZ = Float.parseFloat(dataArray[5]);
	}


	@Override
	public byte[] getData() {
		return ("03" + username + "," + x + "," + y + "," + dirX + "," + dirY + "," + rotZ).getBytes();
	}

	public String getUsername() {
		return username;
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getDirX() {
		return dirX;
	}

	public float getDirY() {
		return dirY;
	}

	public float getRotZ() {
		return rotZ;
	}
}

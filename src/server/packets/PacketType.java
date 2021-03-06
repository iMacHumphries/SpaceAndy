package server.packets;

public enum PacketType {
	INVALID(-1),
	DISCONNECT(00),
	LOGIN(01),
	MOVE(02),
	SHOOT(03),
	KILL(04),
	CHAT(05),
	KICK(06),
	STOP(07);
	
	private int packetID;
	
	private PacketType(int _id) {
		this.packetID = _id;
	}
	
	public int getID() {
		return packetID;
	}
}
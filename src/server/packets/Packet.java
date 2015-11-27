package server.packets;

public abstract class Packet {
	private byte ID;
	private PacketType type;
	
	public Packet(int _ID) {
		this.setID((byte) _ID);
		type = Packet.findPacket(_ID);
	}

	public abstract byte[] getData();
	
	public String readData(byte[] data) {
		String msg = new String(data).trim();
		return msg.substring(2);
	}
	
	public static PacketType findPacket(int _id) {
		PacketType result = PacketType.INVALID;
		for (PacketType p : PacketType.values()) {
			if (p.getID() == _id)
				result = p;
		}
		return result;
	}
	
	public static PacketType findPacket(String _id) {
		try {
			return findPacket(Integer.parseInt(_id.substring(0, 2)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PacketType.INVALID;
	}

	public byte getID() {
		return ID;
	}

	public void setID(byte iD) {
		ID = iD;
	}
	
	public PacketType getType() {
		return type;
	}
}

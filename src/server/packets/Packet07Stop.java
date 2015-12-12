package server.packets;

public class Packet07Stop extends Packet {

	public Packet07Stop() {
		super(07);
	}

	@Override
	public byte[] getData() {
		return ("07").getBytes();
	}

}

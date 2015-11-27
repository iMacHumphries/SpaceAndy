package server;

import server.packets.*;

public interface ClientListener {
	
	void clientDidReceiveDisconnectPacket(Packet00Disconnect disconnectPacket);
	
	void clientDidReceiveLoginPacket(Packet01Login loginPacket);
	
	void clientDidReceiveMovePacket(Packet02Move movePacket);
	
	void clientDidReceiveShootPacket(Packet03Shoot shootPacket);
	
	void clientDidReceiveKillPacket(Packet04Kill killPacket);
}

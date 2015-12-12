package server;

import server.packets.*;

public interface ClientListener {	
	void clientDidReceivePacket(Packet packet);
}

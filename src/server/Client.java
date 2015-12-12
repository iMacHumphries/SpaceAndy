package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import constants.Constants;
import server.packets.*;

public class Client extends Thread {
	private InetAddress hostIPAddress;
	private DatagramSocket socket;

	private ClientListener delegate;

	public Client(ClientListener delegate, String ip) {
		this.delegate = delegate;
		try {
			this.hostIPAddress = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			byte[] data = new byte[Constants.MAX_BYTE_SIZE];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.parsePacket(data, packet.getAddress(), packet.getPort());
		}
	}

	private void parsePacket(byte[] data, InetAddress ip, int port) {
		String msgFromServer = new String(data).trim();
		PacketType type = Packet.findPacket(msgFromServer.substring(0, 2));
		switch (type) {
		case INVALID:
			System.out.println("Invalid packet type on client.");
			break;
		case DISCONNECT:
			delegate.clientDidReceivePacket(new Packet00Disconnect(data));
			break;
		case LOGIN:
			delegate.clientDidReceivePacket(new Packet01Login(data));
			break;
		case MOVE:
			delegate.clientDidReceivePacket(new Packet02Move(data));
			break;
		case SHOOT:
			delegate.clientDidReceivePacket(new Packet03Shoot(data));
			break;
		case KILL:
			delegate.clientDidReceivePacket(new Packet04Kill(data));
			break;
		case CHAT:
			delegate.clientDidReceivePacket(new Packet05Chat(data));
			break;
		case KICK:
			delegate.clientDidReceivePacket(new Packet06Kick(data));
			break;
		}
	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length,
				hostIPAddress, Constants.PORT_NUMBER);
		try {
			socket.send(packet);
		} catch (IOException e) {
			System.out.println("error sending data to client");
			e.printStackTrace();
		}
	}
}

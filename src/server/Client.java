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
		PacketType type = Packet.findPacket(msgFromServer.substring(0,2));
		switch (type) {
		case INVALID:
			break;
		case LOGIN:
			Packet01Login loginPacket = new Packet01Login(data);
			System.out.println("got a login from " + loginPacket.getUsername());
			delegate.clientDidReceiveLoginPacket(loginPacket);
			break;
		case DISCONNECT:
			delegate.clientDidReceiveDisconnectPacket(new Packet00Disconnect(data));
			break;
		case MOVE:
			delegate.clientDidReceiveMovePacket(new Packet02Move(data));
			break;
		case SHOOT:
			delegate.clientDidReceiveShootPacket(new Packet03Shoot(data));
		case KILL:
			delegate.clientDidReceiveKillPacket(new Packet04Kill(data));
			break;
		default:
			break;
		
		}

	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, hostIPAddress, Constants.PORT_NUMBER);
		try {
			socket.send(packet);
		} catch (IOException e) {
			System.out.println("error sending data to client");
			e.printStackTrace();
		}
	}
}

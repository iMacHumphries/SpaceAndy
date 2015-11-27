package server;

import java.util.LinkedList;
import java.util.Queue;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import constants.Constants;
import entities.EntityManager;
import entities.Laser;
import entities.Player;
import entities.Player.PlayerListener;
import entities.PlayerBot;
import server.packets.*;


public class MultiplayerManager implements ClientListener, PlayerListener {
	private Client client;
	private EntityManager entityManager;
	private Queue<Packet> packetQueue;

	public MultiplayerManager(EntityManager entityManager) {
		this.entityManager = entityManager;
		packetQueue = new LinkedList<Packet>();
		client = new Client(this, Constants.SERVER_IP);
		client.start();
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		if (hasPackets()) {
			Packet packet = popPacket();
			
			switch (packet.getType()) {
			case INVALID:
				break;
			case DISCONNECT:
				Packet00Disconnect disconnectPacket = (Packet00Disconnect) packet;
				entityManager.removePlayerBot(new PlayerBot(disconnectPacket.getUsername()));
				break;
			case LOGIN:
				Packet01Login loginPacket = (Packet01Login) packet;
				PlayerBot bot = new PlayerBot(loginPacket.getUsername());
				bot.setX(loginPacket.getX());
				bot.setY(loginPacket.getY());
				bot.setRotz(loginPacket.getRotZ());
				entityManager.addEntity(bot);
				break;
			case MOVE:
				break;
			case SHOOT:
				Packet03Shoot shootPacket = (Packet03Shoot) packet;
				entityManager.addEntity(new Laser(shootPacket.getUsername(), shootPacket.getDirX(), shootPacket.getDirY(), shootPacket.getX(), shootPacket.getY(), shootPacket.getRotZ()));
				break;
			case KILL:
				Packet04Kill killPacket = (Packet04Kill) packet;
				//entityManager.removePlayerBot(new PlayerBot(killPacket.getUsername()));
				break;
			default:
				break;
			}
			
		}
	}
	
	public boolean hasPackets() {
		return !packetQueue.isEmpty();
	}
	
	public Packet popPacket() {
		return packetQueue.remove();
	}

	public void login(Player player) {
		this.sendPacketToServer(new Packet01Login(player.getName(), (int)player.getX(), (int)player.getY(), (int)player.getRotz()));
	}

	public void disconnect(Player player) {
		this.sendPacketToServer(new Packet00Disconnect(player.getName()));
	}
	
	public void shootLaser(Laser laser) {
		this.sendPacketToServer(new Packet03Shoot(laser.getUsername(),laser.getX(), laser.getY(), laser.getDirX(), laser.getDirY(), laser.getRotz()));
	}
	
	public void kill(String username) {
		this.sendPacketToServer(new Packet04Kill(username));
	}
	
	private void sendPacketToServer(Packet packet) {
		client.sendData(packet.getData());
	}
	
	@Override
	public void playerDidMove(Player player) {
		this.sendPacketToServer(new Packet02Move(player.getName(), (int)player.getX(), (int)player.getY(), (int)player.getRotz()));
	}

	@Override
	public void clientDidReceiveDisconnectPacket(Packet00Disconnect disconnectPacket) {
		packetQueue.add(disconnectPacket);
	}
	
	@Override
	public void clientDidReceiveLoginPacket(Packet01Login loginPacket) {
		packetQueue.add(loginPacket);
	}

	@Override
	public void clientDidReceiveMovePacket(Packet02Move movePacket) {
		entityManager.movePlayerBot(
				new PlayerBot(movePacket.getUsername()),
				movePacket.getX(),
				movePacket.getY(),
				movePacket.getRotZ());
	}

	@Override
	public void clientDidReceiveShootPacket(Packet03Shoot shootPacket) {
		packetQueue.add(shootPacket);
	}

	@Override
	public void clientDidReceiveKillPacket(Packet04Kill killPacket) {
		packetQueue.add(killPacket);
	}
	

}

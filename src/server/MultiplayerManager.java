package server;

import java.util.LinkedList;
import java.util.Queue;

import menu.KickedScreen;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import constants.Constants;
import entities.EntityManager;
import entities.Laser;
import entities.Player;
import entities.Player.PlayerListener;
import entities.PlayerBot;
import server.packets.*;
import sound.AudioManager;
import util.ChatBox;

public class MultiplayerManager implements ClientListener, PlayerListener {
	private Client client;
	private EntityManager entityManager;
	private Queue<Packet> packetQueue;
	private Player player;
	private ChatBox chatBox;

	public MultiplayerManager(EntityManager entityManager, ChatBox box, String serverIp) {
		this.entityManager = entityManager;
		this.chatBox = box;
		packetQueue = new LinkedList<Packet>();
		client = new Client(this, serverIp);
		client.start();
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (!packetQueue.isEmpty()) {
			Packet packet = packetQueue.poll();
			
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
				Packet02Move movePacket = (Packet02Move)packet;
				entityManager.movePlayerBot(
						new PlayerBot(movePacket.getUsername()),
						movePacket.getX(),
						movePacket.getY(),
						movePacket.getRotZ());
				break;
			case SHOOT:
				Packet03Shoot shootPacket = (Packet03Shoot) packet;
				AudioManager.playClipRandomPitch("shot1.wav");
				entityManager.addEntity(new Laser(shootPacket.getUsername(), shootPacket.getDirX(), shootPacket.getDirY(), shootPacket.getX(), shootPacket.getY(), shootPacket.getRotZ()));
				break;
			case KILL:
				AudioManager.playClip("boom.wav");
				Packet04Kill killPacket = (Packet04Kill) packet;
				entityManager.removePlayerBot(new PlayerBot(killPacket.getUsername()));
				if (killPacket.getUsername().equalsIgnoreCase(player.getName()))
					player.setShouldRemove(true);
				break;
			case CHAT:
				Packet05Chat chatPacket = (Packet05Chat) packet;
				chatBox.addMessageFromPlayer(chatPacket.getUsername(), chatPacket.getMessage());
				break;
			case KICK:
				Packet06Kick kickPacket = (Packet06Kick) packet;
				if (kickPacket.getUsername().equalsIgnoreCase(player.getName())) {
					KickedScreen kicked = (KickedScreen)sbg.getState(Constants.KICKED_GAME_STATE);
					kicked.setMessage("You have been kicked for " + kickPacket.getReason());
					sbg.enterState(Constants.KICKED_GAME_STATE);
				}
				break;
			case STOP:
				KickedScreen kicked = (KickedScreen)sbg.getState(Constants.KICKED_GAME_STATE);
				kicked.setMessage("Server stopped.");
				sbg.enterState(Constants.KICKED_GAME_STATE);
				break;
			}	
		}
	}
	
	public void login(Player player) {
		this.player = player;
		this.sendPacketToServer(new Packet01Login(player.getName(), (int)player.getX(), (int)player.getY(), (int)player.getRotz()));
	}

	public void disconnect(Player player) {
		sendPacketToServer(new Packet00Disconnect(player.getName()));
	}
	
	public void shootLaser(Laser laser) {
		sendPacketToServer(new Packet03Shoot(laser.getUsername(),laser.getX(), laser.getY(), laser.getDirX(), laser.getDirY(), laser.getRotz()));
	}
	
	public void kill(String username) {
		sendPacketToServer(new Packet04Kill(username));
	}
	
	public void sendMessage(Player player, String msg) {
		sendPacketToServer(new Packet05Chat(player.getName(), msg));
	}
	
	private void sendPacketToServer(Packet packet) {
		client.sendData(packet.getData());
	}
	
	@Override
	public void playerDidMove(Player player) {
		sendPacketToServer(new Packet02Move(player.getName(), (int)player.getX(), (int)player.getY(), (int)player.getRotz()));
	}
	
	@Override
	public void clientDidReceivePacket(Packet packet) {
		packetQueue.add(packet);
	}
}

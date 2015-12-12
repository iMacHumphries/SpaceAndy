package server;

import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import constants.Constants;

public class LocalServerFinder implements Runnable {

	private ArrayList<InetAddress> hosts;
	
	private LocalServerListener delegate;
	public interface LocalServerListener {
		void didFinishSearchingForHosts(ArrayList<InetAddress> hosts);
	}

	public LocalServerFinder(LocalServerListener delegate) {
		this.delegate = delegate;
		hosts = new ArrayList<InetAddress>();
	}

	@Override
	public void run() {
		hosts = checkHosts(Constants.PORT_NUMBER, 9999);
		delegate.didFinishSearchingForHosts(hosts);
	}

	public ArrayList<InetAddress> getHosts() {
		return hosts;
	}

	private ArrayList<InetAddress> checkHosts(int socket, int timeout) {
		ArrayList<InetAddress> al = new ArrayList<InetAddress>();
		try {
			byte[] buffer = { 1 };
			DatagramSocket ds = new DatagramSocket(socket);
			ds.setSoTimeout(timeout);
			DatagramPacket dp;
			InetAddress local = InetAddress.getLocalHost();
			String subnet = getSubnet(local);
			for (int i = 1; i <= 255; i++) {
				String host = subnet + i;
				try {
					ds.send(new DatagramPacket(buffer, 1, InetAddress
							.getByName(host), 3333));
					ds.receive(dp = new DatagramPacket(buffer, 1));
					if (dp.getPort() == socket
							&& !dp.getAddress().equals(local)) {
						al.add(dp.getAddress());

					}
				} catch (Exception e) {
					continue;
				}
			}
			ds.close();
		} catch (BindException e) {
			System.out.println("found callled");
			try {
				al.add(InetAddress.getLocalHost());
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

	private String getSubnet(InetAddress address) {
		byte[] a = address.getAddress();
		String subnet = "";
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] != 0)
				subnet += (256 + a[i]) + ".";
			else
				subnet += 0 + ".";
		}
		return subnet;
	}
}

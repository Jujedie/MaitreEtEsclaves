package MaitreEsclave.Metier;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Service extends Thread {
	private static ArrayList<Integer> portLibre = initPortLibre();

	private Serveur serveur;
	private DatagramPacket data;
	private BufferedImage image;

	public Service(Serveur serveur, DatagramPacket data, BufferedImage image) {
		this.serveur = serveur;
		this.data = data;
		this.image = image;
	}

	@Override
	public void run() {
		try {

			String message = new String(data.getData(), 0, data.getLength());

			if (message.equals("awaiting task")) {
				int[] coord = this.serveur.getImageCoordinates(this.image);

				this.serveur.setGrilleImagesComplete(coord[0], coord[1]);

				String task;
				if (Math.random() < 0.5) {
					task = "invert";
				} else {
					task = "permute";
				}

				// Envoie la tache au client
				byte[] sendData = (task + " " + coord[0] + " " + coord[1] + " ").getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, data.getAddress(),
						data.getPort());
				this.serveur.getServerSocket().send(sendPacket);

				// Envoie de la taille de l'image au client
				byte[] imageData = Serveur.BufferedImageToByteArray(this.image);
				String imageSize = String.valueOf(imageData.length);
				sendData = imageSize.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length, data.getAddress(), data.getPort());
				this.serveur.getServerSocket().send(sendPacket);

				// Envoie de l'image au client
				sendData = imageData;
				sendPacket = new DatagramPacket(sendData, sendData.length, data.getAddress(), data.getPort());
				this.serveur.getServerSocket().send(sendPacket);
			} else {
				String info = new String(data.getData(), 0, data.getLength());

				// Séparer les informations de l'image
				String[] infoParts = info.split(" ");

				int tailleIMG = Integer.parseInt(infoParts[0]);
				int x = Integer.parseInt(infoParts[1]);
				int y = Integer.parseInt(infoParts[2]);

				while (Service.getPortLibre().size() == 0) {
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				new DatagramSocket(Service.getPortLibre().get(0), InetAddress.getLocalHost());
				Service.getPortLibre().remove(0);

				// envoie du port libre au client
				byte[] sendData = String.valueOf(Service.getPortLibre().get(0)).getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, data.getAddress(),
						data.getPort());
				this.serveur.getServerSocket().send(sendPacket);

				// reception de l'image

				DatagramSocket socket = new DatagramSocket();
				DatagramPacket receivePacket = new DatagramPacket(new byte[512], 512);
				byte[] receiveData = new byte[512];

				try {
					receiveData   = new byte[tailleIMG];
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					socket = new DatagramSocket(Service.getPortLibre().get(0),InetAddress.getLocalHost());
					socket.receive(receivePacket);
				} catch (Exception e) {
					e.printStackTrace();
				}

				Service.getPortLibre().add(0, socket.getLocalPort());

				byte[] imageData = receivePacket.getData();
				ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
				this.image = ImageIO.read(bais);

				socket.close();

				this.serveur.setGrilleImages(x, y, this.image);
				this.serveur.majIHM();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static ArrayList<Integer> initPortLibre() {
		ArrayList<Integer> portLibre = new ArrayList<Integer>();

		portLibre.add(5000);

		return portLibre;
	}

	public static ArrayList<Integer> getPortLibre() {
		return portLibre;
	}
}

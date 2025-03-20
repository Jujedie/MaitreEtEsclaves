package MaitreEsclave.Metier;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
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
				System.out.println("Image : " + this.image);
				int[] coord = this.serveur.getImageCoordinates(this.image);

				if (coord == null) {
					System.err.println("Erreur : Coordonnées de l'image non trouvées.");
					return;
				}

				this.serveur.setGrilleImagesComplete(coord[0], coord[1]);

				String task;
				if (Math.random() < 0.5) {
					task = "invert";
				} else {
					task = "permute";
				}

				// Envoie la tache au client
				System.out.println("Envoie de la tache : " + task + "\n");

				byte[] sendData = (task + " " + coord[0] + " " + coord[1] + " ").getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, data.getAddress(),
						data.getPort());
				this.serveur.getServerSocket().send(sendPacket);

				// Envoie de la taille de l'image au client
				System.out.println("Envoie de la taille de l'image : " + this.image.getWidth() + "x"
						+ this.image.getHeight() + "\n");

				byte[] imageData = Serveur.BufferedImageToByteArray(this.image);
				String imageSize = String.valueOf(imageData.length);
				sendData = imageSize.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length, data.getAddress(), data.getPort());
				this.serveur.getServerSocket().send(sendPacket);

				// Envoie de l'image au client
				System.out.println("Envoie de l'image\n");

				sendData = imageData;
				sendPacket = new DatagramPacket(sendData, sendData.length, data.getAddress(), data.getPort());
				this.serveur.getServerSocket().send(sendPacket);
			} else {
				String info = new String(data.getData(), 0, data.getLength());

				// Séparer les informations de l'image
				System.out.println("Réception de l'image\n");

				String[] infoParts = info.split(" ");

				int tailleIMG = Integer.parseInt(infoParts[0]);
				int x = Integer.parseInt(infoParts[1]);
				int y = Integer.parseInt(infoParts[2]);

				URL url = new URI("http://checkip.amazonaws.com/").toURL();
				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
				String adr = br.readLine();

				
				while (Service.getPortLibre().size() == 0) {
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(0);
					}
				}

				DatagramSocket ds = new DatagramSocket(Service.getPortLibre().remove(0), InetAddress.getByName(adr));
				int port = ds.getLocalPort();

				// envoie du port libre au client
				System.out.println("Envoie du port libre : " + ds.getLocalPort() + "\n");

				byte[] sendData = String.valueOf(ds.getLocalPort()).getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, data.getAddress(),
						data.getPort());
				this.serveur.getServerSocket().send(sendPacket);

				// reception de l'image
				System.out.println("Réception de l'image\n");

				DatagramPacket receivePacket = new DatagramPacket(new byte[512], 512);
				byte[] receiveData = new byte[512];

				try {
					receiveData = new byte[tailleIMG];
					receivePacket = new DatagramPacket(receiveData, receiveData.length);
					ds.receive(receivePacket);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}

				byte[] imageData = receivePacket.getData();
				ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
				this.image = ImageIO.read(bais);

				System.out.println("Image reçue\n");

				ds.close();

				Service.getPortLibre().add(0, port);

				this.serveur.setGrilleImages(x, y, this.image);

				System.out.println("Image ajoutée à la grille\n");
				this.serveur.majIHM();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
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

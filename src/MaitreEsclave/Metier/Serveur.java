package MaitreEsclave.Metier;

import MaitreEsclave.Controleur;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Serveur extends Thread
{
	private Boolean[][]       grilleImagesComplete;
	private BufferedImage[][] grilleImages;
	private DatagramSocket    serverSocket;
	private Controleur        controleur;

	public Serveur(String cheminImage, int nbLignes, int nbColonnes, int port, Controleur ctrl)
	{
		this.controleur = ctrl;

		BufferedImage image = null;
		try
		{
			this.serverSocket = new DatagramSocket(port,InetAddress.getLocalHost());

			image = ImageIO.read(new File(cheminImage));
			if (image == null)
			{
				throw new IOException("Impossible de lire l'image");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		this.grilleImagesComplete = new Boolean[nbLignes][nbColonnes];
		this.grilleImages   = new BufferedImage[nbLignes][nbColonnes];

		ImageIcon imageIcon = new ImageIcon(image);

		int largeurImage = imageIcon.getIconWidth() / nbColonnes;
		int hauteurImage = imageIcon.getIconHeight() / nbLignes;

		for (int i = 0; i < nbLignes ; i++)
		{
			for (int j = 0; j < nbColonnes ; j++)
			{
				this.grilleImages[i][j] = image.getSubimage(j * largeurImage, i * hauteurImage, largeurImage, hauteurImage);
				this.grilleImagesComplete[i][j] = false;
			}
		}
	}

	public void run()
	{
		System.out.println("Serveur initialisé");

		while(!this.isGrilleImagesComplete())
		{
			try
			{
				// récupérer les coordonnées de l'image à envoyer
				BufferedImage image = this.getNextImage();
				

				byte[] receiveData = new byte[4096];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

				if (message.equals("awaiting task")) {
					byte[] imageData = BufferedImageToByteArray(image);
					DatagramPacket sendPacket = new DatagramPacket(imageData, imageData.length, receivePacket.getAddress(), receivePacket.getPort());
					System.out.println("Taille de l'image envoyée : " + imageData.length);
					serverSocket.send(sendPacket);
					
					for (int i = 0; i < grilleImagesComplete.length; i++) {
						for (int j = 0; j < grilleImagesComplete[0].length; j++) {
							if (grilleImages[i][j] == image) {
								grilleImagesComplete[i][j] = true;
								break;
							}
						}
					}
				} 
				else 
				{
					// Process received image and update grilleImages
					// Update IHM
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public Boolean[][] getGrilleImagesComplete()
	{
		return grilleImagesComplete;
	}

	public BufferedImage[][] getGrilleImages()
	{
		return grilleImages;
	}

	public DatagramSocket getServerSocket()
	{
		return serverSocket;
	}

	public static byte[] BufferedImageToByteArray(BufferedImage buffer)
	{
		byte[] bytes = null;
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(buffer, "jpg", baos);
			bytes = baos.toByteArray();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return bytes;
	} 

	public Boolean isGrilleImagesComplete()
	{
		for (int i = 0; i < this.grilleImagesComplete.length; i++)
		{
			for (int j = 0; j < this.grilleImagesComplete[0].length; j++)
			{
				if (!this.grilleImagesComplete[i][j])
				{
					return false;
				}
			}
		}

		return true;
	}

	public BufferedImage getNextImage()
	{
		for (int i = 0; i < this.grilleImagesComplete.length; i++)
		{
			for (int j = 0; j < this.grilleImagesComplete[0].length; j++)
			{
				if (!this.grilleImagesComplete[i][j])
				{
					return this.grilleImages[i][j];
				}
			}
		}

		return null;
	}
}
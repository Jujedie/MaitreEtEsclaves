package MaitreEsclave.Metier;

import MaitreEsclave.Controleur;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Serveur extends Thread
{
	private Boolean[][]       grilleImagesComplete;
	private BufferedImage[][] grilleImages;
	private DatagramSocket    serverSocket;
	private Controleur        controleur;

	private String adress;

	public Serveur(String cheminImage, int nbLignes, int nbColonnes, String adress, int port, Controleur ctrl)
	{
		this.controleur = ctrl;
		
		BufferedImage image = null;
		try
		{
			this.adress = adress;
			this.serverSocket = new DatagramSocket(port,InetAddress.getByName(adress));

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

		System.out.println("largeurImage = " + largeurImage + " hauteurImage = " + hauteurImage);
		System.out.println("nbLignes = " + nbLignes + " nbColonnes = " + nbColonnes);
		System.out.println("Largeur image = " + imageIcon.getIconWidth() + " Hauteur image = " + imageIcon.getIconHeight());

		for (int i = 0; i < nbLignes ; i++)
		{
			for (int j = 0; j < nbColonnes ; j++)
			{
				int x = j * largeurImage;
				int y = i * hauteurImage;
				int w = (j == nbColonnes - 1) ? imageIcon.getIconWidth() - x : largeurImage; // afin de prendre en compte les images qui ne sont pas carrées
				int h = (i == nbLignes - 1) ? imageIcon.getIconHeight() - y : hauteurImage;
				this.grilleImages[i][j] = image.getSubimage(x, y, w, h);
				this.grilleImagesComplete[i][j] = false;
			}
		}
	}

	public void majIHM()
	{
		this.controleur.majImage();
	}

	@Override
	public void run()
	{
		try
		{
			System.out.println("Serveur initialisé sur le port " + this.serverSocket.getLocalPort() + " et l'adresse " + InetAddress.getLocalHost());
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}

		//while(!this.isGrilleImagesComplete())
		while(true)
		{
			try
			{
				System.out.println("\n\nEn attente de paquet de données\n");

				byte[] receiveData = new byte[2048];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);

				// récupérer les coordonnées de l'image à envoyer
				BufferedImage image = this.getNextImage();

				System.out.println("Réception du paquet de données\n");
				(new Service(this, receivePacket, image)).start();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		//System.out.println("\nServeur terminé\n");
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
	public String getAdress()
	{
		return adress;
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

		if (this.isGrilleImagesComplete())
		{
			for (int i = 0; i < this.grilleImagesComplete.length; i++)
			{
				for (int j = 0; j < this.grilleImagesComplete[0].length; j++)
				{
					this.grilleImagesComplete[i][j] = false;
				}
			}
		}
		
		ArrayList<BufferedImage> imagesLibre = new ArrayList<BufferedImage>();
		for (int i = 0; i < this.grilleImagesComplete.length; i++)
		{
			for (int j = 0; j < this.grilleImagesComplete[0].length; j++)
			{
				if (!this.grilleImagesComplete[i][j])
				{
					imagesLibre.add(this.grilleImages[i][j]);
				}
			}
		}

		if (imagesLibre.isEmpty())
		{
			return null;
		}

		BufferedImage image = imagesLibre.get((int)(Math.random() * imagesLibre.size()));
		int[] coord = this.getImageCoordinates(image);
		
		this.grilleImagesComplete[coord[0]][coord[1]] = true;

		return image;
	}

	public int[] getImageCoordinates(BufferedImage image)
	{
		for (int i = 0; i < this.grilleImagesComplete.length; i++)
		{
			for (int j = 0; j < this.grilleImagesComplete[0].length; j++)
			{
				if (this.grilleImages[i][j] == image)
				{
					return new int[]{i, j};
				}
			}
		}

		return null;
	}

	public void setGrilleImagesComplete(int i, int j)
	{
		this.grilleImagesComplete[i][j] = true;
	}

	public void setGrilleImages(int i, int j, BufferedImage image)
	{
		this.grilleImages[i][j] = image;
	}
}
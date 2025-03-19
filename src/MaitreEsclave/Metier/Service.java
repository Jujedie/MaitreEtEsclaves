package MaitreEsclave.Metier;

import java.awt.image.BufferedImage;
import java.net.DatagramPacket;

public class Service extends Thread
{
	private Serveur serveur;
	private DatagramPacket data;
	private BufferedImage  image;

	public Service(Serveur serveur, DatagramPacket data, BufferedImage image)
	{
		this.serveur = serveur;
		this.data    = data;
		this.image   = image;
	}

	public void run()
	{
		try
		{
			// si prochain message est un message "awaiting task"

				// alors donner données et tache pour le client ET marquer l'image comme complète dans grilleImagesComplete

			// sinon traiterImage reçu et modifier la grilleImages

				// Mettre à jour IHM

			String message = new String(data.getData(), 0, data.getLength());

			if (message.equals("awaiting task"))
			{
				int[] coord = this.serveur.getImageCoordinates(this.image);

				this.serveur.setGrilleImagesComplete(coord[0], coord[1]);

				String task;
				if ( Math.random() < 0.5 )
				{
					task = "invert";
				}
				else
				{
					task = "permute";
				}

				// Envoie la tache au client
				byte[] sendData = (task+ " " + coord[0] + " " + coord[1] + " ").getBytes();
			}
			else
			{
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

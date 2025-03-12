package MaitreEsclave.Metier;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Serveur
{
	private Boolean[][] grilleImagesComplete;
	private BufferedImage[][]   grilleImages;
	private DatagramSocket      serverSocket;

	public Serveur(String cheminImage,int nbColonnes, int nbLignes, int port)
	{
		BufferedImage image = null;
		try
		{
			this.serverSocket = new DatagramSocket(port,InetAddress.getByName());

			image = ImageIO.read(new File(cheminImage));
			if (image == null)
			{
				throw new IOException("Impossible de lire l'image");
			}
		}
		catch (IOException e){e.printStackTrace();}

		this.grilleImagesComplete = new Boolean[nbLignes][nbColonnes];
		this.grilleImages   = new BufferedImage[nbLignes][nbColonnes];

		ImageIcon imageIcon = new ImageIcon(image);

		int largeurImage = imageIcon.getIconWidth() / nbColonnes;
		int hauteurImage = imageIcon.getIconHeight() / nbLignes;

		for (int i = 0; i < nbColonnes; i++)
		{
			for (int j = 0; j < nbLignes; j++)
			{
				this.grilleImages[i][j] = image.getSubimage(i * largeurImage, j * hauteurImage, largeurImage, hauteurImage);
				this.grilleImagesComplete[i][j] = false;
			}
		}
	}
}
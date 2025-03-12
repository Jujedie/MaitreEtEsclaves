package MaitreEsclave;

import MaitreEsclave.Metier.Serveur;

import MaitreEsclave.IHM.FrameImage;

import java.awt.image.BufferedImage;

import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class Controleur
{
	@SuppressWarnings("unused")
	private FrameImage frameImage;

	private Serveur serveur;

	public Controleur(String nomFichier, int nbLig, int nbCol, int port)
	{
		this.serveur = new Serveur(nomFichier, nbLig, nbCol, port, this);

		this.frameImage = new FrameImage(this, serveur.getGrilleImages());

		try
		{
			// Lit le fichier et le transforme en image
			FileInputStream fichier = new FileInputStream("./" + nomFichier);
			BufferedImage image = ImageIO.read(fichier);

			// Initialisation du tableau des images
			BufferedImage imgs[][] = new BufferedImage[nbLig][nbCol];

			// Division de l'image en sous-images égales
			int largeurSousImage = image.getWidth() / nbCol;
			int hauteurSousImage = image.getHeight() / nbLig;

			// Itération des lignes et colonnes pour chaque sous-image
			for (int cpt1 = 0; cpt1 < nbLig; cpt1++)
			{
				for (int cpt2 = 0; cpt2 < nbCol; cpt2++)
				{
					// Création sous-image
					imgs[cpt1][cpt2] = new BufferedImage(largeurSousImage, hauteurSousImage, image.getType());
				}
			}

			//TODO: Utilisation des sous-images
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args) 
	{
		if (args.length != 4)
		{
			System.out.println("Erreur de format : java Controleur nomImage nombreColonnes nombreLignes port");
		}

		else
		{
			String nomFichier = args[0];

			if (!nomFichier.endsWith(".png") && !nomFichier.endsWith(".jpg"))
			{
				System.out.println("Fichier non supporté");
			}
			else
			{
				new Controleur(nomFichier, Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3]));
			}
		}
	}
}
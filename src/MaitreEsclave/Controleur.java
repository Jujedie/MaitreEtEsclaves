package MaitreEsclave;

import MaitreEsclave.IHM.FrameImage;
import MaitreEsclave.Metier.Serveur;

public class Controleur
{
	@SuppressWarnings("unused")
	private FrameImage frameImage;

	private Serveur serveur;

	public Controleur(String nomFichier, int nbLig, int nbCol, int port)
	{
		this.serveur = new Serveur(nomFichier, nbLig, nbCol, port, this);

		this.frameImage = new FrameImage(this, serveur.getGrilleImages());
	}

	public void majImage()
	{
		this.frameImage.getPanel().majImage();;
	}

	public void lancer()
	{
		this.serveur.start();
	}

	public static void main(String[] args)
	{
		if (args.length != 4)
		{
			System.out.println("Erreur de format : java Controleur nomImage nombreLignes nombreColonnes port");
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
				if ((Integer.parseInt(args[1]) < 1024 || Integer.parseInt(args[1]) > 65535) && Integer.parseInt(args[1]) != 5000)
				{
					System.out.println("Le numéro du port doit être entre 1024 et 65535 et différent de 5000");
					System.exit(1);
				} 
				else
				{
					new Controleur(nomFichier, Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3]));
				}
			}
		}
	}
}
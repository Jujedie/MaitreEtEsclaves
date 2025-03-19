package MaitreEsclave;

import MaitreEsclave.Metier.Serveur;

import MaitreEsclave.IHM.FrameImage;

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
				new Controleur(nomFichier, Integer.valueOf(args[1]), Integer.valueOf(args[2]), Integer.valueOf(args[3]));
			}
		}
	}
}
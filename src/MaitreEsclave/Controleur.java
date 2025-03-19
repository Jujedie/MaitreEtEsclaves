package MaitreEsclave;

import MaitreEsclave.Metier.Serveur;

import MaitreEsclave.IHM.FrameAccueil;
import MaitreEsclave.IHM.FrameImage;

public class Controleur
{
	private FrameImage   frameImage;
	private FrameAccueil frameAccueil;

	private Serveur serveur;

	public Controleur()
	{
		this.frameAccueil = new FrameAccueil(this);
	}

	public void majImage()
	{
		this.frameImage.getPanel().majImage();;
	}

	public void play()
	{
		// Launch the image changer
	}

	public Serveur getServeur()
	{
		return this.serveur;
	}

	public void creerServeur(String nomImg, int nbLig, int nbCol, int port)
	{
		this.serveur = new Serveur(nomImg, nbLig, nbCol, port, this);
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}
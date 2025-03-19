package MaitreEsclave;

import MaitreEsclave.IHM.FrameAccueil;
import MaitreEsclave.Metier.Serveur;

public class Controleur
{
	private FrameAccueil frameAccueil;

	private Serveur serveur;

	public Controleur()
	{
		this.frameAccueil = new FrameAccueil(this);
		this.serveur = null;
	}

	public void majImage()
	{
		this.frameAccueil.getFrameImage().getPanel().majImage();;
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

	public void lancer()
	{
		this.serveur.start();
	}

	
}
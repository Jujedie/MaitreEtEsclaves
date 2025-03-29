package MaitreEsclave;

import java.net.InetAddress;

import MaitreEsclave.IHM.FrameAccueil;
import MaitreEsclave.IHM.FrameServeur;
import MaitreEsclave.Metier.Client;
import MaitreEsclave.Metier.Serveur;

public class Controleur
{
	private FrameServeur frameServeur;

	private Serveur serveur;
	private Client  client;

	public Controleur()
	{
		new FrameAccueil(this);
		this.serveur = null;
		this.client  = null;
	}

	public Serveur getServeur()
	{
		return this.serveur;
	}

	public void setFrameServeur(FrameServeur frameServeur)
	{
		this.frameServeur = frameServeur;
	}

	public void majImage()
	{
		this.frameServeur.getFrameImage().getPanel().majImage();
	}

	public void creerServeur(String nomImg, int nbLig, int nbCol, String adress, int port)
	{
		this.serveur = new Serveur(nomImg, nbLig, nbCol, adress, port, this);
		this.serveur.start();
	}

	public void creerClient(InetAddress address, int port)
	{
		this.client = new Client(address, port);
		this.client.start();
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}
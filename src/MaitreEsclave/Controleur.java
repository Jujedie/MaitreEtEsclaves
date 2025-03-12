package MaitreEsclave;

import MaitreEsclave.IHM.FrameImage;

import MaitreEsclave.Metier.Serveur;

public class Controleur
{
	private FrameImage frameImage;

	private Serveur serveur;

	public Controleur()
	{
		this.frameImage = new FrameImage(this, /* serveur.methodeQuiRetourneBufferedTabImage */);
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}
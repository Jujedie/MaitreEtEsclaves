package MaitreEsclave.Metier;

import javax.swing.ImageIcon;

public class Serveur
{
	private Boolean[][] grilleImagesComplété;
	private ImageIcon[][]       grilleImages;

	public Serveur(String cheminImage,int nbColonnes, int nbLignes)
	{
		this.grilleImagesComplété = new Boolean[nbColonnes][nbLignes];
		this.grilleImages = new ImageIcon[nbColonnes][nbLignes];
	}
}
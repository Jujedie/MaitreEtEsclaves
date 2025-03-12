package MaitreEsclave;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Controleur
{
        
    public Controleur(String nomFichier, int nbCol, int nbLig) 
	{

		if (!nomFichier.endsWith(".png") || !nomFichier.endsWith(".jpg"))
		{
			nomFichier += ".png";
		}

		try {
				
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



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) 
	{
		if (args.length != 3)
		{
			System.out.println("Erreur de format : java Controleur nomImage nombreColonnes nombreLignes");
		}
		else
		{
			String nomFichier = args[0];
			
			if (nomFichier.contains(".") && !nomFichier.endsWith(".png") || !nomFichier.endsWith(".jpg"))
				System.out.println("Fichier non supporté");
			else
				new Controleur(nomFichier, Integer.valueOf(args[1]), Integer.valueOf(args[2]));
		}
		
		
	}

	
}
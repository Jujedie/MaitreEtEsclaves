package MaitreEsclave.IHM;

import MaitreEsclave.Controleur;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelImage extends JPanel implements ActionListener
{
    private JPanel panelImage;
    private JPanel panelBoutons;

    private JButton btnLancer;
    private JButton btnQuitter;

    private BufferedImage[][] tabImages;
    private Controleur controleur;

    public PanelImage(FrameImage frameImage, BufferedImage[][] tabImages, Controleur controleur)
    {
        this.tabImages = tabImages;
        this.controleur = controleur;

        this.setLayout(new BorderLayout());

        this.panelImage = new JPanel(); // Initialisation de panelImage
        this.majImage();

        this.panelBoutons = new JPanel(new GridLayout(1, 4, 20, 20));

        this.btnLancer  = new JButton("Lancer");
        this.btnQuitter = new JButton("Quitter");

        this.panelBoutons.add(new JLabel());
        this.panelBoutons.add(this.btnLancer);
        this.panelBoutons.add(this.btnQuitter);
        this.panelBoutons.add(new JLabel());

        this.add(this.panelImage, BorderLayout.CENTER);
        this.add(this.panelBoutons, BorderLayout.SOUTH);

        this.btnLancer.addActionListener(this);
        this.btnQuitter.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnLancer)
        {
            this.controleur.lancer();
            this.btnLancer.setEnabled(false);
        }

        if (e.getSource() == this.btnQuitter)
        {
            System.exit(0);
        }
    }

    public void majImage()
    {
        this.panelImage.removeAll(); // Supprime tous les composants existants

        this.panelImage.setLayout(new GridLayout(this.tabImages.length, this.tabImages[0].length, 5, 5));

        for (int lig = 0; lig < this.tabImages.length; lig++)
        {
            for (int col = 0; col < this.tabImages[0].length; col++)
            {
                this.panelImage.add(new JLabel(new ImageIcon(this.tabImages[lig][col])));
            }
        }

        this.revalidate(); // Force la mise à jour de la disposition
        this.repaint(); // Redessine le composant
    }
}
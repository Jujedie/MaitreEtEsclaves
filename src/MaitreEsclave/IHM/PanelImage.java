package MaitreEsclave.IHM;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelImage extends JPanel implements ActionListener
{
	private JPanel panelImage;
	private JPanel panelBoutons;

	private JButton btnQuitter;

	private BufferedImage[][] tabImages;

	public PanelImage(FrameImage frameImage, BufferedImage[][] tabImages)
	{
		this.tabImages = tabImages;

		this.setLayout(new BorderLayout());

		this.panelImage = new JPanel(); // Initialisation de panelImage
		this.majImage();

		this.panelBoutons = new JPanel(new GridLayout(1, 3, 20, 20));
		JScrollPane scrollPane = new JScrollPane(this.panelImage);
		this.btnQuitter = new JButton("Quitter");

		this.panelBoutons.add(new JLabel());
		this.panelBoutons.add(this.btnQuitter);
		this.panelBoutons.add(new JLabel());

		this.add(scrollPane, BorderLayout.CENTER);
		this.add(this.panelBoutons, BorderLayout.SOUTH);

		this.btnQuitter.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
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
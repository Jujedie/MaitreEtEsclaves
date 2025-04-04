package MaitreEsclave.IHM;

import MaitreEsclave.Controleur;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class FrameImage extends JFrame
{
	private Controleur ctrl;

	private PanelImage panelImage;

	public FrameImage(Controleur ctrl, BufferedImage[][] tabImages)
	{
		this.ctrl = ctrl;

		this.setTitle("Image");
		this.setSize(850, 500);
		this.setLocationRelativeTo(this.panelImage);
		this.setResizable(true);

		this.panelImage = new PanelImage(this, tabImages);

		this.add(this.panelImage);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	public Controleur getCtrl()
	{
		return this.ctrl;
	}

	public PanelImage getPanel()
	{
		return panelImage;
	}
}
package MaitreEsclave.IHM;

import MaitreEsclave.Controleur;

import javax.swing.JFrame;

import java.awt.image.BufferedImage;

public class FrameImage extends JFrame
{
	private Controleur ctrl;

	private PanelImage panelImage;

	public FrameImage(Controleur ctrl, BufferedImage[][] tabImages)
	{
		this.ctrl = ctrl;

		this.setTitle("Image");
		this.setSize(800, 400);
		this.setLocationRelativeTo(this.panelImage);
		this.setResizable(false);

		this.panelImage = new PanelImage(this, tabImages);

		this.add(this.panelImage);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	public Controleur getCtrl()
	{
		return this.ctrl;
	}
}
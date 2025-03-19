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
		this.setSize(tabImages[0][0].getWidth()*tabImages[0].length , tabImages[0][0].getHeight()*tabImages.length + 75 );
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
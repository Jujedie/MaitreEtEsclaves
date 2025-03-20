package MaitreEsclave.IHM;

import MaitreEsclave.Controleur;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class FrameImage extends JFrame {
	private Controleur ctrl;

	private PanelImage panelImage;

	public FrameImage(Controleur ctrl, BufferedImage[][] tabImages) {
		this.ctrl = ctrl;

		this.setTitle("Image");
		this.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		this.panelImage = new PanelImage(this, tabImages);

		JScrollPane scrollPane = new JScrollPane(this.panelImage);
		this.add(scrollPane);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	public Controleur getCtrl() {
		return this.ctrl;
	}

	public PanelImage getPanel() {
		return panelImage;
	}
}
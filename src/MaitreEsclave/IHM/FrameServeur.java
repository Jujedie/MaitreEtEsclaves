package MaitreEsclave.IHM;

import MaitreEsclave.Controleur;
import javax.swing.JFrame;

public class FrameServeur extends JFrame
{
	private Controleur ctrl;

	private PanelServeur panelServeur;

	public FrameServeur(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setTitle("Serveur");
		this.setSize(900, 550);
		this.setLocationRelativeTo(this.panelServeur);
		this.setResizable(false);

		this.panelServeur = new PanelServeur(this);

		this.add(this.panelServeur);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	public Controleur getCtrl()
	{
		return this.ctrl;
	}

	public FrameImage getFrameImage()
	{
		return this.panelServeur.getFrameImage();
	}
}
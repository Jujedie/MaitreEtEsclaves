package MaitreEsclave.IHM;

import MaitreEsclave.Controleur;
import javax.swing.JFrame;

public class FrameAccueil extends JFrame
{
	private Controleur ctrl;

	private PanelAccueil panelAccueil;

	public FrameAccueil(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setTitle("Accueil");
		this.setSize(500, 250);
		this.setLocationRelativeTo(this.panelAccueil);
		this.setResizable(false);

		this.panelAccueil = new PanelAccueil(this);

		this.add(this.panelAccueil);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	public Controleur getCtrl()
	{
		return this.ctrl;
	}
}
package MaitreEsclave.IHM;

import MaitreEsclave.Controleur;
import javax.swing.JFrame;

public class FrameParametre extends JFrame
{
	private Controleur ctrl;

	private PanelParametre panelParametre;

	public FrameParametre(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setTitle("Parametre");
		this.setSize(900, 450);
		this.setLocationRelativeTo(this.panelParametre);
		this.setResizable(false);

		this.panelParametre = new PanelParametre(this);

		this.add(this.panelParametre);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	public Controleur getCtrl()
	{
		return this.ctrl;
	}
}
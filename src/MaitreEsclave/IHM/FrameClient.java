package MaitreEsclave.IHM;

import javax.swing.JFrame;

import MaitreEsclave.Controleur;

public class FrameClient extends JFrame
{
	private Controleur ctrl;

	private PanelClient panelClient;

	public FrameClient(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.setTitle("Client");
		this.setSize(600, 300);
		this.setLocationRelativeTo(this.panelClient);
		this.setResizable(false);

		this.panelClient = new PanelClient(this);

		this.add(this.panelClient);

		this.setVisible(true);
	}

	public Controleur getCtrl()
	{
		return this.ctrl;
	}
}
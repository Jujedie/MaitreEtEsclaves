package MaitreEsclave.IHM;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelAccueil extends JPanel implements ActionListener
{
	private FrameAccueil frameAccueil;
	private FrameServeur frameServeur;
	@SuppressWarnings("unused")
	private FrameClient frameClient;

	private JPanel panelBoutons;

	private JButton btnServeur;
	private JButton btnClient;
	private JButton btnQuitter;

	public PanelAccueil(FrameAccueil frameAccueil)
	{
		this.frameAccueil = frameAccueil;
		this.frameServeur = null;
		this.frameClient  = null;

		this.setLayout(new BorderLayout(60, 20));

		this.panelBoutons = new JPanel(new GridLayout(3, 1, 20, 20));

		this.btnServeur = new JButton("Serveur");
		this.btnClient  = new JButton("Client" );
		this.btnQuitter = new JButton("Quitter");

		this.panelBoutons.add(this.btnServeur);
		this.panelBoutons.add(this.btnClient);
		this.panelBoutons.add(this.btnQuitter);

		this.add(new JPanel(), BorderLayout.NORTH);
		this.add(new JPanel(), BorderLayout.WEST);
		this.add(this.panelBoutons, BorderLayout.CENTER);
		this.add(new JPanel(), BorderLayout.EAST);
		this.add(new JPanel(), BorderLayout.SOUTH);

		this.btnServeur.addActionListener(this);
		this.btnClient.addActionListener(this);
		this.btnQuitter.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnServeur)
		{
			this.frameServeur = new FrameServeur(this.frameAccueil.getCtrl());
			this.frameAccueil.getCtrl().setFrameServeur(frameServeur);
			this.btnServeur.setEnabled(false);
		}

		if (e.getSource() == this.btnClient)
		{
			this.frameClient = new FrameClient(this.frameAccueil.getCtrl());
		}

		if (e.getSource() == this.btnQuitter)
		{
			System.exit(0);
		}
	}
}
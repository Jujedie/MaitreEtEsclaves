package MaitreEsclave.IHM;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelAccueil extends JPanel implements ActionListener
{
	private FrameAccueil   frameAccueil;
	@SuppressWarnings("unused")
	private FrameParametre frameParametre;
	private FrameImage     frameImage;

	private JPanel panelBoutons;

	private JButton btnLancer;
	private JButton btnParametre;
	private JButton btnQuitter;

	public PanelAccueil(FrameAccueil frameAccueil)
	{
		this.frameAccueil   = frameAccueil;
		this.frameParametre = null;
		this.frameImage     = null;

		this.setLayout(new BorderLayout(60, 20));

		this.panelBoutons = new JPanel(new GridLayout(3, 1, 20, 20));

		this.btnLancer    = new JButton("Lancer");
		this.btnParametre = new JButton("Parametre");
		this.btnQuitter   = new JButton("Quitter");

		this.panelBoutons.add(this.btnLancer);
		this.panelBoutons.add(this.btnParametre);
		this.panelBoutons.add(this.btnQuitter);

		this.add(new JPanel(), BorderLayout.NORTH);
		this.add(new JPanel(), BorderLayout.WEST);
		this.add(this.panelBoutons, BorderLayout.CENTER);
		this.add(new JPanel(), BorderLayout.EAST);
		this.add(new JPanel(), BorderLayout.SOUTH);

		this.btnLancer.addActionListener(this);
		this.btnParametre.addActionListener(this);
		this.btnQuitter.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnLancer)
		{
			// Lancement de la decentralisation des processus
			this.frameImage = new FrameImage(this.frameAccueil.getCtrl(), this.frameAccueil.getCtrl().getServeur().getGrilleImages());
		}

		if (e.getSource() == this.btnParametre)
		{
			this.frameParametre = new FrameParametre(this.frameAccueil.getCtrl());
		}

		if (e.getSource() == this.btnQuitter)
		{
			System.exit(0);
		}
	}
}
package MaitreEsclave.IHM;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelServeur extends JPanel implements ActionListener
{
	private FrameServeur frameServeur;
	private FrameImage   frameImage;

	private JPanel panelParam;

	private JFileChooser fcImage;

	private JTextField txtNbLignes;
	private JTextField txtNbColonnes;
	private JTextField txtAdress;
	private JTextField txtPort;

	private JButton btnImage;
	private JButton btnLancer;

	private String nomImage;

	public PanelServeur(FrameServeur frameServeur)
	{
		this.frameServeur = frameServeur;
		this.frameImage   = null;
		this.nomImage     = null;

		this.setLayout(new BorderLayout(240, 20));

		this.panelParam = new JPanel(new GridLayout(7, 1, 20, 20));

		this.txtNbColonnes = new JTextField();
		this.txtNbLignes   = new JTextField();
		this.txtAdress     = new JTextField();
		this.txtPort       = new JTextField();

		txtNbColonnes.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder("Nombre de lignes :"  ), txtNbColonnes.getBorder()));
		txtNbLignes  .setBorder(BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder("Nombre de colonnes :"), txtNbLignes  .getBorder()));
		txtAdress    .setBorder(BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder("Adresse IP :"        ), txtAdress    .getBorder()));
		txtPort      .setBorder(BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder("Port :"              ), txtPort      .getBorder()));

		this.btnImage  = new JButton("Choisir l'image");
		this.btnLancer = new JButton("Lancer");

		this.panelParam.add(this.txtNbLignes);
		this.panelParam.add(this.txtNbColonnes);
		this.panelParam.add(this.txtAdress);
		this.panelParam.add(this.txtPort);
		this.panelParam.add(new JLabel("Attention l'image ne doit contenir de partie transparent."));
		this.panelParam.add(this.btnImage);
		this.panelParam.add(this.btnLancer);

		this.add(new JPanel(), BorderLayout.NORTH);
		this.add(new JPanel(), BorderLayout.WEST);
		this.add(this.panelParam, BorderLayout.CENTER);
		this.add(new JPanel(), BorderLayout.EAST);
		this.add(new JPanel(), BorderLayout.SOUTH);

		this.txtNbLignes.addActionListener(this);
		this.txtNbColonnes.addActionListener(this);
		this.txtAdress.addActionListener(this);
		this.txtPort.addActionListener(this);

		this.btnImage.addActionListener(this);
		this.btnLancer.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnImage)
		{
			this.fcImage = new JFileChooser();
			this.fcImage.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png"));

			int returnVal = this.fcImage.showOpenDialog(this.frameServeur);

			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				java.io.File fichierSelect = this.fcImage.getSelectedFile();

				this.nomImage = fichierSelect.getAbsolutePath();
			}
		}

		if (e.getSource() == this.btnLancer)
		{
			if (this.nomImage == null)
			{
				JOptionPane.showMessageDialog(this, "Veuillez choisir une image", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (Integer.parseInt(this.txtNbColonnes.getText()) <= 1)
			{
				JOptionPane.showMessageDialog(this, "Le nombre de colonnes doit être positif et supérieur à 1", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (Integer.parseInt(this.txtNbLignes.getText()) <= 1 )
			{
				JOptionPane.showMessageDialog(this, "Le nombre de lignes doit être positif et supérieur à 1", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (Integer.parseInt(this.txtPort.getText()) < 1024 || Integer.parseInt(this.txtPort.getText()) > 65535 || Integer.parseInt(this.txtPort.getText()) == 5000)
			{
				JOptionPane.showMessageDialog(this, "Le port doit être compris entre 1024 et 65535 et ne doit pas être égal à 5000", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			this.frameServeur.getCtrl().creerServeur
			(
				this.nomImage,
				Integer.parseInt(this.txtNbLignes.getText())  ,
				Integer.parseInt(this.txtNbColonnes.getText()),
				this.txtAdress.getText(),
				Integer.parseInt(this.txtPort      .getText())
			);

			if (this.frameServeur.getCtrl().getServeur() != null)
			{
				this.frameImage = new FrameImage(this.frameServeur.getCtrl(), this.frameServeur.getCtrl().getServeur().getGrilleImages());
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Impossible de lancer, le serveur n'a pas été paramétré.", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			this.frameServeur.dispose();
		}
	}

	public FrameImage getFrameImage()
	{
		return this.frameImage;
	}
}
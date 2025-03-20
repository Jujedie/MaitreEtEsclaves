package MaitreEsclave.IHM;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelParametre extends JPanel implements ActionListener
{
	private FrameParametre frameParametre;

	private JPanel panelParam;

	private JFileChooser fcImage;

	private JTextField   txtNbLignes;
	private JTextField   txtNbColonnes;
	private JTextField   txtPort;

	private JButton btnImage;
	private JButton btnEnregistrer;

	private String nomImage;

	public PanelParametre(FrameParametre frameParametre)
	{
		this.frameParametre   = frameParametre;
		this.nomImage = null;

		this.setLayout(new BorderLayout(240, 20));

		this.panelParam = new JPanel(new GridLayout(5, 1, 20, 20));

		this.txtNbColonnes = new JTextField();
		this.txtNbLignes   = new JTextField();
		this.txtPort       = new JTextField();

		txtNbColonnes.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder("Nombre de lignes :"  ), txtNbColonnes.getBorder()));
		txtNbLignes  .setBorder(BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder("Nombre de colonnes :"), txtNbLignes  .getBorder()));
		txtPort      .setBorder(BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder("Port :"              ), txtPort      .getBorder()));

		this.btnImage  = new JButton("Choisir l'image");
		this.btnEnregistrer = new JButton("Enregistrer");

		this.panelParam.add(this.txtNbLignes);
		this.panelParam.add(this.txtNbColonnes);
		this.panelParam.add(this.txtPort);
		this.panelParam.add(this.btnImage);
		this.panelParam.add(this.btnEnregistrer);

		this.add(new JPanel(), BorderLayout.NORTH);
		this.add(new JPanel(), BorderLayout.WEST);
		this.add(this.panelParam, BorderLayout.CENTER);
		this.add(new JPanel(), BorderLayout.EAST);
		this.add(new JPanel(), BorderLayout.SOUTH);

		this.txtNbLignes.addActionListener(this);
		this.txtNbColonnes.addActionListener(this);
		this.txtPort.addActionListener(this);

		this.btnImage.addActionListener(this);
		this.btnEnregistrer.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnImage)
		{
			this.fcImage = new JFileChooser();
			this.fcImage.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png"));

			int returnVal = this.fcImage.showOpenDialog(this.frameParametre);

			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				java.io.File fichierSelect = this.fcImage.getSelectedFile();

				this.nomImage = fichierSelect.getAbsolutePath();
			}
		}

		if (e.getSource() == this.btnEnregistrer)
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

			this.frameParametre.getCtrl().creerServeur
			(
				this.nomImage, 
				Integer.parseInt(this.txtNbLignes.getText())  ,
				Integer.parseInt(this.txtNbColonnes.getText()),
				Integer.parseInt(this.txtPort      .getText())
			);

			this.frameParametre.dispose();
		}
	}
}
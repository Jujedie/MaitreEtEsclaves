package MaitreEsclave.IHM;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelClient extends JPanel implements ActionListener
{
	private FrameClient frameClient;

	private JPanel panelParam;

	private JTextField   txtAdress;
	private JTextField   txtPort;

	private JButton btnConnexion;

	public PanelClient(FrameClient frameClient)
	{
		this.frameClient = frameClient;

		this.setLayout(new BorderLayout(100, 20));

		this.panelParam = new JPanel(new GridLayout(3, 1, 20, 20));

		this.txtAdress     = new JTextField();
		this.txtPort       = new JTextField();

		this.txtAdress.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder("Adresse IP :"), txtAdress.getBorder()));
		this.txtPort  .setBorder(BorderFactory.createCompoundBorder( BorderFactory.createTitledBorder("Port :"      ), txtPort  .getBorder()));

		this.btnConnexion = new JButton("Se connecter");

		this.panelParam.add(this.txtAdress);
		this.panelParam.add(this.txtPort);
		this.panelParam.add(this.btnConnexion);

		this.add(new JPanel(), BorderLayout.NORTH);
		this.add(new JPanel(), BorderLayout.WEST);
		this.add(this.panelParam, BorderLayout.CENTER);
		this.add(new JPanel(), BorderLayout.EAST);
		this.add(new JPanel(), BorderLayout.SOUTH);

		this.txtAdress.addActionListener(this);
		this.txtPort.addActionListener(this);

		this.btnConnexion.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnConnexion)
		{
			if (Integer.parseInt(this.txtPort.getText()) < 1024 || Integer.parseInt(this.txtPort.getText()) > 65535 || Integer.parseInt(this.txtPort.getText()) == 5000)
			{
				JOptionPane.showMessageDialog(this, "Le port doit être compris entre 1024 et 65535 et ne doit pas être égal à 5000", "Erreur", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try
			{
				this.frameClient.getCtrl().creerClient(InetAddress.getByName(this.txtAdress.getText()), Integer.parseInt(this.txtPort.getText()));
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}

			this.frameClient.dispose();
		}
	}
}
package MaitreEsclave.Metier;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

import javax.imageio.ImageIO;

public class Client extends Thread
{
	private BufferedImage  image;
	private DatagramSocket socket;
	private InetAddress    address;

	private int port;

	public Client(InetAddress address, int port)
	{
		this.image   = null;
		this.address = address;
		this.port    = port;

		try
		{
			this.socket = new DatagramSocket();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		try
		{
			while (true)
			{
				// Envoie "awaiting task" au server
				byte[] sendData = "awaiting task".getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
				socket.send(sendPacket);

				//Reçoi la tache envoyé par le serveur server
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(receivePacket);
				String task = new String(receivePacket.getData(), 0, receivePacket.getLength());

				// Séparer la tache des coordonnée du BufferedImage
				String[] taskParts = task.split(" ");
				String taskType = taskParts[0];
				int x = Integer.parseInt(taskParts[1]);
				int y = Integer.parseInt(taskParts[2]);

				// Recevoir la taille de l'image en bits
				byte[] sizeData = new byte[512];
				receivePacket = new DatagramPacket(sizeData, sizeData.length);
				socket.receive(receivePacket);
				int imageSize = Integer.parseInt(new String(receivePacket.getData(), 0, receivePacket.getLength()));

				// Recevoir l'image
				receiveData = new byte[imageSize];
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				socket.receive(receivePacket);

				byte[] imageData = receivePacket.getData();
				ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
				this.image = ImageIO.read(bais);

				if (taskType.equals("invert"))
				{
					invertColors();
				}
				else
				{
					permuteRedBlueGreen();
				}

				// Convertis l'image en tableau de bits
				byte[] resultData = BufferedImageToByteArray(this.image);

				// Envoie l'image modifié au serveur
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				baos.write(x);
				baos.write(y);
				baos.write(resultData);

				sendData = baos.toByteArray();
				sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
				socket.send(sendPacket);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	private void invertColors()
	{
		for (int y = 0; y < this.image.getHeight(); y++)
		{
			for (int x = 0; x < this.image.getWidth(); x++)
			{
				int rgb = this.image.getRGB(x, y);
				int alpha = (rgb >> 24) & 0xff;
				int red = (rgb >> 16) & 0xff;
				int green = (rgb >> 8) & 0xff;
				int blue = rgb & 0xff;
				red = 255 - red;
				blue = 255 - blue;
				green = 255 - green;
				int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
				this.image.setRGB(x, y, newRgb);
			}
		}
	}

	private void permuteRedBlueGreen()
	{
		for (int y = 0; y < this.image.getHeight(); y++)
		{
			for (int x = 0; x < this.image.getWidth(); x++)
			{
				int rgb = this.image.getRGB(x, y);
				int alpha = (rgb >> 24) & 0xff;
				int red = (rgb >> 16) & 0xff;
				int green = (rgb >> 8) & 0xff;
				int blue = rgb & 0xff;
				int newRgb = (alpha << 24) | (green << 16) | (blue << 8) | red;
				this.image.setRGB(x, y, newRgb);
			}
		}
	}


	public static byte[] BufferedImageToByteArray(BufferedImage buffer)
	{
		byte[] bytes = null;
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(buffer, "jpg", baos);
			bytes = baos.toByteArray();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return bytes;
	}

	public static void main(String[] args)
	{
		if (args.length != 2)
		{
			System.out.println("Usage: java Client <address> <port>");
			System.exit(1);
		}
		if (Integer.parseInt(args[1]) < 1024 || Integer.parseInt(args[1]) > 65535)
		{
			System.out.println("Le numéro du port doit être entre 1024 et 65535");
			System.exit(1);
		}
		
		try
		{
			Client client = new Client(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));
			client.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
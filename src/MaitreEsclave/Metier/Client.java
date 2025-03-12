package MaitreEsclave.Metier;

import java.awt.image.BufferedImage;

public class Client
{
	private BufferedImage   image;
	private DatagramSocket socket;
	private InetAddress   address;

	public Client()
	{

	}
	private void invertColors()
	{
		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = 0; x < image.getWidth(); x++)
			{
				int rgb = image.getRGB(x, y);
				int alpha = (rgb >> 24) & 0xff;
				int red = (rgb >> 16) & 0xff;
				int green = (rgb >> 8) & 0xff;
				int blue = rgb & 0xff;
				red = 255 - red;
				blue = 255 - blue;
				green = 255 - green;
				int newRgb = (alpha << 24) | (red << 16) | (green << 8) | blue;
				image.setRGB(x, y, newRgb);
			}
		}
	}

	private void permuteRedBlueGreen(BufferedImage image)
	{
		for (int y = 0; y < image.getHeight(); y++)
		{
			for (int x = 0; x < image.getWidth(); x++)
			{
				int rgb = image.getRGB(x, y);
				int alpha = (rgb >> 24) & 0xff;
				int red = (rgb >> 16) & 0xff;
				int green = (rgb >> 8) & 0xff;
				int blue = rgb & 0xff;
				int newRgb = (alpha << 24) | (green << 16) | (blue << 8) | red;
				image.setRGB(x, y, newRgb);
			}
		}
	}
}
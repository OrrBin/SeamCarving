package seamcarving;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) throws IOException {
		if(args.length == 0 || args.length < 5) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}
		
		String inputFileName = args[0];
		int numOfCol = Integer.valueOf(args[1]);
		int numOfRow = Integer.valueOf(args[2]);
		int energyType = Integer.valueOf(args[3]);
		String outputFileName = args[4];

		BufferedImage img = ImageIO.read(new File(inputFileName));
		File imgdst = new File(outputFileName);		
		Color c;
		
		for(int i = 0; i < img.getWidth();i++)
	        for(int j = 0; j < img.getHeight(); j ++)
	        {
	        	if (j % 2 == 0)
	        		continue;
	        	c = new Color(img.getRGB(i,j));
	            System.out.println(c.getRed() + " " +c.getGreen() +" "+ c.getBlue());
	        }
		
		ImageIO.write(img,"jpg", imgdst);

	}

}

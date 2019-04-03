package seamcarving;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seamcarving.algorithm.EnergyFunction;
import seamcarving.algorithm.EntropyEnergyFunction;

public class Test {

	public static void main(String[] args) throws IOException {
		if(args.length == 0 || args.length < 5) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}
		
		String inputFileName = args[0];
		int numOfCol = Integer.valueOf(args[1]);
		int numOfRow = Integer.valueOf(args[2]);
		int energyType = Integer.valueOf(args[3]);
		String outputFileName = args[4];
		double energy;
		
		BufferedImage img = ImageIO.read(new File(inputFileName));
		BufferedImage imgOut = ImageIO.read(new File(inputFileName));
		File imgdst = new File(outputFileName);		
		Color c;
//		EnergyFunction ef = new BasicEnergyFunction();
		EnergyFunction ef = new EntropyEnergyFunction();
		
		for(int i = 0; i < img.getWidth();i++)
	        for(int j = 0; j < img.getHeight(); j ++)
	        {
	        	 energy = ef.calculateEnergyForPixel(img, i, j, );
	        	 imgOut.setRGB(i, j, (int)(energy));
	        }
		
		ImageIO.write(imgOut,"jpg", imgdst);

	}
}

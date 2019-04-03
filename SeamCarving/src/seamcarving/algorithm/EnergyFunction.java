package seamcarving.algorithm;

import java.awt.image.BufferedImage;

public interface EnergyFunction {
	
	double[][] getEnergyMap(BufferedImage img);
	
	double calculateEnergyForPixel(BufferedImage img, int x, int y);
}
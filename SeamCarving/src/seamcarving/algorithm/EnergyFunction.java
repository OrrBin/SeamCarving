package seamcarving.algorithm;

import java.awt.image.BufferedImage;

public interface EnergyFunction {
	double calculateEnergyForPixel(BufferedImage img, int x, int y);
}
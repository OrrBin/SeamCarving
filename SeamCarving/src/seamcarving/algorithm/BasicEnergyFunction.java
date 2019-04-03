package seamcarving.algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class BasicEnergyFunction implements EnergyFunction {

	@Override
	public double calculateEnergyForPixel(BufferedImage img, int x, int y) {
		int startI = Math.max(x - 1, 0), endI = Math.min(img.getWidth() - 1, x + 1);
		int startJ = Math.max(y - 1, 0), endJ = Math.min(img.getHeight() - 1, y + 1);
		Color c = new Color(img.getRGB(x, y)), currColor;
		int r = c.getRed(), g = c.getGreen(), b = c.getBlue();
		int neighboursNum = 0;
		double valDiff, totalDiff = 0;

		for (int i = startI; i <= endI; i++) {
			for (int j = startJ; j <= endJ; j++) {
				neighboursNum++;
				currColor = new Color(img.getRGB(i, j));
				valDiff = 0;
				valDiff += Math.abs(currColor.getRed() - r);
				valDiff += Math.abs(currColor.getGreen() - g);
				valDiff += Math.abs(currColor.getBlue() - b);
				valDiff /= 3;
				totalDiff += valDiff;
			}
		}

		totalDiff /= neighboursNum;

		return totalDiff;
	}

	@Override
	public double[][] getEnergyMap(BufferedImage img) {
		double[][] heatMap = new double[img.getHeight()][img.getWidth()];
		
		
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				heatMap[j][i] = calculateEnergyForPixel(img,i,j);
			}
		}

		return heatMap;
	}
}
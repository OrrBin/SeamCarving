package seamcarving.algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class EntropyEnergyFunction implements EnergyFunction {

	private BasicEnergyFunction basicFunc;
	private double[][] pArr;
	
	public EntropyEnergyFunction(BufferedImage img) {
		basicFunc = new BasicEnergyFunction();
		pArr = new double[img.getWidth()][img.getHeight()];
	}

	@Override
	public double calculateEnergyForPixel(BufferedImage img, int x, int y) {
		double energy = basicFunc.calculateEnergyForPixel(img, x, y);
		double entropy = calculateEntropyForPixel(img, x, y);
		double w = 0.9;
		return (w * energy + (1 - w) * entropy);
	}


	private int getGrayScale(BufferedImage img, int x, int y) {
		Color c = new Color(img.getRGB(x, y)), currColor;
		int r = c.getRed(), g = c.getGreen(), b = c.getBlue();
		int avg = (r + g + b) / 3;
		return new Color(avg, avg, avg).getRGB();
	}

	private double calculateEntropyForPixel(BufferedImage img, int x, int y) {
		int startI = Math.max(x - 4, 0), endI = Math.min(img.getWidth() - 1, x + 4);
		int startJ = Math.max(y - 4, 0), endJ = Math.min(img.getHeight() - 1, y + 4);
		int grayScale = getGrayScale(img, x, y);
		int neighboursNum = 0;
		double H = 0, P;

		for (int i = startI; i <= endI; i++) {
			for (int j = startJ; j <= endJ; j++) {
				if(pArr[i][j] == 0) {
					pArr[i][j] = getP(img, i, j);
				} 
				P = pArr[i][j];
				H += P * Math.log(P);
			}
		}

		return -H;
	}

	private double getP(BufferedImage img, int x, int y) {
		int startI = Math.max(x - 4, 0), endI = Math.min(img.getWidth() - 1, x + 4);
		int startJ = Math.max(y - 4, 0), endJ = Math.min(img.getHeight() - 1, y + 4);
		int grayScale = getGrayScale(img, x, y);
		int neighboursNum = 0;
		double P = 0;

		for (int i = startI; i <= endI; i++) {
			for (int j = startJ; j <= endJ; j++) {
				P += getGrayScale(img, i, j);
			}
		}

		P = grayScale / P;

		return P;
	}
}

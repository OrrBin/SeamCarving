package seamcarving.algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class EntropyEnergyFunction implements EnergyFunction {

	private BasicEnergyFunction basicFunc;

	public EntropyEnergyFunction() {
		basicFunc = new BasicEnergyFunction();
	}

	private int getGrayScale(BufferedImage img, int x, int y) {
		Color c = new Color(img.getRGB(x, y)), currColor;
		int r = c.getRed(), g = c.getGreen(), b = c.getBlue();
		int avg = (r + g + b) / 3;
		return new Color(avg, avg, avg).getRGB();
	}

	@Override
	public double calculateEnergyForPixel(BufferedImage img, int x, int y) {
		double energy = basicFunc.calculateEnergyForPixel(img, x, y);
		double entropy = calculateEntropyForPixel(img, x, y);
		double w = 0.5;
		return (w * energy + (1 - w) * entropy);
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

	private double calculateEntropyForPixel(BufferedImage img, int x, int y, double[][] Parray) {

		int startI = Math.max(x - 4, 0), endI = Math.min(img.getWidth() - 1, x + 4);
		int startJ = Math.max(y - 4, 0), endJ = Math.min(img.getHeight() - 1, y + 4);
		int grayScale = getGrayScale(img, x, y);
		int neighboursNum = 0;
		double H = 0, P;
		
		
		for (int i = startI; i <= endI; i++) {
			for (int j = startJ; j <= endJ; j++) {
				if (Parray[i][j] == 0)
					Parray[i][j] = getP(img, i, j);
				
				H += Parray[i][j] * Math.log(Parray[i][j]);
			}
		}

		return -H;
	}
}

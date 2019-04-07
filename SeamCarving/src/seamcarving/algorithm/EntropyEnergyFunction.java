package seamcarving.algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

import seamcarving.Util;

public class EntropyEnergyFunction implements EnergyFunction {

	private BasicEnergyFunction basicFunc;
	private double[][] pArr;

	public EntropyEnergyFunction() {
		basicFunc = new BasicEnergyFunction();
	}

	@Override
	public double calculateEnergyForPixel(int[][] img, int i, int j) {
		double energy = basicFunc.calculateEnergyForPixel(img, i, j);
		double entropy = calculateEntropyForPixel(img, i, j);
		double w = 0;
		return (w * energy + (1 - w) * entropy);
	}

	private int getGrayScale(int[][] img, int i, int j) {
		Color c = new Color(img[i][j]);
		int r = c.getRed(), g = c.getGreen(), b = c.getBlue();
		int avg = (r + g + b) / 3;
		return new Color(avg, avg, avg).getRGB();
	}

	private double calculateEntropyForPixel(int[][] img, int i, int j) {
		int startK = Math.max(j - 4, 0), endK = Math.min(img[0].length - 1, j + 4);
		int startL = Math.max(i - 4, 0), endL = Math.min(img.length - 1, i + 4);
		double H = 0, P;

		for (int l = startL; l <= endL; l++) {
			for (int k = startK; k <= endK; k++) {
				if (pArr[l][k] == 0) {
					pArr[l][k] = getP(img, l, k);
				}
				P = pArr[l][k];
				H += P * Math.log(P);
			}
		}

		return -H;
	}

	private double getP(int[][] img, int i, int j) {
		int startK = Math.max(j - 4, 0), endK = Math.min(img[0].length - 1, j + 4);
		int startL = Math.max(i - 4, 0), endL = Math.min(img.length - 1, i + 4);
		int grayScale = getGrayScale(img, i, j);
		double P = 0;

		for (int l = startL; l <= endL; l++) {
			for (int k = startK; k <= endK; k++) {
				P += getGrayScale(img, l, k);
			}
		}

		P = grayScale / P;

		return P;
	}

	@Override
	public double[][] getEnergyMap(int[][] img) {
		pArr = new double[img.length][img[0].length];	
		
		int height = img.length;
		int width = img[0].length;
		double[][] heatMap = new double[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				heatMap[i][j] = calculateEnergyForPixel(img, i, j);
			}
		}
		
		return heatMap;
	}
}

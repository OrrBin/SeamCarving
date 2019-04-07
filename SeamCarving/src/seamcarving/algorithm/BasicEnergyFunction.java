package seamcarving.algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

import seamcarving.Util;

public class BasicEnergyFunction implements EnergyFunction {

	@Override
	public double calculateEnergyForPixel(int[][] img, int i, int j) { // x - row, y - column
		int height = img.length;
		int width = img[0].length;
		int startI = Math.max(i - 1, 0), endI = Math.min(height - 1, i + 1);
		int startJ = Math.max(j - 1, 0), endJ = Math.min(width - 1, j + 1);
		Color c = new Color(img[i][j]), currColor;
		int r = c.getRed(), g = c.getGreen(), b = c.getBlue();
		int neighboursNum = -1; // excludes self
		double valDiff, totalDiff = 0;

		for (int k = startI; k <= endI; k++) {
			for (int l = startJ; l <= endJ; l++) {
				neighboursNum++;
				currColor = new Color(img[k][l]);
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
	public double[][] getEnergyMap(int[][] img) {
		int height = img.length;
		int width = img[0].length;
		double[][] heatMap = new double[height][width];
		
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				heatMap[i][j] = calculateEnergyForPixel(img,i,j);
			}
		}
		
//		Util.printArr(heatMap);
		return heatMap;
	}
}
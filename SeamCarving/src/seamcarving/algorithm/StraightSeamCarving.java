package seamcarving.algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.rmi.server.UnicastRemoteObject;

import seamcarving.Util;

public class StraightSeamCarving implements SeamCarving {

	private int getOptimalSeam(double[][] heatMap) {
		double sum = 0;
		double min = Double.POSITIVE_INFINITY;
		int minIndex = -1;
		int height = heatMap.length, width = heatMap[0].length;

		for (int i = 0; i < width; i++) {
			sum = 0;
			for (int j = 0; j < height; j++) {
				sum += heatMap[j][i];
			}

			if (sum < min) {
				min = sum;
				minIndex = i;
			}
		}

		return minIndex;
	}

	@Override
	public int[][] vertical(int[][] img, int numOfColumns, EnergyFunction func) {
		boolean cut = false;
		int height = img.length, width = img[0].length;
		double[][] heatMap  = func.getEnergyMap(img);
		int opt;

		for (int i = 0; i < numOfColumns; i++) {

			if (cut) {
				heatMap = func.getEnergyMap(img);
				opt = getOptimalSeam(heatMap);
				img = Util.shiftImage(img, opt);
			} else {
				opt = getOptimalSeam(heatMap);
				for (int j = 0; j < width; j++) {
					int col = 255 - (i % 255);
					img[opt][j] = new Color(col, col, col).getRGB();
					heatMap[opt][j] = Double.POSITIVE_INFINITY;
				}
			}
		}

		return img;
	
	}

	@Override
	public int[][] horizontal(int[][] img, int numOfRows, EnergyFunction func) {
		
		return img;
	}

}

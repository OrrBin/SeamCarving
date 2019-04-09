package seamcarving.algorithm;

import java.awt.Color;

import seamcarving.Util;

public class StraightSeamCarving implements SeamCarving {

	public int getOptimalSeam(double[][] heatMap) {
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
		if (numOfColumns == 0)
			return img;

		if (numOfColumns > 0) {
			return verticalRemove(img, numOfColumns, func);
		}

		return verticalAdd(img, -numOfColumns, func);
	}

	private int[][] verticalRemove(int[][] img, int numOfColumns, EnergyFunction func) { // Decrease image size
		boolean cut = true;
		int height = img.length;
		double[][] heatMap = func.getEnergyMap(img);
		int opt;
		
		for (int i = 0; i < numOfColumns; i++) {

			if (cut) {
				heatMap = func.getEnergyMap(img);
				opt = getOptimalSeam(heatMap);
				img = Util.shrinkImage(img, opt);
			} else {
				opt = getOptimalSeam(heatMap);
				for (int j = 0; j < height; j++) {
					int col = (i % 255);
					img[j][opt] = new Color(255, col, col).getRGB();
					heatMap[j][opt] = Double.POSITIVE_INFINITY;
				}
			}
		}
		
		return img;
	}

	private int[][] verticalAdd(int[][] img, int numOfColumns, EnergyFunction func) { // Increase image size
		int height = img.length;
		double[][] heatMap = func.getEnergyMap(img);
		int opt;
		int[][] optimalSeams = new int[numOfColumns][height];
		
//		System.out.println("Heatmap before:");
//		Util.printArr(heatMap);
		
		for (int i = 0; i < numOfColumns; i++) {
			opt = getOptimalSeam(heatMap);
			for (int j = 0; j < height; j++) {
				// int col = (i % 255);
				// img[j][opt] = new Color(255, col, col).getRGB();
				heatMap[j][opt] += 0.5;
				optimalSeams[i][j] = opt;
			}
//			System.out.println("Heatmap after iteation "+i+":");
//			Util.printArr(heatMap);
		}
		
		img = Util.enlargeImage(img, optimalSeams);
		return img;
	}
}

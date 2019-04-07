package seamcarving.algorithm;

import java.awt.Color;

import seamcarving.Util;

public class DiagonalSeamCarving implements SeamCarving {
	
	// x - column, y - row, shortestPath[i][j] - minimal path sum from [i,j] to the
	private double getOptimalPaths(double[][] heatMap, double[][] shortestPath, int i, int j) {

//		System.out.println("getOptimlPaths, i, j:" + i + ", " + j);
		if (i == 0) {
			shortestPath[i][j] = heatMap[i][j];
			return shortestPath[i][j];
		}
		double min = Double.POSITIVE_INFINITY;
		double tmp;

		int start = Math.max(j - 1, 0), end = Math.min(j + 1, heatMap[0].length - 1);
		for (int k = start; k <= end; k++) { // iterate over 3 top
																								// neighbors
			if (shortestPath[i - 1][k] != Double.POSITIVE_INFINITY) {
				tmp = shortestPath[i - 1][k];
			} else {
				tmp = getOptimalPaths(heatMap, shortestPath, i - 1, k);
			}

			if (tmp < min) {
				min = tmp;
			}
		}

		shortestPath[i][j] = heatMap[i][j] + min;
		return shortestPath[i][j];
	}

	public int[] getOptimalSeam(double[][] heatMap, boolean isVertical) {
		double sum = 0;
		double min = Double.POSITIVE_INFINITY;
		int minIndex = -1;
		int width = heatMap[0].length, height = heatMap.length;

		double[][] shortestPath = new double[height][width]; // entry [i][j] will hold the weight of the shortest path
																// from pixel (i,j) to the top
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				shortestPath[i][j] = Double.POSITIVE_INFINITY;
			}
		}
		for (int j = 0; j < width; j++) {
//			System.out.println("Getting optimal path for column: " + j);
			getOptimalPaths(heatMap, shortestPath, height - 1, j);
		}
//		
//		for (int i = 0; i < height; i++) {
//			for (int j = 0; j < width; j++) {
//				System.out.print(shortestPath[i][j]+" ");
//				if (shortestPath[i][j] != 0 && shortestPath[i][j] != Double.POSITIVE_INFINITY)
//					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
//			}
//			System.out.println();
//		}

		int[] optimalSeam = new int[height];

		for (int j = 0; j < width; j++) { // restore optimal seam from shortestPath
			if (shortestPath[height - 1][j] < min) {
				min = shortestPath[height - 1][j];
				minIndex = j;
			}
		}

		optimalSeam[height - 1] = minIndex;

		for (int i = height - 2; i >= 0; i--) {
			int start = Math.max(minIndex - 1, 0), end = Math.min(minIndex + 1, width - 1);

			min = Double.POSITIVE_INFINITY;
			for (int k = start; k <= end; k++) {
				if (shortestPath[i][k] < min) {
					min = shortestPath[i][k];
					minIndex = k;
				}
			}

			optimalSeam[i] = minIndex;

		}

		return optimalSeam;
	}

	@Override
	public int[][] vertical(int[][] img, int numOfColumns, EnergyFunction func) {
		
		for (int i = 0; i < numOfColumns; i++) {
			double[][] heatMap = func.getEnergyMap(img);
			int[] opt = getOptimalSeam(heatMap, true);
			img = Util.shiftImage(img, opt);
		}

		return img;
	}

	@Override
	public int [][] horizontal(int[][] input, int numOfRows, EnergyFunction func) {
		// TODO Auto-generated method stub
		return null;
	}
}

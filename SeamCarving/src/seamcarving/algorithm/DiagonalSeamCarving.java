package seamcarving.algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seamcarving.Util;

public class DiagonalSeamCarving implements SeamCarving {

	private String outputDirPath;
	int outputNum;

	public DiagonalSeamCarving() {
	}

	public DiagonalSeamCarving(String outputDirPath) {
		this.outputDirPath = outputDirPath;
		outputNum = 0;
	}

	@Override

	public int[][] vertical(int[][] img, int numOfColumns, EnergyFunction func) {
		if (numOfColumns == 0) {
			return img;
		}

		if (numOfColumns > 0) {
			return verticalRemove(img, numOfColumns, func);
		}

		return verticalAdd(img, -numOfColumns, func);
	}

	private int[][] verticalRemove(int[][] img, int numOfColumns, EnergyFunction func) {
		
		for (int i = 0; i < numOfColumns; i++) {
			double[][] heatMap = func.getEnergyMap(img);
			int[] opt = getOptimalSeam(heatMap);

			if (outputDirPath != null)
				exportImage(img, opt, i);

			img = Util.shrinkImage(img, opt);
		}

		return img;
	}
	
	private int[][] verticalAdd(int[][] img, int numOfColumns, EnergyFunction func) { // Increase image size
		int height = img.length;
		double[][] heatMap = func.getEnergyMap(img);
		int[][] optimalSeams = new int[numOfColumns][height];
		
		heatMap = func.getEnergyMap(img);
//		System.out.println("Heatmap before:");
//		Util.printArr(heatMap);
		
		for (int i = 0; i < numOfColumns; i++) {
			int[] opt = getOptimalSeam(heatMap);
			for (int j = 0; j < height; j++) {
				heatMap[j][opt[j]] += 0.5;
			}			
			optimalSeams[i] = opt;
			
			if (outputDirPath != null)
				exportImage(img, opt, outputNum++);
		}
		
		img = Util.enlargeImage(img, optimalSeams);
		
		return img;
	}

	private double[][] getShortestPaths(double[][] heatMap, int height, int width) {
		double[][] shortestPath = new double[height][width];

		for (int j = 0; j < width; j++) {
			shortestPath[0][j] = heatMap[0][j];
		}

		for (int i = 1; i < height; i++) {

			shortestPath[i][0] = heatMap[i][0] + Math.min(shortestPath[i - 1][0], shortestPath[i - 1][1]); // handle the
																											// edges
																											// separately

			for (int j = 1; j < width - 1; j++) {
				shortestPath[i][j] = heatMap[i][j] + Math
						.min(Math.min(shortestPath[i - 1][j - 1], shortestPath[i - 1][j]), shortestPath[i - 1][j + 1]);
			}

			shortestPath[i][width - 1] = heatMap[i][width - 1]
					+ Math.min(shortestPath[i - 1][width - 1], shortestPath[i - 1][width - 2]);
		}
		return shortestPath;
	}

	public int[] getOptimalSeam(double[][] heatMap) {
		double min = Double.POSITIVE_INFINITY;
		int minIndex = -1;
		int width = heatMap[0].length, height = heatMap.length;

		double[][] shortestPath = getShortestPaths(heatMap, height, width);

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

	private void exportImage(int[][] img, int[] path, int index) {

		BufferedImage outputImg = Util.arrToImg(img);
		int rgb = Color.red.getRGB();
		// draw the sim
		for (int i = 0; i < img.length; i++) {
			outputImg.setRGB(path[i], i, rgb);
		}
		
		File outputFile = new File(outputDirPath + "\\output\\output-" + index + ".jpg");
		try {
			ImageIO.write(outputImg, "jpg", outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

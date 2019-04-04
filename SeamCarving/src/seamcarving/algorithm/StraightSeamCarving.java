package seamcarving.algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class StraightSeamCarving implements SeamCarving {

	private int getOptimalSeam(double[][] heatMap, boolean isVertical) {
		double sum = 0;
		double min = Double.POSITIVE_INFINITY;
		int minIndex = -1;
		int dim1, dim2;
		
		if (isVertical) {
			dim1 = heatMap[0].length;
			dim2 = heatMap.length;
		}

		else {
			dim1 = heatMap.length;
			dim2 = heatMap[0].length;
		}

		for (int i = 0; i < dim1; i++) {
			sum = 0;
			for (int j = 0; j < dim2; j++) {
				if (isVertical) {
					sum += heatMap[j][i];
				}
				else {
					sum += heatMap[i][j];
				}
			}

			if (sum < min) {
				min = sum;
				minIndex = i;
			}
		}

		return minIndex;
	}

	@Override
	public BufferedImage vertical(BufferedImage img, int numOfColumns, EnergyFunction func) {

		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		double[][] heatMap = func.getEnergyMap(img);

		for (int i = 0; i < numOfColumns; i++) {
			int opt = getOptimalSeam(heatMap, true);
			for (int j = 0; j < img.getHeight(); j++) {
				int col = (i % 255);
				img.setRGB(opt, j, new Color(col, col, col).getRGB());
				heatMap[j][opt] = Double.POSITIVE_INFINITY;
			}
		}

		return img;
	}

	@Override
	public BufferedImage horizontal(BufferedImage img, int numOfRows, EnergyFunction func) {
		BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		double[][] heatMap = func.getEnergyMap(img);

		for (int i = 0; i < numOfRows; i++) {
			int opt = getOptimalSeam(heatMap, false);
			for (int j = 0; j < img.getWidth(); j++) {
				int col = 255 - (i % 255);
				img.setRGB(j, opt, new Color(col, col, col).getRGB());
				heatMap[opt][j] = Double.POSITIVE_INFINITY;
			}
		}

		return img;
	}

}

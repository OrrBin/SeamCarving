package seamcarving.algorithm;

import java.awt.image.BufferedImage;

public class StraightSeamCarving implements SeamCarving {

	private int getOptimalSeam(double[][] heatMap, boolean isVertical) {
		double sum = 0;
		double min = Double.POSITIVE_INFINITY;
		int minIndex = -1;
		int dim1, dim2;

		if (isVertical) {
			dim1 = heatMap.length;
			dim2 = heatMap[0].length;
		}

		else {
			dim1 = heatMap[0].length;
			dim2 = heatMap.length;
		}

		for (int i = 0; i < dim1; i++) {
			sum = 0;
			for (int j = 0; j < dim2; j++) {
				if (isVertical) {
					sum += heatMap[i][j];
				}
				else {
					sum += heatMap[j][i];
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
			int opt = getOptimalSeam(heatMap, false);
			for (int j = 0; j < img.getHeight(); j++) {
				img.setRGB(j, opt, -1);
				heatMap[opt][i] = Double.POSITIVE_INFINITY;
			}
		}

		return img;
	}

	@Override
	public BufferedImage horizontal(BufferedImage img, int numOfRows, EnergyFunction func) {
		// TODO Auto-generated method stub
		return null;
	}

}

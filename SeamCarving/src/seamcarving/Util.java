package seamcarving;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;

public class Util {

	public static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		int[][] result = new int[height][width];
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}

		return result;
	}

	public static int[][] shiftImage(int[][] pixels, int index) { // for straight carving
		int[] path = new int[pixels.length];
		Arrays.fill(path, index);
		return shiftImage(pixels, path);

	}

	public static int[][] shiftImage(int[][] pixels, int[] path) { // for diagonal carving
		int[][] result = new int[pixels.length][pixels[0].length - 1];

		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				if (j < path[i]) {
					result[i][j] = pixels[i][j];
				}
				if (j > path[i]) {
					result[i][j - 1] = pixels[i][j];
				}
			}
		}
		return result;
	}

	public static double[][] shiftImage(double[][] pixels, int[] path) { ////////////// for testing
		double[][] result = new double[pixels.length][pixels[0].length - 1];

		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				if (j < path[i]) {
					result[i][j] = pixels[i][j];
				}
				if (j > path[i]) {
					// System.out.println("i,j: " + i + " " + j);
					result[i][j - 1] = pixels[i][j];
				}
			}
		}
		return result;
	}

	public static void printArr(double[][] heatMap) {

		for (int k = 0; k < heatMap.length; k++) {
			System.out.print("line " + k + ": ");
			for (int l = 0; l < heatMap[0].length; l++) {
				System.out.print((int) heatMap[k][l] + "\t\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static void printArr(int[][] heatMap) {

		for (int k = 0; k < heatMap.length; k++) {
			System.out.print("line " + k + ": ");
			for (int l = 0; l < heatMap[0].length; l++) {
				System.out.print(heatMap[k][l] + "\t\t\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static BufferedImage arrToImg(int[][] arr) {

		BufferedImage imgOut = new BufferedImage(arr[0].length, arr.length, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				imgOut.setRGB(j, i, arr[i][j]);
			}
		}

		return imgOut;
	}

	public static BufferedImage arrToImg(double[][] arr) { ////////////// for testing

		BufferedImage imgOut = new BufferedImage(arr[0].length, arr.length, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				imgOut.setRGB(j, i, (int) arr[i][j]);
			}
		}

		return imgOut;
	}

	public static int[][] rotateMatrix(int[][] matrix, boolean clockwise) {
		 int totalRowsOfRotatedMatrix = matrix[0].length; //Total columns of Original Matrix
		  int totalColsOfRotatedMatrix = matrix.length; //Total rows of Original Matrix

		  int[][] rotatedMatrix = new int[totalRowsOfRotatedMatrix][totalColsOfRotatedMatrix];

		  for (int i = 0; i < matrix.length; i++) {
		   for (int j = 0; j < matrix[0].length; j++) {
			if(clockwise)
				rotatedMatrix[j][ (totalColsOfRotatedMatrix-1)- i] = matrix[i][j];
			else
				rotatedMatrix[(totalRowsOfRotatedMatrix-1)-j][i] = matrix[i][j];
		   }
		  }
		  return rotatedMatrix;
	}

}

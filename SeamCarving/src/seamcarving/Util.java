package seamcarving;

import java.awt.Color;
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

	public static int[][] shrinkImage(int[][] pixels, int index) { // For straight carving
		int[] path = new int[pixels.length];
		Arrays.fill(path, index);
		return shrinkImage(pixels, path);

	}

	public static int[][] shrinkImage(int[][] pixels, int[] path) { // For diagonal carving
		// System.out.println(Arrays.toString(path));
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

	public static int[][] enlargeImage(int[][] pixels, int[][] optimalSeams) {
		int height = pixels.length, width = pixels[0].length;
		int[][] pixelsToEnlarge = optimalSeamsToArray(optimalSeams, height, width);
		int[][] newImage = new int[height][width + optimalSeams.length];

		for (int i = 0; i < height; i++) {
			int jump = 0;
			for (int j = 0; j < width; j++) {
				newImage[i][j + jump] = pixels[i][j];
				for (int k = 0; k < pixelsToEnlarge[i][j]; k++) {
					jump++;
					if (!Main.interpolation) { // Duplicate the chosen seam as is
						newImage[i][j + jump] = pixels[i][j];
					} else { // Interpolate with the right seam
						if (j != width - 1) {
							newImage[i][j + jump] = interpolate(pixels[i][j],pixels[i][j + 1]);
						} else { // If pixel is rightmost, interpolate with left neighbor
							newImage[i][j + jump] = interpolate(pixels[i][j],pixels[i][j - 1]);
						}
					}
				}
			}
		}

		return newImage;
	}
	
	public static int interpolate(int RGB1, int RGB2) {
		Color c1 = new Color(RGB1);
		Color c2 = new Color(RGB2);
		
		int r = (c1.getRed() + c2.getRed()) / 2;
		int g = (c1.getGreen() + c2.getGreen()) / 2;
		int b = (c1.getBlue() + c2.getBlue()) / 2;
		
		return new Color(r,g,b).getRGB();
	}
	
	public static double[][] enlargeImage(double[][] pixels, int[][] optimalSeams) { //////////// for testing
		int height = pixels.length, width = pixels[0].length;
		int[][] pixelsToEnlarge = optimalSeamsToArray(optimalSeams, height, width);
		
		double[][] newImage = new double[height][width + optimalSeams.length];

		for (int i = 0; i < height; i++) {
			int jump = 0;
			for (int j = 0; j < width; j++) {
				newImage[i][j + jump] = (int) pixels[i][j];
				for (int k = 0; k < pixelsToEnlarge[i][j]; k++) {
					jump++;
					if (!Main.interpolation) { // Duplicate the chosen seam as is
						newImage[i][j + jump] = pixels[i][j];
					} else { // Interpolate with the right seam
						if (j != width - 1) {
							newImage[i][j + jump] = (pixels[i][j] + pixels[i][j + 1]) / 2;
						} else { // If pixel is rightmost, interpolate with left neighbor
							newImage[i][j + jump] = (pixels[i][j] + pixels[i][j - 1]) / 2;
						}
					}
				}
			}
		}

		return newImage;
	}

	public static double[][] shrinkImage(double[][] pixels, int[] path) { ////////////// for testing
		double[][] result = new double[pixels.length][pixels[0].length - 1];

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

	// Returns a int array in the size of the image.
	// The value of [i][j] is the number times the pixel (i,j) needs to be
	// duplicated.

	public static int[][] optimalSeamsToArray(int[][] optimalSeams, int height, int width) {
		int[][] optArr = new int[height][width];

		for (int i = 0; i < optimalSeams.length; i++) {
			for (int j = 0; j < optimalSeams[0].length; j++) {
				optArr[j][optimalSeams[i][j]]++;
			}
		}

		return optArr;
	}

	public static int[][] rotateMatrix(int[][] matrix, boolean clockwise) {
		int totalRowsOfRotatedMatrix = matrix[0].length; // Total columns of Original Matrix
		int totalColsOfRotatedMatrix = matrix.length; // Total rows of Original Matrix

		int[][] rotatedMatrix = new int[totalRowsOfRotatedMatrix][totalColsOfRotatedMatrix];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (clockwise)
					rotatedMatrix[j][(totalColsOfRotatedMatrix - 1) - i] = matrix[i][j];
				else
					rotatedMatrix[(totalRowsOfRotatedMatrix - 1) - j][i] = matrix[i][j];
			}
		}
		return rotatedMatrix;
	}

}

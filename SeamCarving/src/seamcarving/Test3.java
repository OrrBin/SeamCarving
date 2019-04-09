package seamcarving;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seamcarving.algorithm.DiagonalSeamCarving;

public class Test3 {
	public static void main(String[] args) throws IOException {

		int width = 3, height = 4, range = 3;
		int seamsToAdd = 2;
		
		double[][] yosi = new double[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				yosi[i][j] = (int) (Math.random() * range);
			}
		}
		
		System.out.println("Heatmap before:");
		Util.printArr(yosi);
		
		DiagonalSeamCarving sc = new DiagonalSeamCarving();
		
		
		for (int i = 0; i < seamsToAdd; i++) {
			int[] opt = ((DiagonalSeamCarving) sc).getOptimalSeam(yosi);

			for (int j = 0; j < height; j++) {
				yosi[j][opt[j]] = Double.POSITIVE_INFINITY;
			}
			System.out.println("---------Iter " + (i + 1) + "---------");
			System.out.println("Found path:");
			Util.printArr(yosi);
			System.out.println("After shift:");
			yosi = Util.shrinkImage(yosi, opt);
			Util.printArr(yosi);
		}

		System.out.println("----------------------\n");

		System.out.println("Heatmap after " + seamsToAdd + " seam removals and shifts:");
		Util.printArr(yosi);
		System.out.println("Done!");
	}
}

package seamcarving;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seamcarving.algorithm.DiagonalSeamCarving;

public class Test3 {
	public static void main(String[] args) throws IOException {

		int width = 100, height = 100;
		int seamsNum = 95;
		
		double[][] yosi = new double[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				yosi[i][j] = (int) (Math.random() * 10000);
			}
		}
		
		String outputFileName = "C:\\Users\\yahav\\eclipse-workspace\\SeamCarving\\output.jpg";
		BufferedImage img = Util.arrToImg(yosi);
		File imgdst = new File(outputFileName);
		ImageIO.write(img, "jpg", imgdst);
		
		System.out.println("Heatmap before:");
		Util.printArr(yosi);

		DiagonalSeamCarving sc = new DiagonalSeamCarving();

		for (int i = 0; i < seamsNum; i++) {
			int[] opt = ((DiagonalSeamCarving) sc).getOptimalSeam(yosi, true);

			for (int j = 0; j < height; j++) {
				yosi[j][opt[j]] = Double.POSITIVE_INFINITY;
			}
			System.out.println("---------Iter " + (i + 1) + "---------");
			System.out.println("Found path:");
			Util.printArr(yosi);
			System.out.println("After shift:");
			yosi = Util.shiftImage(yosi, opt);
			Util.printArr(yosi);
		}

		System.out.println("----------------------\n");

		System.out.println("Heatmap after " + seamsNum + " seam removals and shifts:");
		Util.printArr(yosi);
		System.out.println("Done!");
	}
}

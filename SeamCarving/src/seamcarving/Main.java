package seamcarving;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seamcarving.algorithm.BasicEnergyFunction;
import seamcarving.algorithm.DiagonalSeamCarving;
import seamcarving.algorithm.EnergyFunction;
import seamcarving.algorithm.EntropyEnergyFunction;
import seamcarving.algorithm.ForwardEnergyFunction;
import seamcarving.algorithm.SeamCarving;
import seamcarving.algorithm.StraightSeamCarving;

public class Main {
	public static boolean interpolation = true; // If true, seam adding will done with interpolation (with neighbors).
	public static boolean straight = false; // If true, only straight seams will be chosen.

	public static void main(String[] args) throws IOException {
		if (args.length == 0 || args.length < 5) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}

		String inputFileName = args[0];
		int numOfCol = Integer.valueOf(args[1]);
		int numOfRow = Integer.valueOf(args[2]);
		int energyType = Integer.valueOf(args[3]);
		String outputFileName = args[4];

		BufferedImage img = ImageIO.read(new File(inputFileName));
		int[][] pixels = Util.convertTo2DWithoutUsingGetRGB(img); // Convert image to pixels int array
		File imgdst = new File(outputFileName);

		int height = pixels.length, width = pixels[0].length;
		int colsChange = width - numOfCol;
		int rowsChange = height - numOfRow;

		EnergyFunction ef = null;

		switch (energyType) {
		case 0:
			ef = new BasicEnergyFunction();
			break;
		case 1:
			ef = new EntropyEnergyFunction();
			break;
		case 2:
			ef = new ForwardEnergyFunction();
			break;
		default:
			System.out.println("Wrong energy type.");
			System.exit(0);
		}

		SeamCarving sc = null;

		if (!straight) {
			sc = new DiagonalSeamCarving();
		} else
			sc = new StraightSeamCarving();

		pixels = sc.vertical(pixels, colsChange, ef);
		pixels = sc.horizontal(pixels, rowsChange, ef);

		BufferedImage imgOut = Util.arrToImg(pixels);

		ImageIO.write(imgOut, "jpg", imgdst);
		System.out.println("Finished.");
	}

}



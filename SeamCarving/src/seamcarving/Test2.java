package seamcarving;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seamcarving.algorithm.BasicEnergyFunction;
import seamcarving.algorithm.DiagonalSeamCarving;
import seamcarving.algorithm.EnergyFunction;
import seamcarving.algorithm.EntropyEnergyFunction;
import seamcarving.algorithm.SeamCarving;
import seamcarving.algorithm.StraightSeamCarving;

public class Test2 {

	public static void main(String[] args) throws IOException {
		boolean grey = true;

		if (args.length == 0 || args.length < 5) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}

		// ----------------- params -------------------
		String inputFileName = args[0];
		int numOfCol = 1;
//		int numOfRow = Integer.valueOf(args[2]);
		int numOfRow = 100;
		int energyType = Integer.valueOf(args[3]);
		String outputFileName = args[4];
		// --------------------------------------------
		
		double energy;
		BufferedImage img = ImageIO.read(new File(inputFileName));
		File imgdst = new File(outputFileName);

//		EnergyFunction ef = new BasicEnergyFunction();
		 EnergyFunction ef = new EntropyEnergyFunction();
		SeamCarving sc = new StraightSeamCarving();
//		 SeamCarving sc = new DiagonalSeamCarving(imgdst.getParent());

		int[][] arr = Util.convertTo2DWithoutUsingGetRGB(img);

//		Util.printArr(arr);
		
		System.out.println("before arr height, width : " + arr.length + ", " + arr[0].length);
		arr = sc.vertical(arr,numOfCol, ef);
//		arr = sc.horizontal(arr,numOfRow, ef);
		System.out.println("after arr height, width : " + arr.length + ", " + arr[0].length);
		 
		
		
		BufferedImage imgOut = Util.arrToImg(arr);


		ImageIO.write(imgOut, "jpg", imgdst);

		System.out.println("Done!");

	}
}

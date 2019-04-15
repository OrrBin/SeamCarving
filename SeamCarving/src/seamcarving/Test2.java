package seamcarving;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import seamcarving.algorithm.BasicEnergyFunction;
import seamcarving.algorithm.EnergyFunction;
import seamcarving.algorithm.SeamCarving;
import seamcarving.algorithm.StraightSeamCarving;

public class Test2 {

	public static void main(String[] args) throws IOException {

		if (args.length == 0 || args.length < 5) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}

		// ----------------- params -------------------
		String inputFileName = args[0] + "castle.jpg";
		int numOfCol = -200;
		// int numOfRow = Integer.valueOf(args[2]);
		int numOfRow = -200;
		int energyType = Integer.valueOf(args[3]);
		String outputFileName = args[4];
		// --------------------------------------------

		BufferedImage img = ImageIO.read(new File(inputFileName));
		File imgdst = new File(outputFileName);
		
//		EnergyFunction ef = new ForwardEnergyFunction();
		 EnergyFunction ef = new BasicEnergyFunction();
//		EnergyFunction ef = new EntropyEnergyFunction();
		 SeamCarving sc = new StraightSeamCarving();
//		SeamCarving sc = new DiagonalSeamCarving(imgdst.getParent());
//		SeamCarving sc = new DiagonalSeamCarving();

		int[][] arr = Util.convertTo2DWithoutUsingGetRGB(img);

		// Util.printArr(arr);

		System.out.println("before arr height, width : " + arr.length + ", " + arr[0].length);
		// arr = ((DiagonalSeamCarving)sc).vertical(arr,numOfCol, ef, true);
		
		File output = new File(args[0]+"output/");  // delete previous output directory
		for(File file: output.listFiles()) 
		    if (!file.isDirectory()) 
		        file.delete();
		
		System.out.println("Starting...");
		arr = sc.vertical(arr, numOfCol, ef);
		arr = sc.horizontal(arr, numOfRow, ef);
		System.out.println("after arr height, width : " + arr.length + ", " + arr[0].length);

		BufferedImage imgOut = Util.arrToImg(arr);

		ImageIO.write(imgOut, "jpg", imgdst);

		System.out.println("Done!");

	}
}

package seamcarving.algorithm;

public interface EnergyFunction {
	
	double[][] getEnergyMap(int[][] img);
	
	double calculateEnergyForPixel(int[][] img, int x, int y);
}
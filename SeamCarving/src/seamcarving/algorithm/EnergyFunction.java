package seamcarving.algorithm;

public interface EnergyFunction {
	
	// Returns an energy map of img.
	double[][] getEnergyMap(int[][] img); 
}
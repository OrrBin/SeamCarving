package seamcarving.algorithm;

public interface SeamCarving {
	int[][] vertical(int[][] input, int numOfColumns, EnergyFunction func);
	
	int[][] horizontal(int[][] input, int numOfRows, EnergyFunction func);

	
}

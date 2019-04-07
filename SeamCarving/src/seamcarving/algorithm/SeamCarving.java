package seamcarving.algorithm;

import seamcarving.Util;

public interface SeamCarving {
	
	int[][] vertical(int[][] input, int numOfColumns, EnergyFunction func);
	
	default int[][] horizontal(int[][] img, int numOfRows, EnergyFunction func) {
		img = Util.rotateMatrix(img, true);
		img = vertical(img, numOfRows, func);
		img = Util.rotateMatrix(img, false);
		return img;
	}

	
}

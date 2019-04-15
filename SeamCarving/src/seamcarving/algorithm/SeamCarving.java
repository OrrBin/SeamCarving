package seamcarving.algorithm;

import seamcarving.Util;

public interface SeamCarving {
	// Vertical add/remove of a seam.
	// @param input - an image
	// @param numOfColumns - number of seams to remove. Negative number means adding seams.
	// @param func - the seams will be choose based on this energy function.
	int[][] vertical(int[][] input, int numOfColumns, EnergyFunction func); 
	
	default int[][] horizontal(int[][] img, int numOfRows, EnergyFunction func) { // Rotate and apply vertical operations.
		img = Util.rotateMatrix(img, true);
		img = vertical(img, numOfRows, func);
		img = Util.rotateMatrix(img, false);
		return img;
	}

	
}

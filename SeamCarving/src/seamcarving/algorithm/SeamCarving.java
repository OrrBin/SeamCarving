package seamcarving.algorithm;

import java.awt.image.BufferedImage;

public interface SeamCarving {
	BufferedImage vertical(BufferedImage input, int numOfColumns, EnergyFunction func);
	
	BufferedImage horizontal(BufferedImage input, int numOfRows, EnergyFunction func);
}

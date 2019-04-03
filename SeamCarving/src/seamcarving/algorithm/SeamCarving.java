package seamcarving.algorithm;

import java.awt.image.BufferedImage;

public interface SeamCarving {
	BufferedImage vertical(BufferedImage input, int numOfColumns);
	
	BufferedImage horizontal(BufferedImage input, int numOfRows);
}

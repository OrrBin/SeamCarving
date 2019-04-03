package seamcarving;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) throws IOException {
		String path = "C:\\Users\\yahav\\Desktop\\tiger.jpg";
		
		BufferedImage img = ImageIO.read(new File(path));
		File imgdst = new File("C:\\Users\\yahav\\Desktop\\tiger2.jpg");
		Color myWhite = new Color(0,0,0); // Color white
		int rgb = myWhite.getRGB();
		byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		
		for(int i = 0; i < img.getWidth();i++)
	        for(int j = 0; j < img.getHeight(); j ++)
	        {
	        	if (j % 2 == 0)
	        		continue;
	            img.setRGB(i,j , rgb);
	        }
		
		ImageIO.write(img,"jpg", imgdst);

	}

}

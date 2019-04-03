package seamcarving;

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
		
		byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		
		for(int i = 0; i < img.getWidth()/2;i++)
	        for(int j = 0; j < img.getHeight()/2; j ++)
	        {
	            image.setRGB(i,j , 20);
	        }
		
		img.s
		ImageIO.write(img,"jpg", imgdst);

	}

}

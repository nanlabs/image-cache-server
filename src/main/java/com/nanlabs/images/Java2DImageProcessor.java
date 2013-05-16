package com.nanlabs.images;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Java2DImageProcessor implements ImageProcessor {
	

	@Override
	public byte[] doResize(InputStream source, int width) throws IOException {
		ByteArrayOutputStream destination = new ByteArrayOutputStream(16384);
		BufferedImage sourceImage = ImageIO.read(source);
		int originalWidth = sourceImage.getWidth();
		int scaledWidth = width > 0 ? width: originalWidth;
		int scaledHeight = (int)((float)sourceImage.getHeight() * ((float)scaledWidth/(float)originalWidth)); 
		BufferedImage bufferedResizedImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = bufferedResizedImage.createGraphics();	
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.drawImage(sourceImage, 0, 0,scaledWidth, scaledHeight, null);
		graphics.dispose();
		ImageIO.write(bufferedResizedImage, "jpeg", destination);
		sourceImage.flush();
		return destination.toByteArray();
	}
	
}

package com.nanlabs.images;

import java.awt.Image;
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
		Image resizedImage = sourceImage.getScaledInstance(width > 0 ? width: sourceImage.getWidth(), -1, Image.SCALE_AREA_AVERAGING);
		BufferedImage bufferedResizedImage = new BufferedImage(resizedImage.getWidth(null), resizedImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
		bufferedResizedImage.getGraphics().drawImage(resizedImage, 0, 0, null);
		ImageIO.write(bufferedResizedImage, "jpeg", destination);
		return destination.toByteArray();
	}
	
}

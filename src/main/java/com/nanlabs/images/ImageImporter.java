package com.nanlabs.images;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageImporter {
	
	private SourceRepository sourceRepository;
	
	private StorageRepository storageRepository;
	
	private ImageProcessor imageProcessor;
	
	
	
	
	public ImageImporter(SourceRepository sourceRepository, StorageRepository storageRepository, ImageProcessor imageProcessor) {
		this.sourceRepository = sourceRepository;
		this.storageRepository = storageRepository;
		this.imageProcessor = imageProcessor;
	}

	public void doImport(String sourceURL, int... widths ) throws IOException{
		
		InputStream imageData = sourceRepository.fetchImageData(sourceURL);
		
		for(Integer width:widths){
			byte[] processedImage = imageProcessor.doResize(imageData, width);
			Image image = new Image(new URL(sourceURL).getPath(), processedImage, width);
			storageRepository.store(image);
		}
	}


}

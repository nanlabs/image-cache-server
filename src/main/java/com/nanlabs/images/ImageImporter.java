package com.nanlabs.images;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

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
		
		URL urlToCache = new URL(sourceURL);
		String urlPath = urlToCache.getPath();
		
		String [] urlPathArray = urlPath.substring(1).split("/");
		String context = urlPathArray[0];
		
		String encodedURL;
		
		if (context.equalsIgnoreCase("vfs")) {
			// This URL is a VFS URL, the path has to be encoded.
			
			Integer port = urlToCache.getPort();
			String host = urlToCache.getHost();
			String protocol = urlToCache.getProtocol();
			
			String urlContext = protocol + "://" + host + ":" + port;
			
			urlContext += "/" + context + "/";
			
			List<String> urlPathList = Arrays.asList(urlPathArray);
			urlPathList = urlPathList.subList(1, urlPathList.size());
			String vfsPath = "";
			String joinCharacter = "";
			for (String pathChunk : urlPathList) {
				vfsPath += joinCharacter + pathChunk;
				joinCharacter = "/";
			}
			
			encodedURL = urlContext + URLEncoder.encode(vfsPath, "UTF-8");
		} else {
			encodedURL = sourceURL;
		}
		
		InputStream imageData = sourceRepository.fetchImageData(encodedURL);
		
		for(Integer width:widths){
			byte[] processedImage = imageProcessor.doResize(imageData, width);
			Image image = new Image(urlPath, processedImage, width);
			storageRepository.store(image);
		}
	}


}

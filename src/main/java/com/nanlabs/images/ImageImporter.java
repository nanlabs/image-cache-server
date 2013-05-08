package com.nanlabs.images;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class ImageImporter {
	
	private static final String VFS_URL_CONTEXT = "vfs";

	private SourceRepository sourceRepository;
	
	private StorageRepository storageRepository;
	
	private ImageProcessor imageProcessor;
	
	public ImageImporter(SourceRepository sourceRepository, StorageRepository storageRepository, ImageProcessor imageProcessor) {
		this.sourceRepository = sourceRepository;
		this.storageRepository = storageRepository;
		this.imageProcessor = imageProcessor;
	}

	public void doImport(String sourceURL, int... widths) throws IOException {
		
		URL urlToCache = new URL(sourceURL);
		String urlPath = urlToCache.getPath();
		
		String encodedURL;
		
		if (this.isVfsURL(urlToCache)) {
			// This URL is a VFS URL, the VFS path has to be encoded.
			String urlContext = this.buildUrlContext(urlToCache);
			String vfsPath = this.buildVfsPath(urlPath);
			
			encodedURL = urlContext + URLEncoder.encode(vfsPath, "UTF-8");
		} else {
			encodedURL = sourceURL;
		}
		
		InputStream imageData = sourceRepository.fetchImageData(encodedURL);
		
		for (Integer width : widths) {
			byte[] processedImage = imageProcessor.doResize(imageData, width);
			Image image = new Image(urlPath, processedImage, width);
			storageRepository.store(image);
		}
	}
	
	private String buildUrlContext(URL initialUrl) {
		
		String [] urlPathArray = initialUrl.getPath().substring(1).split("/");
		String context = urlPathArray[0];
		
		Integer port = initialUrl.getPort();
		String host = initialUrl.getHost();
		String protocol = initialUrl.getProtocol();
		
		String urlContext = protocol + "://" + host + ":" + port;
		
		urlContext += "/" + context + "/";
		
		return urlContext;
	}
	
	private String buildVfsPath(String urlPath) {
		
		String [] urlPathArray = urlPath.substring(1).split("/");
		
		List<String> urlPathList = Arrays.asList(urlPathArray);
		urlPathList = urlPathList.subList(1, urlPathList.size());
		
		String vfsPath = "";
		String joinCharacter = "";
		for (String pathChunk : urlPathList) {
			vfsPath += joinCharacter + pathChunk;
			joinCharacter = "/";
		}
		
		return vfsPath;
	}
	
	private Boolean isVfsURL(URL sourceURL) {
		
		String urlPath = sourceURL.getPath();
		String [] urlPathArray = urlPath.substring(1).split("/");
		String context = urlPathArray[0];
		
		return context.equalsIgnoreCase(VFS_URL_CONTEXT);
	}
}

package com.nanlabs.images;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.pool.ObjectPool;

public class ImageImporter {
	
	private static final String VFS_URL_CONTEXT = "vfs";
	
	private StorageRepository storageRepository;
	
	private ImageProcessor imageProcessor;
	
	private ObjectPool<URLConnectionFactory> connectionFactoryPool;
	
	public ImageImporter(ObjectPool<URLConnectionFactory> connectionFactoryPool, StorageRepository storageRepository, ImageProcessor imageProcessor) {
		this.connectionFactoryPool = connectionFactoryPool;
		this.storageRepository = storageRepository;
		this.imageProcessor = imageProcessor;
	}

	public void doImport(String sourceURL, int... widths) throws IOException {
		
		String encodedURL = normalize(sourceURL);
		String urlPath = new URL(sourceURL).getPath();
		
		URLConnectionFactory connectionFactory = null;
		try {
			connectionFactory = connectionFactoryPool.borrowObject();
			processImage(connectionFactory.open(encodedURL), urlPath, widths);
		} catch (IOException ioe) {
			throw ioe;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to borrow connectionFactory from the pool");
		} finally {
	         if(connectionFactory != null) {
	        	 try {
					connectionFactoryPool.returnObject(connectionFactory);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("Unable to return connectionFactory to the pool");
				}
	         }
	    }
	}

	private void processImage(URLConnection connection, String urlPath, int... widths) throws IOException {
		InputStream imageData = connection.getInputStream();
		try {
			for (Integer width : widths) {
				byte[] processedImage = imageProcessor.doResize(imageData, width);
				Image image = new Image(urlPath, processedImage, width);
				storageRepository.store(image);
			}
		} finally {
			imageData.close();
		}
	}
	
	private String normalize(String sourceURL) throws UnsupportedEncodingException, MalformedURLException{
		String encodedURL;
		URL urlToCache = new URL(sourceURL);
		String urlPath = urlToCache.getPath();
		
		if (this.isVfsURL(urlToCache)) {
			// This URL is a VFS URL, the VFS path has to be encoded.
			String urlContext = this.buildUrlContext(urlToCache);
			String vfsPath = this.buildVfsPath(urlPath);
			
			encodedURL = urlContext + URLEncoder.encode(vfsPath, "UTF-8");
		} else {
			encodedURL = sourceURL;
		}
		return encodedURL;
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

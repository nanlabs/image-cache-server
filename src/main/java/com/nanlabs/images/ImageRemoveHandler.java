package com.nanlabs.images;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class ImageRemoveHandler extends AbstractHandler {

private StorageRepository storageRepository;

	public ImageRemoveHandler(StorageRepository storageRepository) {
		this.storageRepository = storageRepository;
	}

	/**
	 * http://[server-url]:[server-port]/remove/[xmbc-image-path]
	 */
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Params params = new Params(request);
		
		System.out.println("Attempting to remove image: " + params.getImageId());
		
		Boolean removeResult = storageRepository.remove(URLDecoder.decode(params.getImageId(), "UTF-8"));
		if (removeResult) {
			System.out.println("Image removed succesfully, id: " + params.getImageId());
			response.setStatus(HttpServletResponse.SC_OK);			
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		
		baseRequest.setHandled(true);
	}
	
	private class Params {
		
		private final String imageId;

		public Params(HttpServletRequest request){
			this.imageId = request.getRequestURI().replaceFirst("remove/", "");
		}
		
		public String getImageId() {
			return imageId;
		}
	}
}

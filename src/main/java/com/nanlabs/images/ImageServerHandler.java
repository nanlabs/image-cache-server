package com.nanlabs.images;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class ImageServerHandler extends AbstractHandler {
	
	private StorageRepository storageRepository;
	
	
	

	public ImageServerHandler(StorageRepository storageRepository) {
		this.storageRepository = storageRepository;
	}

	/**
	 * http://[server-url]:[server-port]/static/[xmbc-image-path]
	 */
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Params params = new Params(request);
		Image image = storageRepository.findBestFitting(URLDecoder.decode(params.getImageId(), "UTF-8"), 0);
		if(image != null){
			response.setContentType("image/jpeg");
			response.getOutputStream().write(image.getData());
			response.getOutputStream().flush();
			response.setStatus(HttpServletResponse.SC_OK);			
		}else{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		baseRequest.setHandled(true);
	}
	
	private class Params {
		
		private final String imageId;

		public Params(HttpServletRequest request){
			this.imageId = request.getRequestURI().replaceFirst("static/", "");
		}
		
		public String getImageId() {
			return imageId;
		}
	}

}

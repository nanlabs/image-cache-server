package com.nanlabs.images;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class ImageResizerHandler extends AbstractHandler{

	private ImageImporter importer;
	
	
	
	public ImageResizerHandler(ImageImporter importer) {
		this.importer = importer;
	}

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		Params params = new Params(request);
		
		importer.doImport(params.getSourceURL(), params.getWidths());
		
		baseRequest.setHandled(true);
		response.setStatus(HttpServletResponse.SC_OK);		
	}
		
	
	private class Params {
		
		private static final String WIDTHS_PARAM = "widths";
		private static final String SOURCEURL_PARAM = "source";

		private final String sourceURL;
		
		private final int[] widths;

		public Params(HttpServletRequest request) {
			
			String widthsParam = request.getParameter(Params.WIDTHS_PARAM);
			String[] tokens;
			if(widthsParam != null && !widthsParam.isEmpty()){
				tokens = widthsParam.split(",");
			}else{
				tokens = new String[]{String.valueOf(Image.DEFAULT_WIDTH)};
			}
			int[] widths = new int[tokens.length];
			for(int i=0;i<widths.length;i++){
				widths[i] = Integer.parseInt(tokens[i]);
			}
			this.sourceURL = request.getParameter(Params.SOURCEURL_PARAM);
			this.widths = widths;
		}

		public String getSourceURL() {
			return sourceURL;
		}

		public int[] getWidths() {
			return widths;
		}
		
	}
}
